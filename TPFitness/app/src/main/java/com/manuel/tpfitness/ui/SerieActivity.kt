package com.manuel.tpfitness.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
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
import androidx.core.view.ViewCompat
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import com.manuel.tpfitness.R
import com.manuel.tpfitness.database.TPFitnessDB
import com.manuel.tpfitness.database.entities.ExercisesSessionEntity
import com.manuel.tpfitness.database.entities.SeriesEntity
import com.manuel.tpfitness.database.entities.SessionEntity
import com.manuel.tpfitness.databinding.ActivitySerieBinding
import kotlinx.coroutines.launch

class SerieActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySerieBinding
    private lateinit var cardContainer: LinearLayout
    private var cardViewCounter = 0
    val cardViewsList = mutableListOf<CardView>()
    private lateinit var tvSerie: TextView
    private var idSerie: Int = 0
    private var valueKg: Int = 0
    private var valueReps: Int = 0
    private lateinit var db: TPFitnessDB


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySerieBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = TPFitnessDB.initDB(this)

        //Se añade el cardView dinamico para añadir series
        addCVSerie()
        setFunctionItemsNavigationBar()

        binding.btnAddSerie.setOnClickListener { addCVSerie() }
        binding.tvDelSerie.setOnClickListener { delSeries() }
        binding.iBtnBack.setOnClickListener { onBackPressed()}
        val idExtra = intent.getIntExtra("idExercise", 0)
        binding.tvSave.setOnClickListener {
            saveSerie(idExtra)
            onBackPressed()
        }
    }

    private fun backToSession() {
        val intent = Intent(this, SessionActivity::class.java)
        startActivity(intent)
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

    //Funcion para eliminar las series mientras el usuario esta rellenandolas
    private fun delSeries() {
        val childCount = cardContainer.childCount
        if (childCount > 0) {
            val lastChild = cardContainer.getChildAt(childCount - 1)
            if (lastChild is CardView) cardContainer.removeView(lastChild)
        }
        cardViewCounter--

    }

    //Funcion para gaurdar las series
    private fun saveSerie (idExercise: Int) {

        lifecycleScope.launch {

            MainActivity.origin = "fromSerie"
            if(MainActivity.origin == "session") {
                //Se guarda una sesion y se almacena con el ultimo id en la tabla compuesta

                db.sessionDao().addSession(SessionEntity(0, "", ""))
                val lastSession = db.sessionDao().getLastId()
                db.exercisesSessionDao()
                    .addExerciseSession(ExercisesSessionEntity(lastSession, idExercise))
            }else if (MainActivity.origin == "fromSerie"){
                val lastSession = db.sessionDao().getLastId()
                db.exercisesSessionDao()
                .addExerciseSession(ExercisesSessionEntity(lastSession, idExercise))}

            //Se establece una variable bandera para verificar los campos de las series
            var error = false

            //Se recorre el listado de series para verificar si se han introducido los campos
            for (item in cardViewsList) {
                val cardView = item
                val etKg = cardView.findViewById<EditText>(R.id.etKg)
                val etReps = cardView.findViewById<EditText>(R.id.etReps)
                //Se controla el error en caso de que haya algún campo no introducido
                if (etKg.text.isEmpty() || etReps.text.isEmpty()) {
                    error = true
                    Toast.makeText(
                        this@SerieActivity,
                        "Debes rellenar todas las series",
                        Toast.LENGTH_SHORT
                    ).show()
                    break
                }
            }
            //En caso de que no haya errores, se guarda en la bbdd la info de las series elegidas
            if(!error){
                val lastSession = db.sessionDao().getLastId()
                for (item in cardViewsList) {
                    val cardView = item
                    val etKg = cardView.findViewById<EditText>(R.id.etKg)
                    val tvSerie = cardView.findViewById<TextView>(R.id.tvCvSerie)
                    val etReps = cardView.findViewById<EditText>(R.id.etReps)
                    idSerie = tvSerie.text.toString().toInt()
                    valueKg = etKg.text.toString().toInt()
                    valueReps = etReps.text.toString().toInt()
                    db.seriesDao().addSerie(SeriesEntity(lastSession, idExercise, idSerie, valueKg, valueReps)
                    )
                }


            }
        }

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