package com.example.triklandroidassessment.view.fragments.startNewGame

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.triklandroidassessment.model.local.models.HighScore
import com.example.triklandroidassessment.model.remote.models.QuestionsOptionsAnswer
import com.example.triklandroidassessment.model.useCases.AddOrUpdateHighScoreUseCase
import com.example.triklandroidassessment.model.useCases.GetLatestHighScoreUseCase
import com.example.triklandroidassessment.model.useCases.GetQuestionsAnswersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StartNewGameViewModel @Inject constructor(
    private val getQuestionsAnswersUseCase: GetQuestionsAnswersUseCase,
    private val getLatestHighScoreUseCase: GetLatestHighScoreUseCase,
    private val addHighScoreUseCase: AddOrUpdateHighScoreUseCase
) : ViewModel() {
    private val _events = MutableLiveData<StartNewGameEvents>()
    val events = _events

    private var onGoingQuestionIndex = 0
    private val questionsAnswersList = mutableListOf<QuestionsOptionsAnswer.Question>()

    private var rightAnswer = ""
    private var selectedAnswer = ""
    var setIsOptionsClickable = true

    private var totalScore = 0

    private var HIGH_SCORE = 0
    private var HighScore_OBJ_ID = -1

    init {
        viewModelScope.launch {
            getQuestionAnswers()
        }
    }

     fun retryData(){
        viewModelScope.launch {
            getQuestionAnswers()
        }
    }
    private suspend fun getQuestionAnswers() {
        callEvent(StartNewGameEvents.ShowLoader(true))
        getQuestionsAnswersUseCase().catch {
            callEvent(StartNewGameEvents.ShowLoader(false))
        }.collect {
            callEvent(StartNewGameEvents.ShowLoader(false))
            if (it == null) {
                callEvent(StartNewGameEvents.ShowToast("No Data Found..."))
                callEvent(StartNewGameEvents.SetContentVisibility(false))
            } else {
                callEvent(StartNewGameEvents.SetContentVisibility(true))
                it.questions?.let { questionsAnswers ->
                    questionsAnswersList.addAll(questionsAnswers)
                    setQuestion(onGoingQuestionIndex)
                }
            }
            getTotalScore()
        }
    }

    private suspend fun getTotalScore() {
        getLatestHighScoreUseCase().catch {
            Log.d("Error", "err")
        }.collect {
            it?.let { highScore ->
                Log.d("dbStoredScore", highScore.highScore.toString())
                HIGH_SCORE = highScore.highScore
                HighScore_OBJ_ID = highScore.id!!
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
            setAndCheckScoreToDB()
        } else {
            onGoingQuestionIndex = index
            setQuestion(index)
        }
        selectedAnswer = ""
    }

    private fun setAndCheckScoreToDB() {
        viewModelScope.launch {
            if (HIGH_SCORE == 0) {
                addHighScoreToDB(false)
            } else {
                if (totalScore > HIGH_SCORE)
                    addHighScoreToDB(true)
                else
                    callEvent(StartNewGameEvents.Finish(totalScore))
            }
        }
    }

    private suspend fun addHighScoreToDB(isUpdate: Boolean) {
        if (isUpdate) {
            addHighScoreUseCase(
                HighScore(highScore = totalScore, id = HighScore_OBJ_ID),
                isUpdate = true
            ).catch {
            }.collect {
                callEvent(StartNewGameEvents.Finish(totalScore))
            }
        } else {
            addHighScoreUseCase(HighScore(highScore = totalScore), isUpdate = false).catch {
            }.collect {
                callEvent(StartNewGameEvents.Finish(totalScore))
            }
        }
    }

    private fun callEvent(events: StartNewGameEvents) {
        _events.value = events
    }
}