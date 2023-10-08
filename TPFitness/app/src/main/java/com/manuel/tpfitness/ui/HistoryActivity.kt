package com.manuel.tpfitness.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.manuel.tpfitness.R
import com.manuel.tpfitness.databinding.ActivityHistoryBinding

class HistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setFunctionItemsNavigationBar()
    }
    fun setFunctionItemsNavigationBar(){
        binding.myBottomNavigation.setOnItemSelectedListener {menuItem ->
            when (menuItem.itemId){
                R.id.itm_home -> {
                    val intent = Intent (this, MainActivity::class.java)
                    startActivity(intent)
                }
                R.id.itm_history -> {
                    val intent = Intent (this, HistoryActivity::class.java)
                    startActivity(intent)
                }

            }
            true
        }
    }
}