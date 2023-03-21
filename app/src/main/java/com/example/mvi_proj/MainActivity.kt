package com.example.mvi_proj


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mvi_proj.click.scaleClick
import com.example.mvi_proj.ui.theme.MVI_projTheme
import org.kodein.di.DIAware
import org.kodein.di.android.closestDI
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect


class MainActivity : ComponentActivity(), DIAware {
    override val di by closestDI()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MVI_projTheme {
                val navController = rememberNavController()
                NavHost(navController, startDestination = "One"){
                    composable("One"){
                        ScreenOne {
                            navController.navigate("Two/Hello")
                        }
                    }
                    composable(
                        "Two/{mes}",
                        arguments = listOf(
                            navArgument("mes"){ type = NavType.StringType }
                        )
                    ){
                        ScreenTwo()
                    }
                }
            }
        }
    }


    @Composable
    fun ScreenOne(goToSecond: ()-> Unit) {
        Button(onClick = goToSecond) {

        }
    }

    @Composable
    fun ScreenTwo() {
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
                Box(
                    modifier = Modifier
                        .scaleClick(
                            enabled = state.number > 0
                        ) {
                            vm.onEvent(CounterEvent.Subtract)
                        }
                        .background(MaterialTheme.colors.primary)
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ){
                    Text(text = "Subtract")
                }
            }
        }
    }


}
