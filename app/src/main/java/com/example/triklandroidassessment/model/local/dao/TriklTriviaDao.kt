package com.example.triklandroidassessment.model.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.triklandroidassessment.model.local.models.HighScore
import kotlinx.coroutines.flow.Flow

@Dao
interface TriklTriviaDao {
    @Query("SELECT * FROM HighScore ORDER BY id DESC LIMIT 1")
    fun getLatestScore(): Flow<HighScore?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addHighScore(highScore: HighScore)

    @Update
    suspend fun updateHighScore(highScore: HighScore)

}