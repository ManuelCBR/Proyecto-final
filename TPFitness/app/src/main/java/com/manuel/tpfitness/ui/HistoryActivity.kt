package com.manuel.tpfitness.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.manuel.tpfitness.R
import com.manuel.tpfitness.adapter.DatePickerFragment
import com.manuel.tpfitness.database.TPFitnessDB
import com.manuel.tpfitness.database.entities.FullSession
import com.manuel.tpfitness.databinding.ActivityHistoryBinding
import kotlinx.coroutines.launch

class HistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding
    private lateinit var db: TPFitnessDB
    private var fullSessionList: MutableList<FullSession> = mutableListOf()
    private var cardViewCounter = 0
    private lateinit var tvSerie: TextView
    private var date = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = TPFitnessDB.initDB(this)

        binding.btnAddExercise.isVisible = false
        binding.btnDate.setOnClickListener { showDatePicker() }
        binding.tvEdit.setOnClickListener {
            binding.btnDate.isVisible = false
            binding.btnAddExercise.isVisible = true
        }
        binding.iBtnBack.setOnClickListener { onBackPressed() }

        setFunctionItemsNavigationBar()
    }

    /*Funcion que sobreescribe onResume para que cuando vuelva a esta activity de haber eliminado
    un ejercicio, se recargue la sesion con la informacion actualizada*/
    override fun onResume() {
        super.onResume()
        binding.containerSession.removeAllViews()
        showSession()
    }

    //Funcion para mostrar la sesion
    private fun showSession() {
        lifecycleScope.launch {
            var idExercise = 0

            fullSessionList = db.sessionDao().getFullSession(date)

            for (fullSession in fullSessionList) {

                binding.tvNameSession.text = fullSession.session.nameSession

                if (idExercise != fullSession.idExercise) {

                    cardViewCounter++
                    idExercise = fullSession.idExercise
                    addCVExercise(fullSession.nameExercise)
                    addCVSeries(
                        cardViewCounter,
                        fullSession.idSerie,
                        fullSession.weight,
                        fullSession.reps
                    )

                } else {
                    addCVSeries(
                        cardViewCounter,
                        fullSession.idSerie,
                        fullSession.weight,
                        fullSession.reps
                    )

                }
            }
        }
    }

    //Funcion para añadir los ejercicios al cardview ejercicio de forma dinamica
    private fun addCVExercise(nameExercise: String) {

        val cvExercise = CardView(this@HistoryActivity)
        val newCVExercise = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        newCVExercise.setMargins(0, 8, 0, 8)
        cvExercise.layoutParams = newCVExercise
        cvExercise.setCardBackgroundColor(
            ContextCompat.getColor(
                this@HistoryActivity,
                R.color.background
            )
        )
        cvExercise.cardElevation = 0f

        //Inflamos el diseño de las card desde el XML
        val customCardContent =
            LayoutInflater.from(this@HistoryActivity)
                .inflate(R.layout.item_cv_exercise, null, false) as ViewGroup
        cvExercise.addView(customCardContent)
        // Se encuentra el LinearLayout en el CardView padre
        var contenExercises = findViewById<LinearLayout>(R.id.containerSession)
        contenExercises.addView(cvExercise)
        /* Se establece un setOnClickListener para que cuando el usuario pulse un
        cardview, pueda editarlo */
        cvExercise.setOnClickListener {
            val intent = Intent (this, HistoryEditActivity::class.java)
            lifecycleScope.launch {
                val idExercise = db.exerciseDao().getIdExercisesByName(nameExercise)
                val idSession = db.sessionDao().sessionByDate(date)
                intent.putExtra("idExercise", idExercise)
                intent.putExtra("idSession", idSession)
                startActivity(intent)
            }
        }
        //Se establece el nombre del ejercicio
        cvExercise.findViewById<TextView>(R.id.tvCVExerciseName).text = nameExercise
        cvExercise.id = cardViewCounter
    }

    //Funcion para añadir la serires al cardview ejercicio de forma dinamica
    private fun addCVSeries(cvExerciseId: Int, idSerie: Int, kg: Int, reps: Int) {

        val cvSerie = CardView(this)
        val newCVserie = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        newCVserie.setMargins(0, 0, 0, 0)
        cvSerie.layoutParams = newCVserie
        cvSerie.setCardBackgroundColor(ContextCompat.getColor(this, R.color.cards))
        //Inflamos el diseño de las card desde el XML
        val customCardContent =
            LayoutInflater.from(this).inflate(R.layout.item_cv_series, null, false) as ViewGroup
        //Se agrega la vista al card
        cvSerie.addView(customCardContent)
        //Se deshabilitan los EditText
        disableET(customCardContent)
        tvSerie = cvSerie.findViewById(R.id.tvCvSerie)
        tvSerie.setText((idSerie).toString())
        val etKg = cvSerie.findViewById<EditText>(R.id.etKg)
        val etReps = cvSerie.findViewById<EditText>(R.id.etReps)
        etKg.setText(kg.toString())
        etReps.setText(reps.toString())
        //Se agrega la card al container (layout)
        val parentCV = findViewById<CardView>(cvExerciseId)
        if (parentCV != null) {
            // Se encuentra el LinearLayout en el CardView padre
            val contenSeries = parentCV.findViewById<LinearLayout>(R.id.contentSeries)
            contenSeries.addView(cvSerie)
        } else {
            Toast.makeText(this, "No se han encontrado cardviews", Toast.LENGTH_SHORT).show()
        }
    }

    //Funcion para eliminar una sesion
    private fun deleteSession(date: String) {
        val alert = AlertDialog.Builder(this)
        alert.setTitle("Alerta")
        alert.setMessage("¿Seguro que quieres eliminar esta sesión?")
        alert.setPositiveButton("Sí") { dialog, witch ->
            lifecycleScope.launch { db.sessionDao().delSession(date) }
            Toast.makeText(this, "Sesión eliminada", Toast.LENGTH_SHORT).show()
            onBackPressed()
        }
        alert.setNegativeButton("No") { dialog, witch ->

        }
        val dialog: AlertDialog = alert.create()
        dialog.show()

    }

    //Funcion para deshabilitar los EditText
    private fun disableET(cardContent: ViewGroup) {
        for (i in 0 until cardContent.childCount) {
            val child = cardContent.getChildAt(i)
            if (child is EditText) {
                child.isEnabled = false
            } else if (child is ViewGroup) {
                disableET(child)
            }
        }
    }

    //Funcion para mostrar el date piker
    private fun showDatePicker() {
        val datePicker = DatePickerFragment { day, month, year ->
            selectedDate(day, month, year)
            binding.containerSession.removeAllViews()
            lifecycleScope.launch {
                val idDateSession = db.sessionDao().sessionByDate(date)
                if (idDateSession > 0) {
                    showSession()
                } else {
                    Toast.makeText(
                        this@HistoryActivity,
                        "No hay entrenamientos que mostrar este día",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        datePicker.show(supportFragmentManager, "datePicker")
    }

    //Funcion para establecer la fecha del datePicker
    private fun selectedDate(day: Int, month: Int, year: Int): String {

        val monthPlus = month + 1
        date = "$day-$monthPlus-$year"

        return date

    }

    private fun setFunctionItemsNavigationBar() {
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
}