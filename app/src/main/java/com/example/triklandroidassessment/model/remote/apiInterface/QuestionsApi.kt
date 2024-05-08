package com.example.triklandroidassessment.model.remote.apiInterface

import com.example.triklandroidassessment.model.remote.models.QuestionsOptionsAnswer
import retrofit2.Response
import retrofit2.http.GET

interface QuestionsApi {
    @GET("/questions")
    suspend fun getQuestionsAndOptions(): Response<QuestionsOptionsAnswer?>

}