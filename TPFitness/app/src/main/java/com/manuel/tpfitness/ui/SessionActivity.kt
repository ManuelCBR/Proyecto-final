package com.manuel.tpfitness.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.manuel.tpfitness.R
import com.manuel.tpfitness.adapter.DatePickerFragment
import com.manuel.tpfitness.adapter.ExerciseAdapter
import com.manuel.tpfitness.database.TPFitnessDB
import com.manuel.tpfitness.database.entities.ExerciseMuscleEntity
import com.manuel.tpfitness.database.entities.SessionEntity
import com.manuel.tpfitness.databinding.ActivitySessionBinding
import kotlinx.coroutines.launch

class SessionActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var binding: ActivitySessionBinding
    private var exerciseMuscleList: MutableList<ExerciseMuscleEntity> = mutableListOf()
    private lateinit var adapter: ExerciseAdapter
    private lateinit var db: TPFitnessDB
    private lateinit var rv: RecyclerView

    companion object {
        var date = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySessionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = TPFitnessDB.initDB(this)
        val adapterSpinner = ArrayAdapter<String>(
            this, R.layout.spinner_items
        )
        binding.spinnerMuscleGroup.adapter = adapterSpinner
        binding.spinnerMuscleGroup.onItemSelectedListener = this
        binding.iBtnBack.setOnClickListener { onBackPressed() }
        binding.icDatePicker.setOnClickListener {showDatePicker() }
        binding.tvSave.setOnClickListener {
            lifecycleScope.launch {
                val isDateSession = db.sessionDao().getDatesSession()
                if (isDateSession.contains(date)) {
                    Toast.makeText(
                        this@SessionActivity,
                        "Ya hay un entrenamiento en la fecha seleccionada, por favor, seleccione otra",
                        Toast.LENGTH_SHORT
                    ).show()
                }else{saveSession()}
            }
        }
        lifecycleScope.launch {
            adapterSpinner.add("Todos los Grupos Musculares")
            adapterSpinner.addAll(db.exerciseMuscleDao().getNameMuscleGroup())
        }
        getExercises(db)
        setAdapter()
        setFunctionItemsNavigationBar()
    }

    //Funcino para establecer el adaptador del recycleView
    private fun setAdapter() {
        rv = binding.rvExercise
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = ExerciseAdapter(this, exerciseMuscleList)
    }

    //Funcion para obtener los ejercicios
    private fun getExercises(room: TPFitnessDB) {

        lifecycleScope.launch {

            exerciseMuscleList = room.exerciseMuscleDao().getAllFromExerciseMuscle()
            adapter = ExerciseAdapter(this@SessionActivity, exerciseMuscleList)
            binding.rvExercise.adapter = adapter
        }

    }

    private fun getExerciseByMuscleGroup(room: TPFitnessDB, id: Int) {
        lifecycleScope.launch {
            exerciseMuscleList = db.exerciseMuscleDao().getExerciseById(id)
            adapter = ExerciseAdapter(this@SessionActivity, exerciseMuscleList)
            binding.rvExercise.adapter = adapter
        }
    }

    //Se establecen las funciones de los botones del bottom navigation view
    fun setFunctionItemsNavigationBar() {
        binding.myBottomNavigation.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.itm_home -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
                R.id.itm_history -> {
                    val intent = Intent(this, HistoryActivity::class.java)
                    startActivity(intent)
                }
            }
            true
        }
    }

    //Funcion para mostrar el date piker
    private fun showDatePicker() {
        val datePicker = DatePickerFragment { day, month, year ->
            selectedDate(day, month, year)
        }
        datePicker.show(supportFragmentManager, "datePicker")
    }
    //Funcion para establecer la fecha del datePicker
    private fun selectedDate(day: Int, month: Int, year: Int): String {
        val monthPlus = month + 1
        date = "$day-$monthPlus-$year"

        return date
    }

    //Funcion para guardar la sesion
    private fun saveSession() {
        lifecycleScope.launch {
            /*Se recupera la ultima sesion guardada y el nombre que haya introducido el usuario*/
            val lastSession = db.sessionDao().getLastId()
            val nameSession = binding.etSessionName.text.toString()
            /*Se controla que si el nombre o la fecha estan vacios que lance un Toast*/
            if (nameSession == "") {
                Toast.makeText(
                    this@SessionActivity,
                    "Introduce un nombre para la sesión",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (date == "") {
                Toast.makeText(
                    this@SessionActivity,
                    "Introduce una fecha para la sesión",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                /*Se controla de donde viene ya que debe sustituir la ultima sesion en caso de que haya
                introducido algun ejercicio, si no querra decir que esta sobreescribiendo una sesion que
                no corresponde, y lanzara un Toast*/
                if (MainActivity.origin == "fromSerie") {

                    db.sessionDao().updateSession(SessionEntity(lastSession, nameSession, date))
                    Toast.makeText(this@SessionActivity, "Sesión guardada", Toast.LENGTH_SHORT)
                        .show()
                    onBackPressed()

                } else {
                    Toast.makeText(
                        this@SessionActivity,
                        "No has elegido ejercicios para esta sesión",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    //Funciones para darle funcionalidad a los items del Spinner
    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

        if (p2 == 0) {
            getExercises(db)

        } else getExerciseByMuscleGroup(db, p2)
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

        getExercises(db)
    }

}