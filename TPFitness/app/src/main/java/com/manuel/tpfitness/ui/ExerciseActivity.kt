package com.manuel.tpfitness.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.lifecycleScope
import com.manuel.tpfitness.database.TPFitnessDB
import com.manuel.tpfitness.database.entities.ExerciseEntity
import com.manuel.tpfitness.databinding.ActivityExerciseBinding
import kotlinx.coroutines.launch

class ExerciseActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var binding: ActivityExerciseBinding
    private lateinit var db: TPFitnessDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = TPFitnessDB.initDB(this)
        //Se reciben los parámetros del cardview en el que se clicke del recycleview
        val nameExtra = intent.getStringExtra("nameExercise")
        val descriptionExtra = intent.getStringExtra("descriptionExercise")
        val muscleGroupExtra = intent.getStringExtra("muscleGroupExercise")

        //Se establece el Spinner
        val adapterSpinner = ArrayAdapter<String>(
            this, com.manuel.tpfitness.R.layout.spinner_items
        )
        binding.spinnerMuscleGroup.adapter = adapterSpinner
        binding.spinnerMuscleGroup.onItemSelectedListener = this
        //Se añade el valor recibido al spinner
        adapterSpinner.add(muscleGroupExtra)
        //Se añaden los valores recibidos a los campos
        addFields(nameExtra, descriptionExtra, muscleGroupExtra)
        //Se deshabilitan los campos para que sea solo tipo vista
        fieldsNotEnabled()
        //Boton atras
        binding.iBtnBack.setOnClickListener{navigateToBack()}
        //Se añade la funcionalidad para que esten los campos bloqueados hasta que se clicke en edit
        binding.tvEdit.setOnClickListener {
            //Se habilitan los campos para que el usuario pueda editar el ejercicio
            fieldsEnabled()
            //Se limpia el spinner para que pueda recoger todos los grupos musculares
            adapterSpinner.clear()
            //Se llama a la base de datos para rellenar el spinner
            lifecycleScope.launch {
                adapterSpinner.add("Grupos Musculares")
                adapterSpinner.addAll(db.exerciseMuscleDao().getNameMuscleGroup())
            }
            //Se establece la funcionalidad para guardar el ejercicio nuevo
            binding.btnSave.setOnClickListener {
                val etnameWorkout = binding.etWorkoutname.text.toString()
                val etDescription = binding.etDescriptionWorkout.text.toString()
            }
        }
    }

    private fun navigateToBack() {
        val intent = Intent(this, ExerciseListActivity::class.java)
        startActivity(intent)
    }
    private fun fieldsNotEnabled(){
        binding.etWorkoutname.isEnabled = false
        binding.etDescriptionWorkout.isEnabled = false
        binding.spinnerMuscleGroup.isEnabled = false
    }
    private fun fieldsEnabled(){
        binding.etWorkoutname.isEnabled = true
        binding.etDescriptionWorkout.isEnabled = true
        binding.spinnerMuscleGroup.isEnabled = true
    }
    private fun addFields(fieldName: String?, fieldDescription: String?, fieldBtn: String?){
        binding.etWorkoutname.setText(fieldName)
        binding.etDescriptionWorkout.setText(fieldDescription)

    }

    private fun saveExercise(room: TPFitnessDB, exercise: ExerciseEntity){
        lifecycleScope.launch {
            room.exerciseDao().addExercise(exercise)
        }
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

        //Revisar
        lifecycleScope.launch {
            val itemSeleccionado = p2
        }


    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }


}