package com.example.mvi_proj

import android.app.Application
import org.kodein.di.*

class App: Application(), DIAware {
    override val di by DI.lazy {
        bindProvider { new(::Counter) }
        bindProvider { new(::Empty) }
    }
}