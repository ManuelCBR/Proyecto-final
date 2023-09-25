package com.manuel.tpfitness.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.manuel.tpfitness.R
import com.manuel.tpfitness.database.entities.ExerciseEntity
import com.manuel.tpfitness.ui.MainActivity

class ExerciseAdapter(var context: Context, var exerciseList: MutableList<ExerciseEntity>): RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>() {

    inner class ExerciseViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        lateinit var exerciseName: TextView
        init {
            exerciseName = itemView.findViewById(R.id.tvExerciseName)
        }
    /*val exercise = view.findViewById<TextView>(R.id.tvExerciseName)
    fun render (exerciseEntity: ExerciseEntity){
        exercise.text = exerciseEntity.nameExercise
    }*/
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        var layoutInflater = LayoutInflater.from(parent.context).inflate(R.layout.item_rv_exercise, parent, false)
        return ExerciseViewHolder(layoutInflater)

    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        var exercise = exerciseList[position]
        holder.exerciseName.text = exercise.nameExercise
    }
    override fun getItemCount(): Int {
        return exerciseList.size
    }





}