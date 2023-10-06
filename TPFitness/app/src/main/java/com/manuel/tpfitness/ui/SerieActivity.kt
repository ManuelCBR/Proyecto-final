package com.manuel.tpfitness.ui

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
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import com.manuel.tpfitness.R
import com.manuel.tpfitness.databinding.ActivitySerieBinding

class SerieActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySerieBinding
    private lateinit var cardContainer: LinearLayout
    private var cardViewCounter = 1
    val cardViewsList = mutableListOf<CardView>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySerieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        addCVSerie()
        binding.btnAddSerie.setOnClickListener { addCVSerie() }
        binding.tvDelSerie.setOnClickListener { delSeries() }
        binding.iBtnBack.setOnClickListener { onBackPressed() }

        getCardViewSerie(0)

    }

    //Metodo para añadir de forma dinámica los cardviews correspondientes para las series
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
        val tvSerie = cardView.findViewById<TextView>(R.id.tvSerie)
        tvSerie.setText(cardViewCounter.toString())
        //Se agrega la card al container (layout)
        cardContainer = findViewById(R.id.containerSeries)
        cardContainer.addView(cardView)
        cardViewsList.add(cardView)
        cardViewCounter++
        cardView.id

        //Se establece un scroll
        val scrollView = findViewById<ScrollView>(R.id.scrollSeries)
        scrollView.post {
            scrollView.fullScroll(View.FOCUS_DOWN)
        }

    }

    private fun delSeries() {
        val childCount = cardContainer.childCount
        if (childCount > 0) {
            val lastChild = cardContainer.getChildAt(childCount - 1)
            if (lastChild is CardView) cardContainer.removeView(lastChild)
        }

    }
    private fun getCardViewSerie(idCardView: Int){

        val cardView = cardViewsList[idCardView]
        val etKg = cardView.findViewById<EditText>(R.id.etKg)
        etKg.inputType = InputType.TYPE_CLASS_NUMBER
        val etReps = cardView.findViewById<EditText>(R.id.etReps)
        etReps.inputType = InputType.TYPE_CLASS_NUMBER
        val valueKg = etKg.text.toString()
        val valueReps = etReps.text.toString()

        Log.e("Valor", valueKg)
        Log.e("Valor", valueReps)
    }
}