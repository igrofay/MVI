package com.example.mvi_proj

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelLazy
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import org.kodein.di.DIAware
import org.kodein.di.compose.localDI
import kotlin.reflect.full.createInstance

@Composable
inline fun <reified VM> rememberVM() where VM : ViewModel, VM: DIAware = with(localDI()) {
    val viewModelStoreOwner = LocalViewModelStoreOwner.current ?: error("")
    remember {
        ViewModelLazy(
            viewModelClass = VM::class,
            storeProducer = { viewModelStoreOwner.viewModelStore },
            factoryProducer = {
                object : ViewModelProvider.Factory {
                    override fun <T : ViewModel> create(modelClass: Class<T>): T =
                        VM::class.constructors.first().call(di) as T
                }
            }
        )
    }
}