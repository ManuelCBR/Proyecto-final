package com.manuel.tpfitness.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.manuel.tpfitness.databinding.ActivityWorkoutBinding
import com.manuel.tpfitness.databinding.LayoutBottomSheetBinding

class WorkoutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWorkoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnMuscleGroups.setOnClickListener { showBottomSheet() }
        binding.iBtnBack.setOnClickListener{navigateToBack()}
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
}