package com.example.triklandroidassessment.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.triklandroidassessment.events.MainMenuEvents
import com.example.triklandroidassessment.events.StartNewGameEvents
import com.example.triklandroidassessment.model.dataModel.QuestionsOptionsAnswer
import com.example.triklandroidassessment.useCases.GetQuestionsAnswersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StartNewGameViewModel @Inject constructor(
    private val getQuestionsAnswersUseCase: GetQuestionsAnswersUseCase
) : ViewModel() {
    private val _events = MutableLiveData<StartNewGameEvents>()
    val events = _events

    val ongoingHighScore = MutableLiveData("0")
    val btnText = MutableLiveData("Submit")
    var onGoingQuestionIndex = 0
    private val questionsAnswersList = mutableListOf<QuestionsOptionsAnswer.Question>()

    private var rightAnswer = ""
    private var selectedAnswer = ""

    init {
        viewModelScope.launch {
            getQuestionAnswers()
        }
    }

    private suspend fun getQuestionAnswers() {
        callEvent(StartNewGameEvents.ShowLoader(true))
        getQuestionsAnswersUseCase().catch {
            callEvent(StartNewGameEvents.ShowLoader(false))
            Log.d("QuestionsAnswerssss", it.toString())
        }.collect {
            callEvent(StartNewGameEvents.ShowLoader(false))
            it?.questions?.let { questionsAnswers ->
                questionsAnswersList.addAll(questionsAnswers)
                setQuestion(onGoingQuestionIndex)
            }
        }
    }

    private fun setQuestion(index: Int) {
        if (questionsAnswersList.isNotEmpty()) {
            questionsAnswersList[index].question?.let { question ->
                callEvent(
                    StartNewGameEvents.SetQuestion(
                        index + 1,
                        questionsAnswersList.size,
                        question
                    )
                )
            }
            questionsAnswersList[index].options?.let {
                callEvent(StartNewGameEvents.SetOptions(it))
            }
            questionsAnswersList[index].answer?.let {
                rightAnswer = it
            }
            callEvent(StartNewGameEvents.StartStop10SecondsTimer(stopTimer = false))
        }
    }

    fun verifyAnswer(answer: String) {
        selectedAnswer = answer
        callEvent(StartNewGameEvents.CheckRightWrong(answer, rightAnswer))
        callEvent(StartNewGameEvents.StartStop10SecondsTimer(stopTimer = true))
        if (answer == rightAnswer) {
            Log.d("answer", "RIGHT")
        } else {
            Log.d("answer", "WRONGGGGG")
        }
    }

    fun nextQuestion() {
        if(selectedAnswer==""){
            callEvent(StartNewGameEvents.ShowToast("Select an option first !"))
        }else{
            val index = onGoingQuestionIndex + 1
            if (index == questionsAnswersList.size) {
                callEvent(StartNewGameEvents.Finish)
            } else {
                onGoingQuestionIndex = index
                setQuestion(index)
            }
        }
        selectedAnswer=""
    }

    private fun callEvent(events: StartNewGameEvents) {
        _events.value = events

    }
}