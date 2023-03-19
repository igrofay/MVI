package com.example.mvi_proj

interface EventBase<T> {
    fun onEvent(event: T)
}