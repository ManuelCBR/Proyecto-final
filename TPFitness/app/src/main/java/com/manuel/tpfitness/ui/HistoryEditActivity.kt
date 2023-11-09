package com.manuel.tpfitness.ui

import android.app.Activity
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
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.lifecycle.lifecycleScope
import com.manuel.tpfitness.R
import com.manuel.tpfitness.database.TPFitnessDB
import com.manuel.tpfitness.database.entities.SeriesEntity
import com.manuel.tpfitness.databinding.ActivityHistoryEditBinding
import kotlinx.coroutines.launch

class HistoryEditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryEditBinding
    private lateinit var db: TPFitnessDB
    private lateinit var cardContainer: LinearLayout
    private var cardViewCounter = 0
    val cardViewsList = mutableListOf<CardView>()
    private lateinit var tvSerie: TextView
    private var idSerie: Int = 0
    private var valueKg: Int = 0
    private var valueReps: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = TPFitnessDB.initDB(this)

        binding.btnAddSerie.setOnClickListener { addCVSerie() }
        binding.tvDelSerie.setOnClickListener { delSeries() }
        binding.iBtnBack.setOnClickListener { onBackPressed() }
        val idExerciseExtra = intent.getIntExtra("idExercise", 0)
        val idSessionExtra = intent.getIntExtra("idSession", 0)
        lifecycleScope.launch {
            binding.tvExercise.text = db.exerciseDao().getNameExercise(idExerciseExtra)
        }
        binding.imBtnSave.setOnClickListener { updateExercise(idExerciseExtra, idSessionExtra) }
        binding.imBtnDelete.setOnClickListener { delExercises(idSessionExtra, idExerciseExtra) }



        setFunctionItemsNavigationBar()
    }
    //Funcion para añadir de forma dinámica los cardviews correspondientes para las series
    private fun addCVSerie() {

        val cardView = CardView(this)
        val newCardView = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        newCardView.setMargins(0, 8, 0, 8)
        cardView.layoutParams = newCardView
        cardView.id = ViewCompat.generateViewId()
        cardView.setCardBackgroundColor(ContextCompat.getColor(this, R.color.cards))
        //Inflamos el diseño de las card desde el XML
        val customCardContent =
            LayoutInflater.from(this).inflate(R.layout.item_cv_series, null, false) as ViewGroup
        //Se agrega la vista al card
        cardView.addView(customCardContent)
        //Se declara la variable serie para que se vaya incrementando cuando el usuario añada alguna más
        tvSerie = cardView.findViewById(R.id.tvCvSerie)
        tvSerie.setText((cardViewCounter + 1).toString())
        //Se agrega la card al container (layout)
        cardContainer = findViewById(R.id.containerSeries)
        cardContainer.addView(cardView)
        cardViewsList.add(cardView)

        //Se suma uno al contador para incrementar la id del cardVIew
        cardViewCounter++
        cardView.id

        //Se establece un scroll
        val scrollView = findViewById<ScrollView>(R.id.scrollSeries)
        scrollView.post {
            scrollView.fullScroll(View.FOCUS_DOWN)
        }

    }
    //Funcion para actualizar el ejercicio
    private fun updateExercise (idExercise: Int, idSession: Int){
        var error = false
        //Se recorre el listado de series para verificar si se han introducido los campos
        for (field in cardViewsList) {
            val etKg = field.findViewById<EditText>(R.id.etKg)
            val etReps = field.findViewById<EditText>(R.id.etReps)
            //Se controla el error en caso de que haya algún campo no introducido
            if (etKg.text.isEmpty() || etReps.text.isEmpty()) {
                error = true
            }
        }
        if (error){
            Toast.makeText(
                this@HistoryEditActivity,
                "Debes rellenar todas las series",
                Toast.LENGTH_SHORT
            ).show()
        }
        if(!error){
            //Se actualizan los valores con lso que ha introducido el usuario
            for (item in cardViewsList) {
                val cardView = item
                val etKg = cardView.findViewById<EditText>(R.id.etKg)
                val tvSerie = cardView.findViewById<TextView>(R.id.tvCvSerie)
                val etReps = cardView.findViewById<EditText>(R.id.etReps)
                idSerie = tvSerie.text.toString().toInt()
                valueKg = etKg.text.toString().toInt()
                valueReps = etReps.text.toString().toInt()
                lifecycleScope.launch {
                    db.seriesDao().updateSerie(
                        SeriesEntity(
                            idSession,
                            idExercise,
                            idSerie,
                            valueKg,
                            valueReps
                        )
                    )
                }
            }
            onBackPressed()
        }
    }

    //Funcion para eliminar las series mientras el usuario esta rellenandolas
    private fun delSeries() {
        val childCount = cardContainer.childCount
        if (childCount > 0) {
            val lastChild = cardContainer.getChildAt(childCount - 1)
            if (lastChild is CardView) cardContainer.removeView(lastChild)
        }
        /*Se resta 1 para que el numero de serie siga siendo el mismo si al usuario le da por
        añadir alguna mas despues de haberlas borrado*/
        cardViewCounter--
        cardViewsList.removeLast()

    }
    private fun delExercises(idSession: Int, idExercise: Int){
        val alert = AlertDialog.Builder(this)
        alert.setTitle("Alerta")
        alert.setMessage("¿Seguro que quieres eliminar el ejercicio?")
        alert.setPositiveButton("Sí"){dialog, witch ->
            lifecycleScope.launch {
                db.exercisesSessionDao().delExerciseSession(idSession, idExercise)
                Toast.makeText(this@HistoryEditActivity, "Ejercicio eliminado", Toast.LENGTH_SHORT).show()
            }
        }
        alert.setNegativeButton("No"){dialog, witch ->

        }
        val dialog: AlertDialog = alert.create()
        dialog.show()
    }
    //Se establecen las funcionesd de los botones del bottom navigation view
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
}