package com.example.triklandroidassessment.model.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.triklandroidassessment.model.local.dao.TriklTriviaDao
import com.example.triklandroidassessment.model.local.models.HighScore

@Database(
    entities = [HighScore::class],
    version = 1
)
abstract class TriklTriviaDatabase : RoomDatabase() {
    abstract val triklTriviaDao: TriklTriviaDao

    companion object {
        const val DATABASE_NAME = "trikl_trivia_db"
    }
}