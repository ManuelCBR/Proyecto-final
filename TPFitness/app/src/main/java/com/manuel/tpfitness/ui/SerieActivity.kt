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
    private var cardViewCounter = 0
    val cardViewsList = mutableListOf<CardView>()
    private lateinit var tvSerie: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySerieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        addCVSerie()
        binding.btnAddSerie.setOnClickListener { addCVSerie() }
        binding.tvDelSerie.setOnClickListener { delSeries() }
        binding.iBtnBack.setOnClickListener { onBackPressed() }

        binding.tvSave.setOnClickListener { setCardViewSerie() }



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
        tvSerie = cardView.findViewById(R.id.tvCvSerie)
        tvSerie.setText((cardViewCounter + 1).toString())
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
        cardViewCounter--

    }
    private fun setCardViewSerie(){

        for(item in cardViewsList){
            val cardView = item
            val etKg = cardView.findViewById<EditText>(R.id.etKg)
            val tvSerie = cardView.findViewById<TextView>(R.id.tvCvSerie)
            val etReps = cardView.findViewById<EditText>(R.id.etReps)
            //Valores de la serie
            val idSerie = tvSerie.text
            val valueKg = etKg.text.toString().toInt()
            val valueReps = etReps.text.toString().toInt()
            Log.e("Values", idSerie.toString() + "|" + valueKg + "|" + valueReps)
        }



    }
}