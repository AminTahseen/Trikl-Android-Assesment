package com.example.triklandroidassessment.model.useCases

import com.example.triklandroidassessment.model.dataModel.QuestionsOptionsAnswer
import com.example.triklandroidassessment.model.remote.repository.QuestionsAnswersRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetQuestionsAnswersUseCase @Inject constructor(
    private val questionsAnswersRepo: QuestionsAnswersRepo
){
    suspend operator fun invoke(): Flow<QuestionsOptionsAnswer?> {
        return questionsAnswersRepo.getQuestionsAndOptions()
    }
}