package com.manuel.tpfitness

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.manuel.tpfitness.databinding.ActivityWorkoutBinding

class WorkoutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWorkoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.iBtnBack.setOnClickListener{navigateToBack()}
    }

    private fun navigateToBack() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}