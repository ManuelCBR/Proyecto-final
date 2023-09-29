package com.manuel.tpfitness.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.manuel.tpfitness.R
import com.manuel.tpfitness.adapter.ExerciseAdapter
import com.manuel.tpfitness.database.TPFitnessDB
import com.manuel.tpfitness.database.entities.ExerciseEntity
import com.manuel.tpfitness.database.entities.ExerciseMuscleEntity
import com.manuel.tpfitness.databinding.ActivityExerciseListBinding
import com.manuel.tpfitness.databinding.LayoutBottomSheetBinding
import kotlinx.coroutines.launch
import java.util.Arrays

class ExerciseListActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var binding: ActivityExerciseListBinding
    private var exerciseList: MutableList<ExerciseEntity> = mutableListOf()
    private var exerciseMuscleList: MutableList<ExerciseMuscleEntity> = mutableListOf()
    private lateinit var adapter: ExerciseAdapter
    private lateinit var db: TPFitnessDB
    private lateinit var rv: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseListBinding.inflate(layoutInflater)
        var bottomSheetBinding: LayoutBottomSheetBinding =
            LayoutBottomSheetBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = TPFitnessDB.initDB(this)
        val adapterSpinner = ArrayAdapter<String>(
            this,
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item
        )
        binding.spinnerMuscleGroup.adapter = adapterSpinner
        binding.spinnerMuscleGroup.onItemSelectedListener = this
        lifecycleScope.launch {
            adapterSpinner.add("Todos los Grupos Musculares")
            adapterSpinner.addAll(db.exerciseMuscleDao().getNameMuscleGroup())
        }

        getExercises(db)
        setAdapter()
        //binding.btnMuscleGroups.setOnClickListener {showBottomSheet()}
        binding.btnAddExercise.setOnClickListener { navigateToExercise() }
        binding.iBtnBack.setOnClickListener { navigateToBack() }

    }

    //Funcion para mostrar el bottom sheet
    private fun showBottomSheet() {

        var bottomSheetBinding: LayoutBottomSheetBinding =
            LayoutBottomSheetBinding.inflate(layoutInflater)
        val bottomSheetView = bottomSheetBinding.root
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(bottomSheetView)
        dialog.show()


    }

    //Funcion para ir al listado de ejercicios
    private fun navigateToExercise() {
        val intent = Intent(this, ExerciseActivity::class.java)
        startActivity(intent)
    }

    //Funcion para volver al activity anterior
    private fun navigateToBack() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    //Funcion para establecer el adaptador
    private fun setAdapter() {
        rv = binding.rvExercise
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = ExerciseAdapter(this, exerciseMuscleList)
    }

    //Funcion para obtener los ejercicios deseados
    private fun getExercises(room: TPFitnessDB) {

        lifecycleScope.launch {
            exerciseMuscleList = room.exerciseMuscleDao().getAllFromExerciseMuscle()
            adapter = ExerciseAdapter(this@ExerciseListActivity, exerciseMuscleList)
            binding.rvExercise.adapter = adapter
        }

    }

    private fun getExerciseByMuscleGroup(room: TPFitnessDB, id: Int) {
        lifecycleScope.launch {
            exerciseMuscleList = db.exerciseMuscleDao().getExerciseById(id)
            adapter = ExerciseAdapter(this@ExerciseListActivity, exerciseMuscleList)
            binding.rvExercise.adapter = adapter
        }
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

        when (p2) {
            0 -> getExercises(db)
            1 -> getExerciseByMuscleGroup(db, 1)
            2 -> getExerciseByMuscleGroup(db, 2)
            3 -> getExerciseByMuscleGroup(db, 3)
            4 -> getExerciseByMuscleGroup(db, 4)
            5 -> getExerciseByMuscleGroup(db, 5)
            6 -> getExerciseByMuscleGroup(db, 6)
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

        lifecycleScope.launch {
            exerciseMuscleList = db.exerciseMuscleDao().getAllFromExerciseMuscle()
            adapter = ExerciseAdapter(this@ExerciseListActivity, exerciseMuscleList)
            binding.rvExercise.adapter = adapter
        }
    }

}