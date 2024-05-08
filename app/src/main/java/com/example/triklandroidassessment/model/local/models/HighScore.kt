package com.example.triklandroidassessment.model.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class HighScore(
    val highScore: Int = 0,
    @PrimaryKey val id: Int? = null
)
