package com.manuel.tpfitness.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.manuel.tpfitness.adapter.ExerciseAdapter
import com.manuel.tpfitness.database.TPFitnessDB
import com.manuel.tpfitness.database.entities.ExerciseEntity
import com.manuel.tpfitness.database.entities.ExerciseMuscleEntity
import com.manuel.tpfitness.databinding.ActivityExerciseListBinding
import com.manuel.tpfitness.databinding.LayoutBottomSheetBinding
import kotlinx.coroutines.launch

class ExerciseListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityExerciseListBinding
    private var exerciseList: MutableList<ExerciseEntity> = mutableListOf()
    private var exerciseMuscleList: MutableList<ExerciseMuscleEntity> = mutableListOf()
    private lateinit var adapter: ExerciseAdapter
    private lateinit var db: TPFitnessDB
    private lateinit var rv: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = TPFitnessDB.initDB(this)



        Log.e("Manuel", getExercises(db).toString())

        binding.btnMuscleGroups.setOnClickListener { showBottomSheet() }
        binding.btnAddExercise.setOnClickListener { navigateToExercise() }
        binding.iBtnBack.setOnClickListener { navigateToBack() }
        getExercises(db)
        setAdapter()
    }

    //Funcion para mostrar el bottom sheet
    private fun showBottomSheet() {

        val bottomSheetBinding = LayoutBottomSheetBinding.inflate(layoutInflater)
        val bottomSheetView = bottomSheetBinding.root
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(bottomSheetView)
        dialog.show()

    }

    //Funcion para ir al listado de ejercicios
    private fun navigateToExercise(){
        val intent = Intent(this, ExerciseActivity::class.java)
        startActivity(intent)
    }

    //Funcion para volver al activity anterior
    private fun navigateToBack(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    //Funcion para establecer el adaptador
    private fun setAdapter(){
        rv = binding.rvExercise
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = ExerciseAdapter(this, exerciseMuscleList)
    }

    //Funcion para obtener los ejercicios deseados
    private fun getExercises(room: TPFitnessDB){
        val bottomSheetBinding = LayoutBottomSheetBinding.inflate(layoutInflater)
                lifecycleScope.launch {
                    exerciseMuscleList = room.exerciseMuscleDao().getAllFromExerciseMuscle()
                    adapter = ExerciseAdapter(this@ExerciseListActivity, exerciseMuscleList)
                    binding.rvExercise.adapter =adapter
        }
    }
}