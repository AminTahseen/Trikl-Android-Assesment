package com.example.triklandroidassessment.view.fragments.mainMenu

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.triklandroidassessment.model.useCases.GetLatestHighScoreUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainMenuViewModel @Inject constructor(
    private val getLatestHighScoreUseCase: GetLatestHighScoreUseCase,
    ) : ViewModel() {

    private val _events = MutableSharedFlow<MainMenuEvents>()
    val events = _events.asSharedFlow()
    fun getCurrentHighScore() {
            viewModelScope.launch {
                getLatestHighScoreUseCase().catch {
                    Log.d("Error", "err")
                }.collect {
                    if(it!=null){
                        it.let { highScore ->
                            val highScore = highScore.highScore
                            Log.d("insideThis", highScore.toString())
                            callEvent(MainMenuEvents.SetCurrentHighScore(highScore))
                        }
                    }else{
                        callEvent(MainMenuEvents.SetCurrentHighScore(0))
                    }
                }
            }

    }

    fun startANewGame() {
        callEvent(MainMenuEvents.StartNewGame)
    }

    private fun callEvent(events: MainMenuEvents) {
        viewModelScope.launch {
            _events.emit(events)
        }
    }

}