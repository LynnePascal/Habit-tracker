package com.pascal.habittracker.logic.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.pascal.habittracker.data.models.Habit

@Dao // Data Access Object
interface HabitDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE) // ignore sth with the same id
    suspend fun addHabit(habit:Habit)

    @Update
    suspend fun updateHabit(habit:Habit)

    @Delete
    suspend fun deleteHabit(habit: Habit)

    @Query ("SELECT * FROM habit_table ORDER BY id DESC")
    fun getAllHabits():LiveData<List<Habit>>

    @Query ("DELETE FROM habit_table")
    suspend fun deleteAll()
}