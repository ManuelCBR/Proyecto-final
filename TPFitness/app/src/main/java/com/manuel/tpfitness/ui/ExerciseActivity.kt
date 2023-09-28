package com.manuel.tpfitness.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.manuel.tpfitness.database.TPFitnessDB
import com.manuel.tpfitness.database.entities.ExerciseEntity
import com.manuel.tpfitness.databinding.ActivityExerciseBinding
import com.manuel.tpfitness.databinding.LayoutBottomSheetBinding
import kotlinx.coroutines.launch

class ExerciseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExerciseBinding
    private lateinit var exercise: ExerciseEntity
    private lateinit var db: TPFitnessDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val nameExtra = intent.getStringExtra("nameExercise")
        val descriptionExtra = intent.getStringExtra("descriptionExercise")
        val muscleGroupExtra = intent.getStringExtra("muscleGroupExercise")

        addFields(nameExtra, descriptionExtra, muscleGroupExtra)
        fieldsNotEnabled()
        binding.iBtnBack.setOnClickListener{navigateToBack()}

        binding.tvEdit.setOnClickListener {
            fieldsEnabled()
            binding.btnMuscleGroups.setOnClickListener {showBottomSheet()}
        }
        binding.btnMuscleGroups.setOnClickListener {showBottomSheet()}
    }
    //Funcion para mostrar el bottom sheet
    private fun showBottomSheet() {
        val bottomSheetBinding = LayoutBottomSheetBinding.inflate(layoutInflater)
        val bottomSheetView = bottomSheetBinding.root
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(bottomSheetView)
        dialog.show()

    }

    private fun navigateToBack() {
        val intent = Intent(this, ExerciseListActivity::class.java)
        startActivity(intent)
    }
    private fun fieldsNotEnabled(){
        binding.etWorkoutname.isEnabled = false
        binding.etDescriptionWorkout.isEnabled = false
        binding.btnMuscleGroups.isEnabled = false
    }
    private fun fieldsEnabled(){
        binding.etWorkoutname.isEnabled = true
        binding.etDescriptionWorkout.isEnabled = true
        binding.btnMuscleGroups.isEnabled = true
    }
    private fun addFields(fieldName: String?, fieldDescription: String?, fieldBtn: String?){
        binding.etWorkoutname.setText(fieldName)
        binding.etDescriptionWorkout.setText(fieldDescription)
        binding.btnMuscleGroups.setText(fieldBtn)
    }

    private fun saveExercise(room: TPFitnessDB, exercise: ExerciseEntity){
        lifecycleScope.launch {
            room.exerciseDao().addExercise(exercise)
        }
    }

}