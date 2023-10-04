package com.manuel.tpfitness.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.manuel.tpfitness.adapter.ExerciseAdapter
import com.manuel.tpfitness.database.TPFitnessDB
import com.manuel.tpfitness.database.entities.ExerciseEntity
import com.manuel.tpfitness.database.entities.ExerciseMuscleEntity
import com.manuel.tpfitness.databinding.ActivityExerciseListBinding
import com.manuel.tpfitness.databinding.ActivityMainBinding
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var db: TPFitnessDB
    private var exerciseList: MutableList<ExerciseEntity> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = TPFitnessDB.initDB(this)

        binding.btnExerciseList.setOnClickListener {
            getExercises(db)
            navigateToWorkoutList()
        }
        binding.btnAddWorkout.setOnClickListener {
            getExercises(db)
            navigateToSession()
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