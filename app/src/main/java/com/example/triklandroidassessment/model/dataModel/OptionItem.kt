package com.example.triklandroidassessment.model.dataModel

data class OptionItem(
    val option:String,
    var isRightAnswer:Boolean=false,
    var isWrongAnswer:Boolean=false
)
