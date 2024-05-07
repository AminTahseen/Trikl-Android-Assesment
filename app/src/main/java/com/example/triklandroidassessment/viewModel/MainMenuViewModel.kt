package com.example.triklandroidassessment.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.triklandroidassessment.events.MainMenuEvents

class MainMenuViewModel:ViewModel() {
    private val _events = MutableLiveData<MainMenuEvents>()
    val events: LiveData<MainMenuEvents> = _events
    val currentHighScore = MutableLiveData("")
    init {
        getCurrentHighScore()
    }
    fun startANewGame(){
        _events.value=MainMenuEvents.StartNewGame
    }
    private fun getCurrentHighScore(){
        currentHighScore.value="Current High Score - 20"
    }
}