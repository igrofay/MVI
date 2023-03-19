package com.example.mvi_proj

sealed class CounterEvent {
    object Add: CounterEvent()
    object Subtract: CounterEvent()
}