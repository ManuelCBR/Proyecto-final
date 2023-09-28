package com.manuel.tpfitness.app

import android.app.Application
import com.manuel.tpfitness.database.TPFitnessDB

class MyApp : Application() {
    private lateinit var db: TPFitnessDB

    fun getDatabase(): TPFitnessDB {
        return db
    }

    /*override fun onCreate() {
        super.onCreate()
        db = TPFitnessDB.initDB(this)
    }*/
}