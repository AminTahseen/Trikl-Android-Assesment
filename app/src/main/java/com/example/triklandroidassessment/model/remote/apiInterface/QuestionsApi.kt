package com.example.triklandroidassessment.model.remote.apiInterface

import com.example.triklandroidassessment.model.dataModel.QuestionsOptionsAnswer
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.GET

interface QuestionsApi {
    @GET("/questions")
    suspend fun getQuestionsAndOptions(): Response<QuestionsOptionsAnswer?>

}