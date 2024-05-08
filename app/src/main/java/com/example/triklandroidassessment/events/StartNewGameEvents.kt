package com.example.triklandroidassessment.events

sealed class StartNewGameEvents {
    data class ShowLoader(val show: Boolean) : StartNewGameEvents()
    data class SetQuestion(val number: Int, val total: Int, val question: String) :
        StartNewGameEvents()

    object Finish : StartNewGameEvents()
    data class SetOptions(val list: List<String>) : StartNewGameEvents()
    data class CheckRightWrong(val selected: String, val valid: String) : StartNewGameEvents()
   data class StartStop10SecondsTimer(val stopTimer:Boolean) : StartNewGameEvents()
    data class ShowToast(val message:String) : StartNewGameEvents()


}