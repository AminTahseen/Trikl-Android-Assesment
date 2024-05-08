package com.example.triklandroidassessment.view.fragments.mainMenu

sealed class MainMenuEvents{
    object StartNewGame: MainMenuEvents()
    data class SetCurrentHighScore(val score:Int):MainMenuEvents()
}