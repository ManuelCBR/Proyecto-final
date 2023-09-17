package com.manuel.tpfitness

import android.R
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.manuel.tpfitness.databinding.ActivityMainBinding
import com.manuel.tpfitness.databinding.LayoutBottomSheetBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var binding2: LayoutBottomSheetBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Añadir estos set on click a los botones correspondientes
        binding.btnWorkoutList.setOnClickListener { navigateToWorkoutList() }
        binding.btnAddWorkout.setOnClickListener { showBottomSheet() }

    }


    private fun navigateToWorkoutList() {
        val intent = Intent(this, WorkoutActivity::class.java)
        startActivity(intent)
    }
    //Funcion para mostrar el bottom sheet
    private fun showBottomSheet() {

        val bottomSheetBinding = LayoutBottomSheetBinding.inflate(layoutInflater)
        val bottomSheetView = bottomSheetBinding.root

        val dialog = BottomSheetDialog(this)
        dialog.setContentView(bottomSheetView)
        dialog.show()

    }


}