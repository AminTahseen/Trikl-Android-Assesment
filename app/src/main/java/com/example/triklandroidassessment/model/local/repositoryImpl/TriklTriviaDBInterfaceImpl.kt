package com.example.triklandroidassessment.model.local.repositoryImpl

import com.example.triklandroidassessment.model.local.dao.TriklTriviaDao
import com.example.triklandroidassessment.model.local.models.HighScore
import com.example.triklandroidassessment.model.local.repository.TriklTriviaDBInterface
import kotlinx.coroutines.flow.Flow

class TriklTriviaDBInterfaceImpl(private val triklTriviaDao: TriklTriviaDao) :
    TriklTriviaDBInterface {
    override fun getLatestScore(): Flow<HighScore?> {
        return triklTriviaDao.getLatestScore()
    }

    override suspend fun addHighScore(highScore: HighScore) {
        triklTriviaDao.addHighScore(highScore)
    }

    override suspend fun updateHighScore(highScore: HighScore) {
        triklTriviaDao.updateHighScore(highScore)
    }
}