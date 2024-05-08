package com.example.triklandroidassessment.model.remote.repository

import com.example.triklandroidassessment.model.remote.models.QuestionsOptionsAnswer
import kotlinx.coroutines.flow.Flow

interface QuestionsAnswersRepo {

    suspend fun getQuestionsAndOptions(): Flow<QuestionsOptionsAnswer?>

}