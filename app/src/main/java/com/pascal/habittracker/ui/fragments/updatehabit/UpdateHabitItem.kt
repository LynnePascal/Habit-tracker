package com.pascal.habittracker.ui.fragments.updatehabit

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.pascal.habittracker.R
import com.pascal.habittracker.data.models.Habit
import com.pascal.habittracker.ui.viewmodels.HabitViewModel
import com.pascal.habittracker.utils.Calculations
import kotlinx.android.synthetic.main.fragment_create_habit_item.*
import kotlinx.android.synthetic.main.fragment_update_habit_item.*
import java.util.*

class UpdateHabitItem:Fragment(R.layout.fragment_update_habit_item),TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    private var title = ""
    private var description = ""
    private var drawableSelected = 0
    private var timeStamp = ""

    private var day = 0
    private var month = 0
    private var year = 0
    private var hour = 0
    private var minute = 0

    private var cleanDate = ""
    private var cleanTime = ""

    private lateinit var habitViewModel: HabitViewModel
    private val args by navArgs<UpdateHabitItemArgs>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        habitViewModel= ViewModelProvider(this)[HabitViewModel::class.java]

        et_habitTitle_update.setText(args.selectedHabit.habitTittle)
        et_habitDescription_update.setText(args.selectedHabit.habitDescription)

        drawableSelected()

        pickDateAndTime()

        btn_confirm_update.setOnClickListener {
            updateHabit()
        }
        setHasOptionsMenu(true)
    }
    private fun updateHabit(){
        title= et_habitTitle_update.text.toString()
        description=et_habitDescription_update.text.toString()

        timeStamp= "$cleanDate $cleanTime"

        if (!(title.isEmpty() || description.isEmpty() || timeStamp.isEmpty() || drawableSelected == 0)){
            val habit= Habit(args.selectedHabit.id, title, description, timeStamp, drawableSelected)

            habitViewModel.updateHabit(habit)
            Toast.makeText(context, "Habit updated successfully", Toast.LENGTH_SHORT).show()

            findNavController().navigate(R.id.action_updateHabitItem_to_habitList)
        }else {
            Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_SHORT).show()
        }
    }
    private fun drawableSelected() {
        iv_fastFoodSelected_update.setOnClickListener {
            iv_fastFoodSelected_update.isSelected = !iv_fastFoodSelected_update.isSelected
            drawableSelected = R.drawable.fastfood_selected

            //de-select the other options when we pick an image
            iv_smokingSelected_update.isSelected= false
            iv_gameSelected_update.isSelected = false
            iv_tiktok_update.isSelected= false
            iv_reading_update.isSelected= false
            iv_workout_update.isSelected= false
        }
        iv_gameSelected_update.setOnClickListener {
            iv_gameSelected_update.isSelected = !iv_gameSelected_update.isSelected
            drawableSelected = R.drawable.game_selected

            //de-select the other options when we pick an image
            iv_smokingSelected_update.isSelected = false
            iv_fastFoodSelected_update.isSelected = false
            iv_tiktok_update.isSelected= false
            iv_reading_update.isSelected= false
            iv_workout_update.isSelected= false

        }
        iv_smokingSelected_update.setOnClickListener {
            iv_smokingSelected_update.isSelected = !iv_smokingSelected_update.isSelected
            drawableSelected = R.drawable.smoking_selected

            //de-select the other options when we pick an image
            iv_fastFoodSelected_update.isSelected = false
            iv_gameSelected_update.isSelected = false
            iv_tiktok_update.isSelected= false
            iv_reading_update.isSelected= false
            iv_workout_update.isSelected= false


        }
        iv_reading_update.setOnClickListener {
            iv_reading_update.isSelected= !iv_reading_update.isSelected
            drawableSelected= R.drawable.reading_selected

            //de-select the other options when we pick an image
            iv_fastFoodSelected_update.isSelected = false
            iv_gameSelected_update.isSelected = false
            iv_tiktok_update.isSelected= false
            iv_smokingSelected_update.isSelected= false
            iv_workout_update.isSelected= false
        }

        iv_tiktok_update.setOnClickListener {
            iv_tiktok_update.isSelected= !iv_tiktok_update.isSelected
            drawableSelected= R.drawable.tiktok_selected

            //de-select the other options when we pick an image
            iv_fastFoodSelected_update.isSelected = false
            iv_gameSelected_update.isSelected = false
            iv_smokingSelected_update.isSelected= false
            iv_reading_update.isSelected= false
            iv_workout_update.isSelected= false

        }

        iv_workout_update.setOnClickListener {
            iv_workout_update.isSelected= !iv_workout_update.isSelected
            drawableSelected= R.drawable.workout_selected

            //de-select the other options when we pick an image
            iv_fastFoodSelected_update.isSelected = false
            iv_gameSelected_update.isSelected = false
            iv_tiktok_update.isSelected= false
            iv_reading_update.isSelected= false
            iv_smokingSelected_update.isSelected= false

        }
    }

    private fun pickDateAndTime(){
        btn_pickDate_update.setOnClickListener {
            getDateCalendar()
            DatePickerDialog(requireContext(),this,year,month,day).show()
        }
        btn_pickTime_update.setOnClickListener {
            getTimeCalendar()
            TimePickerDialog(context,this,hour,minute,true).show()
        }
    }
    //get the time set
    override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {

        cleanTime = Calculations.cleanTime(p1, p2)
        tv_timeSelected_update.text = "Time: $cleanTime"
    }

    //get the date set
    override fun onDateSet(p0: DatePicker?, yearX: Int, monthX: Int, dayX: Int) {

        cleanDate = Calculations.cleanDate(dayX, monthX, yearX)
        tv_dateSelected_update.text = "Date: $cleanDate"
    }

    //get the current time
    private fun getTimeCalendar() {
        val cal = Calendar.getInstance()
        hour = cal.get(Calendar.HOUR_OF_DAY)
        minute = cal.get(Calendar.MINUTE)
    }

    //get the current date
    private fun getDateCalendar() {
        val cal = Calendar.getInstance()
        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.MONTH)
        year = cal.get(Calendar.YEAR)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.single_item_menu,menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.nav_delete -> deleteHabit(args.selectedHabit)
        }

        return super.onOptionsItemSelected(item)
    }
    private fun deleteHabit(habit: Habit){
        habitViewModel.deleteHabit(habit)
        Toast.makeText(context, "Habit successfully deleted", Toast.LENGTH_SHORT).show()

        findNavController().navigate(R.id.action_updateHabitItem_to_habitList)
    }
}