package com.manuel.tpfitness.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
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
    private lateinit var cardContainerSession: LinearLayout
    private var cardViewCounter = 0
    private var cardViewCounterSession = 0
    private lateinit var tvSerie: TextView
    private var date = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = TPFitnessDB.initDB(this)


        binding.btnDate.setOnClickListener { showDatePicker() }
        binding.tvEdit.setOnClickListener {
            addCVSession("Sesión 1")
            addCVExercise(1, "Ejercicio1")
            addCVSeries(1, 1, 50, 10)
            addCVSession("Sesión 2")
            addCVExercise(2, "Ejercicio1")
            addCVSeries(2, 1, 40, 5)
        }



        setFunctionItemsNavigationBar()
    }

    private fun showSession() {
        lifecycleScope.launch {
            var idExercise = 0
            var idSession = 0
            val idSessionByDate = db.sessionDao().sessionByDate(date)

            fullSessionList = db.sessionDao().getFullSession(idSessionByDate)


            for (fullSession in fullSessionList) {


                if (idExercise != fullSession.idExercise) {

                        cardViewCounter++
                        idExercise = fullSession.idExercise
                        addCVExercise(cardViewCounterSession, fullSession.nameExercise)
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

    private fun addCVSession(nameSession: String) {
        val cvSession = CardView(this@HistoryActivity)
        val newCVExercise = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        newCVExercise.setMargins(0, 8, 0, 8)
        cvSession.layoutParams = newCVExercise
        cvSession.setCardBackgroundColor(
            ContextCompat.getColor(
                this@HistoryActivity,
                R.color.background
            )
        )
        cvSession.cardElevation = 0f

        //Inflamos el diseño de las card desde el XML
        val customCardContent =
            LayoutInflater.from(this@HistoryActivity)
                .inflate(R.layout.item_cv_session, null, false) as ViewGroup

        //Se agrega la vista al card
        cvSession.addView(customCardContent)
        cardContainerSession = findViewById(R.id.containerSession)
        cardContainerSession.addView(cvSession)
        //cardViewsList.add(cvSession)
        //Se establece el nombre del ejercicio
        cvSession.findViewById<TextView>(R.id.tvNameSession).text = nameSession
        cvSession.id = cardViewCounterSession


        //Se establece un scroll
        val scrollView = findViewById<ScrollView>(R.id.scrollSeries)
        scrollView.post {
            scrollView.fullScroll(View.FOCUS_DOWN)
        }
    }

    private fun addCVExercise(cvSessionId: Int, nameExercise: String) {

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

        //Se agrega la vista al card
        val parentCV = findViewById<CardView>(cvSessionId)
        if (parentCV != null) {
            // Se encuentra el LinearLayout en el CardView padre
            val contenExercises = parentCV.findViewById<LinearLayout>(R.id.contentSession)
            contenExercises.addView(cvExercise)
        } else {
            Toast.makeText(this, "No se han encontrado cardviews", Toast.LENGTH_SHORT).show()
        }

        /*val cardContainerExercise = findViewById<LinearLayout>(R.id.contentSession)
          cardContainerExercise.addView(cvExercise)
          //Se establece el nombre del ejercicio
          cvExercise.findViewById<TextView>(R.id.tvExerciseName).text = nameExercise

          cvExercise.id = cardViewCounter*/

    }

    private fun addCVSeries(cvExerciseId: Int, idSerie: Int, kg: Int, reps: Int) {

        val cvSerie = CardView(this)
        val newCVserie = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        newCVserie.setMargins(0, 0, 0, 0)
        cvSerie.layoutParams = newCVserie
        //cvSerie.id = ViewCompat.generateViewId()
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
            showSession()
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