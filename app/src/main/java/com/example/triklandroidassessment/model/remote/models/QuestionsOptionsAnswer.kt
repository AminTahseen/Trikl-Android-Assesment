package com.example.triklandroidassessment.model.remote.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class QuestionsOptionsAnswer(
    @Json(name = "questions")  val questions: List<Question>?
){
    @JsonClass(generateAdapter = true)
    data class Question(
        val answer: String?,
        val options: List<String>?,
        val question: String?
    )
}