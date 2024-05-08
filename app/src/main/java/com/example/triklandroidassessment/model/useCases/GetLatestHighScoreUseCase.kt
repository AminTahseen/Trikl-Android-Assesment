package com.example.triklandroidassessment.model.useCases

import com.example.triklandroidassessment.model.local.models.HighScore
import com.example.triklandroidassessment.model.local.repository.TriklTriviaDBInterface
import com.example.triklandroidassessment.model.remote.models.QuestionsOptionsAnswer
import com.example.triklandroidassessment.model.remote.repository.QuestionsAnswersRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLatestHighScoreUseCase @Inject constructor(
    private val triklTriviaDBInterface: TriklTriviaDBInterface
) {
    operator fun invoke(): Flow<HighScore?> {
        return triklTriviaDBInterface.getLatestScore()
    }
}