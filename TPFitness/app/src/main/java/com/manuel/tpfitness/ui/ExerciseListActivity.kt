package com.manuel.tpfitness.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.manuel.tpfitness.adapter.ExerciseAdapter
import com.manuel.tpfitness.database.entities.ExerciseEntity
import com.manuel.tpfitness.databinding.ActivityExerciseListBinding
import com.manuel.tpfitness.databinding.LayoutBottomSheetBinding

class ExerciseListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityExerciseListBinding
    private var exerciseList: MutableList<ExerciseEntity> = mutableListOf()
    private lateinit var rv: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        exerciseList.add(ExerciseEntity(1,"Press banca inclinado", ""))
        exerciseList.add(ExerciseEntity(1,"Press banca horizontal", ""))
        exerciseList.add(ExerciseEntity(1,"Press banca inclinado", ""))
        exerciseList.add(ExerciseEntity(1,"Press banca horizontal", ""))
        exerciseList.add(ExerciseEntity(1,"Press banca inclinado", ""))
        exerciseList.add(ExerciseEntity(1,"Press banca horizontal", ""))
        exerciseList.add(ExerciseEntity(1,"Press banca inclinado", ""))
        exerciseList.add(ExerciseEntity(1,"Press banca horizontal", ""))
        exerciseList.add(ExerciseEntity(1,"Press banca inclinado", ""))
        exerciseList.add(ExerciseEntity(1,"Press banca horizontal", ""))
        exerciseList.add(ExerciseEntity(1,"Press banca inclinado", ""))
        exerciseList.add(ExerciseEntity(1,"Press banca horizontal", ""))
        exerciseList.add(ExerciseEntity(1,"Press banca inclinado", ""))
        exerciseList.add(ExerciseEntity(1,"Press banca horizontal", ""))

        setAdapter()


        binding.btnMuscleGroups.setOnClickListener { showBottomSheet() }
        binding.btnAddExercise.setOnClickListener { navigateToExercise() }
        binding.iBtnBack.setOnClickListener { navigateToBack() }
    }

    //Funcion para mostrar el bottom sheet
    private fun showBottomSheet() {

        val bottomSheetBinding = LayoutBottomSheetBinding.inflate(layoutInflater)
        val bottomSheetView = bottomSheetBinding.root

        val dialog = BottomSheetDialog(this)
        dialog.setContentView(bottomSheetView)
        dialog.show()

    }

    private fun navigateToExercise(){
        val intent = Intent(this, WorkoutActivity::class.java)
        startActivity(intent)
    }
    private fun navigateToBack(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
    private fun setAdapter(){
        rv = binding.rvExercise
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = ExerciseAdapter(this, exerciseList)
    }
}