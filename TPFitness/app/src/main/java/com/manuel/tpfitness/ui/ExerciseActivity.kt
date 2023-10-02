package com.manuel.tpfitness.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.manuel.tpfitness.database.TPFitnessDB
import com.manuel.tpfitness.database.entities.ExerciseEntity
import com.manuel.tpfitness.databinding.ActivityExerciseBinding
import kotlinx.coroutines.launch

class ExerciseActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var binding: ActivityExerciseBinding
    private lateinit var db: TPFitnessDB
    var itemSelected: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = TPFitnessDB.initDB(this)
        //Se reciben los parámetros del cardview en el que se clicke del recycleview

        val idExtra = intent.getIntExtra("idExercise", 0)
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
        /*Se habilitan los campos si no se reciben parametros (esto indicara que el usuario ha pulsado en añadir ejercicio
         y se desabilitan si los recibe (esto indicará que está viendo un ejercicio. Si los campos están vacíos, se rellenan los grupos
         musculares con los elementos en la base de datos para que el usuario pueda seleccionar*/
        if (nameExtra == "") {
            fieldsEnabled()
            binding.tvDelete.visibility = View.GONE
            lifecycleScope.launch {
            adapterSpinner.addAll(db.exerciseMuscleDao().getNameMuscleGroup())
            }
        } else fieldsNotEnabled()
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
            //Se muestra el botón para eliminar el ejercicio
            binding.tvDelete.isShown
            //Se establece la funcionalidad para actualizar un ejercicio existente
            binding.btnSave.setOnClickListener { updateExercise(db, idExtra) }

        }
        //Se establece la funcionalidad para guardar el ejercicio nuevo
        binding.btnSave.setOnClickListener { saveNewExercise(db)}

        //Se establece la funcionalidad para eliminar un ejercicio
        binding.tvDelete.setOnClickListener {
            val alert = AlertDialog.Builder(this)
            alert.setTitle("Alerta")
            alert.setMessage("¿Seguro que quieres elminar el ejercicio?")
            alert.setPositiveButton("Sí"){dialog, witch ->
                deleteExercise(db,idExtra)
                Toast.makeText(this, "Ejercicio Eliminado", Toast.LENGTH_SHORT).show()
            }
            alert.setNegativeButton("No"){dialog, witch ->
                navigateToBack()
            }
            val dialog: AlertDialog = alert.create()
            dialog.show()

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
        binding.btnSave.isEnabled = false
        binding.tvDelete.visibility = View.GONE
    }
    private fun fieldsEnabled(){
        binding.etWorkoutname.isEnabled = true
        binding.etDescriptionWorkout.isEnabled = true
        binding.spinnerMuscleGroup.isEnabled = true
        binding.btnSave.isEnabled = true
        binding.tvDelete.visibility = View.VISIBLE
    }
    private fun addFields(fieldName: String?, fieldDescription: String?, fieldBtn: String?){
        binding.etWorkoutname.setText(fieldName)
        binding.etDescriptionWorkout.setText(fieldDescription)

    }
    private fun saveNewExercise(room: TPFitnessDB){
        val name = binding.etWorkoutname.text.toString()
        val description = binding.etDescriptionWorkout.text.toString()

        lifecycleScope.launch {
            if (room.exerciseDao().getNameExercisesById(name) > 0) {
                Toast.makeText(
                    this@ExerciseActivity,
                    "Este ejercicio ya existe",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                room.exerciseDao().addExercise(ExerciseEntity(0, name, description, itemSelected))
                Toast.makeText(this@ExerciseActivity, "Ejercicio guardado correctmente", Toast.LENGTH_SHORT).show()
            }
        }
        navigateToBack()
    }
    private fun deleteExercise(room: TPFitnessDB, id: Int){
        lifecycleScope.launch {
            room.exerciseDao().delExercise(id)
        }
        navigateToBack()
    }
    private fun updateExercise(room: TPFitnessDB, id: Int){
        val name = binding.etWorkoutname.text.toString()
        val description = binding.etDescriptionWorkout.text.toString()
        lifecycleScope.launch {
                room.exerciseDao().updateExercise(ExerciseEntity(id, name, description, itemSelected))
                Toast.makeText(this@ExerciseActivity, "Ejercicio modificado correctmente", Toast.LENGTH_SHORT).show()
        }
        navigateToBack()
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        itemSelected = p2
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
    }


}