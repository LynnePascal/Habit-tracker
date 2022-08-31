package com.pascal.habittracker.ui.fragments.createhabit

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.pascal.habittracker.R
import com.pascal.habittracker.data.models.Habit
import com.pascal.habittracker.ui.viewmodels.HabitViewModel
import com.pascal.habittracker.utils.Calculations
import kotlinx.android.synthetic.main.fragment_create_habit_item.*
import java.util.*

//Creating a habit
 class CreateHabitItem : Fragment(R.layout.fragment_create_habit_item),
    TimePickerDialog.OnTimeSetListener,DatePickerDialog.OnDateSetListener{


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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        habitViewModel = ViewModelProvider(this)[HabitViewModel::class.java]

        btn_confirm.setOnClickListener {
            addHabitToDB()
        }

        pickDateAndTime()

        drawableSelected()

    }
    private fun addHabitToDB(){
        //Get text from editTexts
        title= et_habitTitle.text.toString()
        description=et_habitDescription.text.toString()

        //Create a timestamp string for our recyclerview
        timeStamp="$cleanDate $cleanTime"

        //Check that the form is complete before submitting data to the database
        if (!(title.isEmpty() || description.isEmpty() || timeStamp.isEmpty() || drawableSelected == 0)) {
            val habit = Habit(0, title, description, timeStamp, drawableSelected)

            //add the habit if all the fields are filled
            habitViewModel.addHabit(habit)
            Toast.makeText(context, "Habit created successfully!", Toast.LENGTH_SHORT).show()

            //navigate back to our home fragment
            findNavController().navigate(R.id.action_createHabitItem_to_habitList)
        } else {
            Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_SHORT).show()
        }
    }
    //selected habit image
    private fun drawableSelected() {
        iv_fastFoodSelected.setOnClickListener {
            iv_fastFoodSelected.isSelected = !iv_fastFoodSelected.isSelected
            drawableSelected = R.drawable.fastfood_selected
            //de-select the other options when we pick an image
            iv_smokingSelected.isSelected = false
            iv_gameSelected.isSelected = false
            iv_reading.isSelected= false
            iv_tiktok.isSelected= false
            iv_workout.isSelected= false
        }
        iv_gameSelected.setOnClickListener {
            iv_gameSelected.isSelected = !iv_gameSelected.isSelected
            drawableSelected = R.drawable.game_selected

            //de-select the other options when we pick an image
            iv_smokingSelected.isSelected = false
            iv_fastFoodSelected.isSelected = false
            iv_reading.isSelected= false
            iv_tiktok.isSelected= false
            iv_workout.isSelected= false


        }
        iv_smokingSelected.setOnClickListener {
            iv_smokingSelected.isSelected = !iv_smokingSelected.isSelected
            drawableSelected = R.drawable.smoking_selected

            //de-select the other options when we pick an image
            iv_fastFoodSelected.isSelected = false
            iv_gameSelected.isSelected = false
            iv_reading.isSelected= false
            iv_tiktok.isSelected= false
            iv_workout.isSelected= false

        }

        iv_reading.setOnClickListener {
            iv_reading.isSelected= !iv_reading.isSelected
            drawableSelected= R.drawable.reading_selected

            //de-select the other options when we pick an image
            iv_fastFoodSelected.isSelected = false
            iv_gameSelected.isSelected = false
            iv_smokingSelected.isSelected= false
            iv_tiktok.isSelected= false
            iv_workout.isSelected= false
        }

        iv_tiktok.setOnClickListener {
            iv_tiktok.isSelected= !iv_tiktok.isSelected
            drawableSelected= R.drawable.tiktok_selected

            //de-select the other options when we pick an image
            iv_fastFoodSelected.isSelected = false
            iv_gameSelected.isSelected = false
            iv_reading.isSelected= false
            iv_smokingSelected.isSelected= false
            iv_workout.isSelected= false

        }

        iv_workout.setOnClickListener {
            iv_workout.isSelected= !iv_workout.isSelected
            drawableSelected= R.drawable.workout_selected

            //de-select the other options when we pick an image
            iv_fastFoodSelected.isSelected = false
            iv_gameSelected.isSelected = false
            iv_reading.isSelected= false
            iv_tiktok.isSelected= false
            iv_smokingSelected.isSelected= false

        }
    }
    private fun pickDateAndTime(){
        btn_pickDate.setOnClickListener {
            getDateCalendar()
            DatePickerDialog(requireContext(),this,year,month,day).show()
        }
        btn_pickTime.setOnClickListener {
            getTimeCalendar()
            TimePickerDialog(context,this,hour,minute,true).show()
        }
    }
    //get the time set
    override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {

        cleanTime = Calculations.cleanTime(p1, p2)
        tv_timeSelected.text = "Time: $cleanTime"
    }

    //get the date set
    override fun onDateSet(p0: DatePicker?, yearX: Int, monthX: Int, dayX: Int) {

        cleanDate = Calculations.cleanDate(dayX, monthX, yearX)
        tv_dateSelected.text = "Date: $cleanDate"
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
}