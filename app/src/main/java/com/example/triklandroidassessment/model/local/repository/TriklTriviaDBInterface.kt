package com.example.triklandroidassessment.model.local.repository

import com.example.triklandroidassessment.model.local.models.HighScore
import kotlinx.coroutines.flow.Flow

interface TriklTriviaDBInterface {
     fun getLatestScore(): Flow<HighScore?>
    suspend fun addHighScore(highScore: HighScore)
    suspend fun updateHighScore(highScore: HighScore)
}