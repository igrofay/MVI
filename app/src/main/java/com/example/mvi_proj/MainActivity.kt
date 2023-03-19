package com.example.mvi_proj

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.createSavedStateHandle
import com.example.mvi_proj.ui.theme.MVI_projTheme
import org.kodein.di.DIAware
import org.kodein.di.android.closestDI
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import org.orbitmvi.orbit.compose.collectState

class MainActivity : ComponentActivity(), DIAware {
    override val di by closestDI()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MVI_projTheme {
                // A surface container using the 'background' color from the theme
                val vm by rememberVM<CounterVM>()
                val state by vm.collectAsState()
                val scaffoldState = rememberScaffoldState()
                vm.collectSideEffect{ counterAction ->
                    when(counterAction){
                        is CounterAction.ShowSnackBar -> {
                            scaffoldState.snackbarHostState.showSnackbar(
                                counterAction.message,
                            )
                        }
                    }
                }
                Scaffold(
                    scaffoldState = scaffoldState
                ){
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {

                        Text(text = state.number.toString())
                        Button(onClick = { vm.onEvent(CounterEvent.Add) }) {
                            Text(text = "Add")
                        }
                        Button(onClick = { vm.onEvent(CounterEvent.Subtract) }) {
                            Text(text = "Subtract")
                        }
                    }
                }
            }
        }
    }



}
