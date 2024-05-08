package com.example.triklandroidassessment.view.fragments.startNewGame

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.triklandroidassessment.model.dataModel.QuestionsOptionsAnswer
import com.example.triklandroidassessment.model.useCases.GetQuestionsAnswersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StartNewGameViewModel @Inject constructor(
    private val getQuestionsAnswersUseCase: GetQuestionsAnswersUseCase
) : ViewModel() {
    private val _events = MutableLiveData<StartNewGameEvents>()
    val events = _events

    private var onGoingQuestionIndex = 0
    private val questionsAnswersList = mutableListOf<QuestionsOptionsAnswer.Question>()

    private var rightAnswer = ""
    private var selectedAnswer = ""
    var setIsOptionsClickable = true

    private var totalScore = 0

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
            if (it == null) {
                callEvent(StartNewGameEvents.ShowToast("No Data Found..."))
            } else {
                it.questions?.let { questionsAnswers ->
                    questionsAnswersList.addAll(questionsAnswers)
                    setQuestion(onGoingQuestionIndex)
                }
            }
            getTotalScore()
            callEvent(StartNewGameEvents.SetProgress(10))
        }
    }

    private fun getTotalScore() {
        callEvent(StartNewGameEvents.SetTotalScore(totalScore))
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
        setIsOptionsClickable = false
        selectedAnswer = answer
        callEvent(StartNewGameEvents.CheckRightWrong(answer, rightAnswer))
        callEvent(StartNewGameEvents.StartStop10SecondsTimer(stopTimer = true))
        if (answer == rightAnswer) {
            Log.d("answer", "RIGHT")
            totalScore += 10
            callEvent(StartNewGameEvents.SetTotalScore(totalScore))
        } else {
            Log.d("answer", "WRONGGGGG")
        }
    }

    fun nextQuestion() {
        setIsOptionsClickable = true
        val index = onGoingQuestionIndex + 1
        callEvent(StartNewGameEvents.SetProgress(10))
        if (index == questionsAnswersList.size) {
            callEvent(StartNewGameEvents.Finish(totalScore))
        } else {
            onGoingQuestionIndex = index
            setQuestion(index)
        }
        selectedAnswer = ""
    }

    private fun callEvent(events: StartNewGameEvents) {
        _events.value = events
    }
}