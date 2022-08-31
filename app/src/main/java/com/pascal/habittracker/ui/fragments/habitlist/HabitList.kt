package com.pascal.habittracker.ui.fragments.habitlist

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.pascal.habittracker.R
import com.pascal.habittracker.data.models.Habit
import com.pascal.habittracker.ui.fragments.habitlist.adapters.HabitListAdapter
import com.pascal.habittracker.ui.viewmodels.HabitViewModel

import kotlinx.android.synthetic.main.fragment_habit_list.*

class HabitList:Fragment(R.layout.fragment_habit_list) {

    private lateinit var habitList: List<Habit>
    private lateinit var habitViewModel:HabitViewModel
    private lateinit var adapter:HabitListAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //Adapter
        adapter= HabitListAdapter()
        rv_habits.adapter= adapter
        rv_habits.layoutManager= LinearLayoutManager(context)
        //View Model
        habitViewModel= ViewModelProvider(this)[HabitViewModel::class.java]

        habitViewModel.getAllHabits.observe(viewLifecycleOwner,Observer {
            adapter.setData(it)
            habitList=it

            if (it.isEmpty()){
                rv_habits.visibility= View.GONE
                tv_emptyView.visibility=View.VISIBLE
            }else{
                rv_habits.visibility= View.VISIBLE
                tv_emptyView.visibility=View.GONE
            }
        })

        setHasOptionsMenu(true)
        swipeToRefresh.setOnRefreshListener {
            adapter.setData(habitList)
            swipeToRefresh.isRefreshing=false
        }

        //takes you to create habit
        fab_add.setOnClickListener {
            findNavController().navigate(R.id.action_habitList_to_createHabitItem)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.nav_main,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.nav_delete -> habitViewModel.deleteAllHabits()
        }
        return super.onOptionsItemSelected(item)
    }
}