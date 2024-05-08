package com.example.triklandroidassessment.model.useCases

import com.example.triklandroidassessment.model.local.models.HighScore
import com.example.triklandroidassessment.model.local.repository.TriklTriviaDBInterface
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddOrUpdateHighScoreUseCase @Inject constructor(
    private val triklTriviaDBInterface: TriklTriviaDBInterface
) {
    suspend operator fun invoke(param: HighScore, isUpdate: Boolean): Flow<String> {
        if (isUpdate)
            triklTriviaDBInterface.updateHighScore(param)
        else
            triklTriviaDBInterface.addHighScore(param)
        return flow { emit("Stored") }
    }
}