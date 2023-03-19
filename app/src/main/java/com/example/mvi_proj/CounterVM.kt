package com.example.mvi_proj

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.syntax.simple.repeatOnSubscription

class CounterVM(
    override val di: DI
) : ViewModel(),
    DIAware,
    ContainerHost<CounterState,CounterAction >,
    EventBase<CounterEvent>
{
    private val counter: Counter by di.instance()
    override val container = viewModelScope.container<CounterState,CounterAction >(
        CounterState(0),
        onCreate = {
            subscribe()
        }
    )
    override fun onEvent(event: CounterEvent) = intent {
        when(event){
            CounterEvent.Add -> counter.add()
            CounterEvent.Subtract -> counter.subtract()
        }
    }

    private fun subscribe() = intent(registerIdling = false) {
        repeatOnSubscription {
            counter.number.collect{ newNum->
                if (
                    newNum % 10 == 0 && state.number != newNum
                ) postSideEffect(CounterAction.ShowSnackBar("Вы дошли до $newNum"))
                reduce { state.copy(number = newNum) }
            }
        }
    }

}