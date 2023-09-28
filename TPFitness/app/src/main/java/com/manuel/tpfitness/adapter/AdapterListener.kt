package com.manuel.tpfitness.adapter

import com.manuel.tpfitness.database.entities.ExerciseEntity

interface AdapterListener {
    fun onEditItemClick(position: Int)
}