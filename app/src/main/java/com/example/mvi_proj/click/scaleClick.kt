package com.example.mvi_proj.click

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.scale
import androidx.compose.ui.input.pointer.pointerInput

private enum class ScaleButtonState{
    Pressed, Idl
}



fun Modifier.scaleClick(
    enabled: Boolean = true,
    onClick:()->Unit,
) = composed {
    var state by remember {
        mutableStateOf(ScaleButtonState.Idl)
    }
    val scale by animateFloatAsState(
        targetValue = if(enabled){
            when(state){
                ScaleButtonState.Pressed -> 0.9f
                ScaleButtonState.Idl -> 1f
            }
        } else 1f
    )
    this
        .scale(scale)
        .clickable(
            interactionSource = remember {
                MutableInteractionSource()
            },
            enabled = enabled,
            indication = null,
            onClick = onClick
        )
        .pointerInput(state){
            awaitPointerEventScope {
                state = when(state){
                    ScaleButtonState.Pressed -> {
                        waitForUpOrCancellation()
                        ScaleButtonState.Idl
                    }
                    ScaleButtonState.Idl -> {
                        awaitFirstDown(false)
                        ScaleButtonState.Pressed
                    }
                }
            }
        }
}