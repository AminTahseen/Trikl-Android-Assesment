package com.example.triklandroidassessment.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StartNewGameViewModel:ViewModel() {
    val ongoingHighScore = MutableLiveData("10")

}