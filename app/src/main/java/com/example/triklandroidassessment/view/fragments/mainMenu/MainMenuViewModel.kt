package com.example.triklandroidassessment.view.fragments.mainMenu

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class MainMenuViewModel:ViewModel() {

    private val _events = MutableSharedFlow<MainMenuEvents>()
    val events = _events.asSharedFlow()

    val currentHighScore = MutableLiveData("")
    init {
        getCurrentHighScore()
    }
    fun startANewGame(){
        callEvent(MainMenuEvents.StartNewGame)
    }
    private fun callEvent(events: MainMenuEvents){
        viewModelScope.launch {
            _events.emit(events)
        }
    }
    private fun getCurrentHighScore(){
        currentHighScore.value="Current High Score - 20 POINTS"
    }
}