package com.manuel.tpfitness.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.manuel.tpfitness.R
import com.manuel.tpfitness.adapter.ExerciseAdapter
import com.manuel.tpfitness.database.TPFitnessDB
import com.manuel.tpfitness.database.entities.ExerciseMuscleEntity
import com.manuel.tpfitness.databinding.ActivitySessionBinding
import kotlinx.coroutines.launch

class SessionActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var binding: ActivitySessionBinding
    private var exerciseMuscleList: MutableList<ExerciseMuscleEntity> = mutableListOf()
    private lateinit var adapter: ExerciseAdapter
    private lateinit var db: TPFitnessDB
    private lateinit var rv: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySessionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = TPFitnessDB.initDB(this)
        val adapterSpinner = ArrayAdapter<String>(
            this,R.layout.spinner_items
        )
        binding.spinnerMuscleGroup.adapter = adapterSpinner
        binding.spinnerMuscleGroup.onItemSelectedListener = this
        binding.iBtnBack.setOnClickListener { navigateToBack() }
        lifecycleScope.launch {
            adapterSpinner.add("Todos los Grupos Musculares")
            adapterSpinner.addAll(db.exerciseMuscleDao().getNameMuscleGroup())
        }
        getExercises(db)
        setAdapter()
    }
    //Funcion para volver al activity anterior y eliminar la sesion
    private fun navigateToBack() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
    private fun setAdapter() {
        rv = binding.rvExercise
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = ExerciseAdapter(this, exerciseMuscleList)
    }
    private fun getExercises(room: TPFitnessDB) {

        lifecycleScope.launch {
            exerciseMuscleList = room.exerciseMuscleDao().getAllFromExerciseMuscle()
            adapter = ExerciseAdapter(this@SessionActivity, exerciseMuscleList)
            binding.rvExercise.adapter = adapter
        }

    }
    private fun getExerciseByMuscleGroup(room: TPFitnessDB, id: Int) {
        lifecycleScope.launch {
            exerciseMuscleList = db.exerciseMuscleDao().getExerciseById(id)
            adapter = ExerciseAdapter(this@SessionActivity, exerciseMuscleList)
            binding.rvExercise.adapter = adapter
        }
    }
    //Funciones para darle funcionalidad a los items del Spinner
    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

        if (p2 == 0){ getExercises(db)

        }else getExerciseByMuscleGroup(db, p2)
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

        getExercises(db)
    }

}