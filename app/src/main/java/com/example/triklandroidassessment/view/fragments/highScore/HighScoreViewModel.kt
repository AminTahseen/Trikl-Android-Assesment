package com.example.triklandroidassessment.view.fragments.highScore

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HighScoreViewModel : ViewModel() {
    private val _events = MutableLiveData<HighScoreEvents>()
    val events = _events

    private fun callEvent(events: HighScoreEvents) {
        _events.value = events
    }

    fun navigateToMainMenu() {
        callEvent(HighScoreEvents.NavigateToMainMenu)
    }
}