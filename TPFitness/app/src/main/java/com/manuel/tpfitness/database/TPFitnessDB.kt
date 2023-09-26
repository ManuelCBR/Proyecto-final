package com.manuel.tpfitness.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.manuel.tpfitness.database.daos.ExerciseDao
import com.manuel.tpfitness.database.daos.MuscleGroupDao
import com.manuel.tpfitness.database.entities.ExerciseEntity
import com.manuel.tpfitness.database.entities.MuscleGroupEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Database(
    entities = [ExerciseEntity::class, MuscleGroupEntity::class],
    version = 1
)
abstract class TPFitnessDB: RoomDatabase() {
    abstract fun exerciseDao(): ExerciseDao
    abstract fun muscleGroupDao(): MuscleGroupDao

    companion object {
        private lateinit var db: TPFitnessDB

        fun initDB(context: Context): TPFitnessDB{
            if(!this::db.isInitialized){
                db = Room.databaseBuilder(
                    context,
                    TPFitnessDB::class.java, "TP_Fitness_DB"
                )
                    .addCallback(callback)
                    .build()
            }
            return db
        }

        private val callback: Callback = object : Callback(){
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                GlobalScope.launch {
                    withContext(Dispatchers.IO){

                        TPFitnessDB.db.muscleGroupDao().addMuscleGroup(MuscleGroupEntity(0, "Pectorales", ""))
                        TPFitnessDB.db.muscleGroupDao().addMuscleGroup(MuscleGroupEntity(0, "Dorsales", ""))
                        TPFitnessDB.db.muscleGroupDao().addMuscleGroup(MuscleGroupEntity(0, "Hombros", ""))
                        TPFitnessDB.db.muscleGroupDao().addMuscleGroup(MuscleGroupEntity(0, "Brazos", ""))
                        TPFitnessDB.db.muscleGroupDao().addMuscleGroup(MuscleGroupEntity(0, "Piernas", ""))
                        TPFitnessDB.db.muscleGroupDao().addMuscleGroup(MuscleGroupEntity(0, "Abdominales", ""))

                        TPFitnessDB.db.exerciseDao().addExercise(ExerciseEntity(0,"Press Banca Horizontal",
                            "Empujes tanto con barra como con mancuernas o en máquina para trabajar el pectoral medial", TPFitnessDB.db.muscleGroupDao().getMuscleGroupById(1)
                        ))
                        TPFitnessDB.db.exerciseDao().addExercise(ExerciseEntity(0,"Press Banca Inclinada",
                            "Empujes tanto con barra como con mancuernas o en máquina para trabajar el pectoral superior", TPFitnessDB.db.muscleGroupDao().getMuscleGroupById(1)))
                        TPFitnessDB.db.exerciseDao().addExercise(ExerciseEntity(0,"Press Banca Declinada",
                            "Empujes tanto con barra como con mancuernas o en máquina para trabajar el pectoral superior", TPFitnessDB.db.muscleGroupDao().getMuscleGroupById(1)))
                        TPFitnessDB.db.exerciseDao().addExercise(ExerciseEntity(0,"Fondos paralelas",
                            "Empujes tanto con barra como con mancuernas o en máquina para trabajar el pectoral superior", TPFitnessDB.db.muscleGroupDao().getMuscleGroupById(1)))
                        TPFitnessDB.db.exerciseDao().addExercise(ExerciseEntity(0,"Aperturas Ascendentes",
                            "Empujes tanto con barra como con mancuernas o en máquina para trabajar el pectoral superior", TPFitnessDB.db.muscleGroupDao().getMuscleGroupById(1)))
                        TPFitnessDB.db.exerciseDao().addExercise(ExerciseEntity(0,"Aperturas Descendentes",
                            "Empujes tanto con barra como con mancuernas o en máquina para trabajar el pectoral superior", TPFitnessDB.db.muscleGroupDao().getMuscleGroupById(1)))
                        TPFitnessDB.db.exerciseDao().addExercise(ExerciseEntity(0,"Apertura Neutra",
                            "Empujes tanto con barra como con mancuernas o en máquina para trabajar el pectoral superior", TPFitnessDB.db.muscleGroupDao().getMuscleGroupById(1)))
                        TPFitnessDB.db.exerciseDao().addExercise(ExerciseEntity(0,"Apertura Neutra",
                            "Empujes tanto con barra como con mancuernas o en máquina para trabajar el pectoral superior", TPFitnessDB.db.muscleGroupDao().getMuscleGroupById(1)))
                        TPFitnessDB.db.exerciseDao().addExercise(ExerciseEntity(0,"Apertura Neutra",
                            "Empujes tanto con barra como con mancuernas o en máquina para trabajar el pectoral superior", TPFitnessDB.db.muscleGroupDao().getMuscleGroupById(1)))
                        TPFitnessDB.db.exerciseDao().addExercise(ExerciseEntity(0,"Apertura Neutra",
                            "Empujes tanto con barra como con mancuernas o en máquina para trabajar el pectoral superior", TPFitnessDB.db.muscleGroupDao().getMuscleGroupById(1)))
                        TPFitnessDB.db.exerciseDao().addExercise(ExerciseEntity(0,"Press Banca Declinada",
                            "Empujes tanto con barra como con mancuernas o en máquina para trabajar el pectoral superior", TPFitnessDB.db.muscleGroupDao().getMuscleGroupById(1)))
                        TPFitnessDB.db.exerciseDao().addExercise(ExerciseEntity(0,"Press Banca Declinada",
                            "Empujes tanto con barra como con mancuernas o en máquina para trabajar el pectoral superior", TPFitnessDB.db.muscleGroupDao().getMuscleGroupById(1)))
                        TPFitnessDB.db.exerciseDao().addExercise(ExerciseEntity(0,"Press Banca Declinada",
                            "Empujes tanto con barra como con mancuernas o en máquina para trabajar el pectoral superior", TPFitnessDB.db.muscleGroupDao().getMuscleGroupById(1)))
                        TPFitnessDB.db.exerciseDao().addExercise(ExerciseEntity(0,"Press Banca Declinada",
                            "Empujes tanto con barra como con mancuernas o en máquina para trabajar el pectoral superior", TPFitnessDB.db.muscleGroupDao().getMuscleGroupById(1)))
                        TPFitnessDB.db.exerciseDao().addExercise(ExerciseEntity(0,"Press Banca Declinada",
                            "Empujes tanto con barra como con mancuernas o en máquina para trabajar el pectoral superior", TPFitnessDB.db.muscleGroupDao().getMuscleGroupById(1)))

                    }
                }
            }
        }
    }
}