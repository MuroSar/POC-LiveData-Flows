package com.example.poclivedataflows

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    /**
     * Creating an instance
     * Flow is not created as in the same way
     */
    private val _liveData = MutableLiveData("Hello World")
    val liveData: LiveData<String> = _liveData

    private val _stateFlow = MutableStateFlow("Hello World")
    val stateFlow = _stateFlow.asStateFlow()

    private val _sharedFlow = MutableSharedFlow<String>()
    val sharedFlow = _sharedFlow.asSharedFlow()

    // Assign a new value to live data
    fun triggerLiveData() {
        _liveData.value = "LiveData"
    }

    // Assign a new value to state flow
    fun triggerStateFlow() {
        _stateFlow.value = "StateFlow"
    }

    /**
     * Flow generator
     * Repeat block here five times.
     * Will output a string that gives us the current iteration.
     * Delay the flow for a second.
     */
    fun triggerFlow(): Flow<String> {
        return flow {
            repeat(5) {
                emit("Item $it")
                delay(1000L)
            }
        }
    }

    // Assign a new value to shared flow
    fun triggerSharedFlow() {
        viewModelScope.launch {
            _sharedFlow.emit("SharedFlow")
        }
    }
}
