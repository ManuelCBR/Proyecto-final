package com.manuel.tpfitness.ui

import android.content.Intent
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.lifecycle.lifecycleScope
import com.manuel.tpfitness.R
import com.manuel.tpfitness.database.TPFitnessDB
import com.manuel.tpfitness.database.entities.SessionEntity
import com.manuel.tpfitness.databinding.ActivityHistoryBinding
import kotlinx.coroutines.launch

class HistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding
    private lateinit var db: TPFitnessDB
    private var sessionList: MutableList<String> = mutableListOf()
    private lateinit var cardContainer: LinearLayout
    private var cardViewCounter = 0
    val cardViewsList = mutableListOf<CardView>()
    private lateinit var tvSerie: TextView
    private lateinit var idSerie: TextView
    private lateinit var valueKg: TextView
    private lateinit var valueReps: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = TPFitnessDB.initDB(this)

        binding.btnDate.setOnClickListener { addCVSession() }

        setFunctionItemsNavigationBar()
    }
    private fun addCVSession(){
        val cardView = CardView(this)
        val newCardView = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        newCardView.setMargins(0, 8, 0, 8)
        cardView.layoutParams = newCardView
        cardView.id = ViewCompat.generateViewId()
        cardView.setCardBackgroundColor(ContextCompat.getColor(this, R.color.background))
        cardView.setCardElevation(0F)
        //Inflamos el diseño de las card desde el XML
        val customCardContent =
            LayoutInflater.from(this).inflate(R.layout.item_cv_session, null, false) as ViewGroup
        //Se agrega la vista al card
        cardView.addView(customCardContent)
        //Se declara la variable serie para que se vaya incrementando cuando el usuario añada alguna más
        tvSerie = cardView.findViewById(R.id.tvCvSerie)
        tvSerie.setText((cardViewCounter + 1).toString())
        //Se agrega la card al container (layout)
        cardContainer = findViewById(R.id.containerSession)
        cardContainer.addView(cardView)
        cardViewsList.add(cardView)

        //Se suma uno al contador para incrementar la id del cardVIew
        //cardViewCounter++
        cardView.id

        //Se establece un scroll
        val scrollView = findViewById<ScrollView>(R.id.scrollSeries)
        scrollView.post {
            scrollView.fullScroll(View.FOCUS_DOWN)
        }
    }
    /*private fun addCVSession(){
        lifecycleScope.launch {

            sessionList = db.sessionDao().sessionByDate("") }

    // Accede al contenedor
        val contenedor = findViewById<LinearLayout>(R.id.containerSession)

    // Se limpia cualquier vista anterior en el contenedor
        contenedor.removeAllViews()

    // Se recorre la lista de datos y crea dinámicamente los EditText
        for (dato in sessionList) {
            val editText = EditText(this)
            editText.text = Editable.Factory.getInstance().newEditable(dato)
            contenedor.addView(editText)
        }

        Log.e("session", sessionList.toString())


        //Se establece un scroll
        val scrollView = findViewById<ScrollView>(R.id.scrollSeries)
        scrollView.post {
            scrollView.fullScroll(View.FOCUS_DOWN)
        }
    }*/

    private fun setFunctionItemsNavigationBar(){
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
}