package com.example.mvi_proj

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalSavedStateRegistryOwner
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavBackStackEntry
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.compose.localDI
import org.kodein.di.compose.rememberViewModel
import org.kodein.di.direct
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.starProjectedType

@Composable
inline fun <reified VM> rememberVM() where VM : ViewModel, VM: DIAware = with(localDI()) {
    val viewModelStoreOwner = LocalViewModelStoreOwner.current ?: error("")
    val localSavedStateRegistryOwner = LocalSavedStateRegistryOwner.current
    remember {
        val constructor = VM::class.constructors.first()
        val bundle = if(viewModelStoreOwner is NavBackStackEntry)viewModelStoreOwner.arguments else null
        ViewModelLazy(
            viewModelClass = VM::class,
            storeProducer = { viewModelStoreOwner.viewModelStore },
            factoryProducer = {
                object : AbstractSavedStateViewModelFactory(localSavedStateRegistryOwner, bundle) {
                    override fun <T : ViewModel> create(
                        key: String,
                        modelClass: Class<T>,
                        handle: SavedStateHandle
                    ): T {
                        val arg = constructor.parameters.associateWith {
                            when (it.type) {
                                (SavedStateHandle::class.starProjectedType) -> handle
                                (DI::class.starProjectedType) -> di
                                else -> error("Not found type")
                            }
                        }
                        return constructor.callBy(arg) as T
                    }

                }
            }
        )
    }
}