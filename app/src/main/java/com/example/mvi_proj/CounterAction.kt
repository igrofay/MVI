package com.example.mvi_proj

sealed class CounterAction {
    class ShowSnackBar(val message: String): CounterAction()
}