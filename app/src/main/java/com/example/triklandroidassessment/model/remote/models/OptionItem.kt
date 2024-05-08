package com.example.triklandroidassessment.model.remote.models

data class OptionItem(
    val option: String,
    var isRightAnswer: Boolean = false,
    var isWrongAnswer: Boolean = false,
    var isNoAnswerSelected: Boolean = false
)
