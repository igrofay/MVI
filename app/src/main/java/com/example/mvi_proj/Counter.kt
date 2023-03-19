package com.example.mvi_proj

import android.util.Log
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

class Counter {
    private val flow = MutableStateFlow(0)
    val number = flow.asStateFlow()
    suspend fun add() = with(flow){
        emit(value.inc())
    }
    suspend fun subtract() = with(flow){
        emit(value.dec())
    }
}