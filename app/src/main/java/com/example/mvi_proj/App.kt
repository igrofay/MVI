package com.example.mvi_proj

import android.app.Application
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.bindProvider

class App: Application(), DIAware {
    override val di by DI.lazy {
        bindProvider { Counter() }
    }
}