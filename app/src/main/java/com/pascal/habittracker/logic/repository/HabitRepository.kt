package com.pascal.habittracker.logic.repository

import androidx.lifecycle.LiveData
import com.pascal.habittracker.data.models.Habit
import com.pascal.habittracker.logic.dao.HabitDao

class HabitRepository(private val habitDao:HabitDao){
    val getAllHabits:LiveData<List<Habit>> = habitDao.getAllHabits()

    suspend fun addHabit(habit: Habit){
        habitDao.addHabit(habit)
    }
    suspend fun updateHabit(habit: Habit){
        habitDao.updateHabit(habit)
    }
    suspend fun deleteHabit(habit: Habit){
        habitDao.deleteHabit(habit)
    }
    suspend fun deleteAll(){
        habitDao.deleteAll()
    }


}