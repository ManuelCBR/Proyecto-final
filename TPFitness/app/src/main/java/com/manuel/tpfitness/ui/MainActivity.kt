package com.manuel.tpfitness.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.manuel.tpfitness.R
import com.manuel.tpfitness.database.TPFitnessDB
import com.manuel.tpfitness.database.entities.ExerciseEntity
import com.manuel.tpfitness.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var db: TPFitnessDB
    private var exerciseList: MutableList<ExerciseEntity> = mutableListOf()

    /*Se declara un companion object para usarlo como bandera y poder hacer uso del recycleview dependiendo
    de donde se haya llamado*/
    companion object{var origin = ""}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = TPFitnessDB.initDB(this)

        binding.btnExerciseList.setOnClickListener {
            getExercises(db)
            navigateToWorkoutList()
            origin = "session"
        }
        binding.btnAddWorkout.setOnClickListener {
            getExercises(db)
            navigateToSession()
            origin = "exercises"
        }

    }

    private fun navigateToWorkoutList() {
        val intent = Intent(this, ExerciseListActivity::class.java)
        startActivity(intent)
    }
    private fun navigateToSession(){
        val intent = Intent(this, SessionActivity::class.java)
        startActivity(intent)
    }
    private fun getExercises(room: TPFitnessDB){

        lifecycleScope.launch { exerciseList = room.exerciseDao().getExercises() }

    }

}