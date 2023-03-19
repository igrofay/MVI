package com.example.mvi_proj

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalSavedStateRegistryOwner
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import org.kodein.di.DIAware
import org.kodein.di.compose.localDI
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.starProjectedType

@Composable
inline fun <reified VM> rememberVM() where VM : ViewModel, VM: DIAware = with(localDI()) {
    val viewModelStoreOwner = LocalViewModelStoreOwner.current ?: error("")
    val localViewModelStoreOwner = LocalSavedStateRegistryOwner.current
    remember {
        val constructor = VM::class.constructors.first()
        val isBaseConstructor = constructor.parameters.any{ kParameter ->
            kParameter.type == SavedStateHandle::class.starProjectedType
        }
        ViewModelLazy(
            viewModelClass = VM::class,
            storeProducer = { viewModelStoreOwner.viewModelStore },
            factoryProducer = {
                if (isBaseConstructor){
                    object : AbstractSavedStateViewModelFactory(localViewModelStoreOwner, null) {
                        override fun <T : ViewModel> create(
                            key: String,
                            modelClass: Class<T>,
                            handle: SavedStateHandle
                        ): T =constructor.call(di, handle) as T
                    }
                }else{
                    object : ViewModelProvider.Factory {
                        override fun <T : ViewModel> create(modelClass: Class<T>): T =
                            constructor.call(di) as T
                    }
                }
            }
        )
    }
}