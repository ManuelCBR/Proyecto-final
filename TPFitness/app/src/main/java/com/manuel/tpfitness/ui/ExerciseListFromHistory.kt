package com.manuel.tpfitness.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.manuel.tpfitness.R
import com.manuel.tpfitness.adapter.ExerciseAdapter
import com.manuel.tpfitness.database.TPFitnessDB
import com.manuel.tpfitness.database.entities.ExerciseMuscleEntity
import com.manuel.tpfitness.databinding.ActivityExerciseListBinding
import com.manuel.tpfitness.databinding.ActivityExerciseListFromHistoryBinding
import kotlinx.coroutines.launch

class ExerciseListFromHistory : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var binding: ActivityExerciseListFromHistoryBinding
    private var exerciseMuscleList: MutableList<ExerciseMuscleEntity> = mutableListOf()
    private lateinit var adapter: ExerciseAdapter
    private lateinit var db: TPFitnessDB
    private lateinit var rv: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseListFromHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = TPFitnessDB.initDB(this)
        binding.iBtnBack.setOnClickListener { onBackPressed() }
        val adapterSpinner = ArrayAdapter<String>(
            this,R.layout.spinner_items
        )
        binding.spinnerMuscleGroup.adapter = adapterSpinner
        binding.spinnerMuscleGroup.onItemSelectedListener = this
        lifecycleScope.launch {
            adapterSpinner.add("Todos los Grupos Musculares")
            adapterSpinner.addAll(db.exerciseMuscleDao().getNameMuscleGroup())
        }

        getExercises(db)
        setAdapter()
        setFunctionItemsNavigationBar()

    }
    //Funcion para establecer el adaptador
    private fun setAdapter() {
        rv = binding.rvExercise
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = ExerciseAdapter(this, exerciseMuscleList)
    }
    //Funcion para obtener los ejercicios deseados
    private fun getExercises(room: TPFitnessDB) {

        lifecycleScope.launch {
            exerciseMuscleList = room.exerciseMuscleDao().getAllFromExerciseMuscle()
            adapter = ExerciseAdapter(this@ExerciseListFromHistory, exerciseMuscleList)
            binding.rvExercise.adapter = adapter
        }

    }
    //Fucion para obtener los ejercicios en base a los grupos musculares seleccionados

    private fun getExerciseByMuscleGroup(room: TPFitnessDB, id: Int) {
        lifecycleScope.launch {
            exerciseMuscleList = db.exerciseMuscleDao().getExerciseById(id)
            adapter = ExerciseAdapter(this@ExerciseListFromHistory, exerciseMuscleList)
            binding.rvExercise.adapter = adapter
        }
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
    //Funciones para darle funcionalidad a los items del Spinner
    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

        if (p2 == 0){ getExercises(db)

        }else getExerciseByMuscleGroup(db, p2)
    }
    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

}