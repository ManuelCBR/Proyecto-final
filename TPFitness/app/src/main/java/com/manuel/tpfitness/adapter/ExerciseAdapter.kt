package com.manuel.tpfitness.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.manuel.tpfitness.R
import com.manuel.tpfitness.database.entities.ExerciseMuscleEntity
import com.manuel.tpfitness.ui.ExerciseActivity

class ExerciseAdapter(var context: Context, var exerciseList: MutableList<ExerciseMuscleEntity>) :
    RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>() {

    inner class ExerciseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val exerciseName: TextView

        init {
            exerciseName = itemView.findViewById(R.id.tvExerciseName)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        var layoutInflater =
            LayoutInflater.from(parent.context).inflate(R.layout.item_rv_exercise, parent, false)
        return ExerciseViewHolder(layoutInflater)

    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val exercise = exerciseList[position]//Cambiado
        holder.exerciseName.text = exercise.exercisesTable.nameExercise//Cambiado

        /* SetOnClick para que cuando cliquemos en un cardview de un recycleview pase a la activity ExerciseActivity para
        mostrar los campos del ejercicio */
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, ExerciseActivity::class.java)
            intent.putExtra("", exercise.exercisesTable.idExercise)
            intent.putExtra("nameExercise", exercise.exercisesTable.nameExercise)
            intent.putExtra("descriptionExercise", exercise.exercisesTable.descriptionExercise)
            intent.putExtra("muscleGroupExercise", exercise.muscleGroupTable.nameMuscleGroup)

            context.startActivity(intent)
        }


    }

    override fun getItemCount(): Int {
        return exerciseList.size
    }


}