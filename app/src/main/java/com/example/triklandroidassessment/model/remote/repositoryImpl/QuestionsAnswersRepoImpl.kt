package com.example.triklandroidassessment.model.remote.repositoryImpl

import android.util.Log
import com.example.triklandroidassessment.model.remote.models.QuestionsOptionsAnswer
import com.example.triklandroidassessment.model.remote.apiInterface.QuestionsApi
import com.example.triklandroidassessment.model.remote.repository.QuestionsAnswersRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class QuestionsAnswersRepoImpl @Inject constructor(
    private val questionsApi: QuestionsApi
) : QuestionsAnswersRepo {

    override suspend fun getQuestionsAndOptions(): Flow<QuestionsOptionsAnswer?> {
        return try {
            val response = questionsApi.getQuestionsAndOptions()
            if (response.isSuccessful) {
                val responseString = response.body()
                val model = responseString as QuestionsOptionsAnswer
                Log.d("dataJSON", model.toString())
                flow { emit(model) }
            } else {
                flow { emit(null) }
            }
        } catch (e: Exception) {
            Log.e("NetworkError", "${e.message}")
            flow { emit(null) }
        }
    }
}