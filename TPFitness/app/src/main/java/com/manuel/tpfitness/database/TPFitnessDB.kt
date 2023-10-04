package com.manuel.tpfitness.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.manuel.tpfitness.database.daos.ExerciseDao
import com.manuel.tpfitness.database.daos.ExerciseMuscleDao
import com.manuel.tpfitness.database.daos.MuscleGroupDao
import com.manuel.tpfitness.database.daos.SeriesDao
import com.manuel.tpfitness.database.daos.SessionDao
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
abstract class TPFitnessDB : RoomDatabase() {
    abstract fun exerciseDao(): ExerciseDao
    abstract fun muscleGroupDao(): MuscleGroupDao
    abstract fun exerciseMuscleDao(): ExerciseMuscleDao
    abstract fun sessionDao(): SessionDao
    abstract fun seriesDao(): SeriesDao

    companion object {
        private lateinit var db: TPFitnessDB

        //Funcion para inicializar la base de datos el cual tiene el método callback para precargar los datos
        fun initDB(context: Context): TPFitnessDB {
            if (!this::db.isInitialized) {
                db = Room.databaseBuilder(
                    context,
                    TPFitnessDB::class.java, "TP_Fitness_DB"
                )
                    .addCallback(callback)
                    .build()
            }
            return db
        }

        //Callback para precargar todos los datos mínimos que debe tener la aplicacion para que el usuario no se la encuentre vacia
        private val callback: Callback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                GlobalScope.launch {
                    withContext(Dispatchers.IO) {

                        //Grupos Musculares
                        TPFitnessDB.db.muscleGroupDao().addMuscleGroup(
                            MuscleGroupEntity(
                                0, "Pectorales",
                                "Ubicado en la parte superior del cuerpo en la región del pecho, desempeña un papel fundamental en la estabilidad" +
                                        " y el movimiento de los hombros y brazos. Se compone de Pectoral Mayor y Pectoral Menor"
                            )
                        )
                        TPFitnessDB.db.muscleGroupDao().addMuscleGroup(
                            MuscleGroupEntity(
                                0, "Dorsales",
                                "Músculos esenciales para la movilidad y la estabilidad de la parte superior del cuerpo, ubicado en la parte posterior" +
                                        " del mismo. Se compone de Dorsal ancho, Trapecio, Romboides y ELevadores de la escápula"
                            )
                        )
                        TPFitnessDB.db.muscleGroupDao().addMuscleGroup(
                            MuscleGroupEntity(
                                0, "Hombros",
                                "También llamados deltoides, están ubicados en la parte superior del brazo y se componen de Deltoides anterior," +
                                        " Deltoides medio, Deltoides posterior y Manguitos rotadores"
                            )
                        )
                        TPFitnessDB.db.muscleGroupDao().addMuscleGroup(
                            MuscleGroupEntity(
                                0, "Brazos",
                                "Se compone de varios músculos que son los encargados de la flexión, extensión y movimiento de este, que son el Bíceps," +
                                        " el Tríceps, el Antebrazo y el Braquiorradial"
                            )
                        )
                        TPFitnessDB.db.muscleGroupDao().addMuscleGroup(
                            MuscleGroupEntity(
                                0, "Piernas",
                                "Parte inferior del cuerpo encargada de la locomoción y la estabilidad de este. Se compone de Cuádriceps, Isquiotibiales," +
                                        " Pantorrilla, Aductores y Abductores"
                            )
                        )
                        TPFitnessDB.db.muscleGroupDao().addMuscleGroup(
                            MuscleGroupEntity(
                                0, "Abdominales",
                                "Se encuentran en la parte frontal del tronco y son los músculos responsables del soporte de la columna vertebral, estabilidad" +
                                        " del núcleo y flexión y rotación del torso, entre otras funciones. Se compone del Recto abdominal, Oblícuos y el Transverso del abdomen"
                            )
                        )

                        //Ejercicios de grupo muscular Pectorales
                        TPFitnessDB.db.exerciseDao().addExercise(
                            ExerciseEntity(
                                0,
                                "Press Banca Horizontal",
                                "Con la espalda apoyada en un banco plano, realizar empujes, ya sea con barra, mancuernas o en una máquina dedicada a ello," +
                                        " desde el pecho hacia extensión de brazos de forma perpendicular al cuerpo, con la apertura de manos aproximadamente a la misma que la de los hombros",
                                1
                            )
                        )
                        TPFitnessDB.db.exerciseDao().addExercise(
                            ExerciseEntity(
                                0, "Press Banca Inclinada",
                                "Con la espalda apoyada en un banco inclinado a 30º ó 45º aprox, realizar empujes, ya sea con barra, mancuernas o en una máquina dedicada a ello," +
                                        " desde el pecho hacia extensión de brazos de forma perpendicular al suelo (o siguiendo la trayectoria de la máquina en su caso), con la apertura de manos" +
                                        " aproximadamente a la misma que la de los hombros", 1
                            )
                        )
                        TPFitnessDB.db.exerciseDao().addExercise(
                            ExerciseEntity(
                                0, "Press Banca Declinada",
                                "Con la espalda apoyada en un banco declinado a 30º aprox, realizar empujes, ya sea con barra o mancuernas, desde el pecho hacia extensión de " +
                                        "brazos de forma perpendicular al suelo", 1
                            )
                        )
                        TPFitnessDB.db.exerciseDao().addExercise(
                            ExerciseEntity(
                                0,
                                "Fondos paralelas",
                                "Sostenerse suspendido en dos barras paralelas con una anchura similar a la de los hombros, bajar hasta casi el nivel del pecho (que no se vuelva" +
                                        " doloroso, y volver a subir. Se puede hacer en paralelas libres, en máquinas con asistencia, o en máquina donde el usuario se sienta y realiza empujes hacia abajo",
                                1
                            )
                        )
                        TPFitnessDB.db.exerciseDao().addExercise(
                            ExerciseEntity(
                                0,
                                "Aperturas planas",
                                "Comenzando con los brazos perpendiculares al cuerpo, estirados hacia el frente a la altura del pecho con las manos juntas, abrirlos hasta extensión máxima y volver a la posición" +
                                        " inicial. Se pueden realizar con mancuernas tumbado en un banco plano o en el suelo, en poleas de pie o tabién en el mismo banco, o en máquinas especializadas",
                                1
                            )
                        )
                        TPFitnessDB.db.exerciseDao().addExercise(
                            ExerciseEntity(
                                0,
                                "Aperturas ascendentes",
                                "Comenzando con los brazos estirados hacia el frente a la altura del pecho, abrirlos hasta extensión máxima con un movimiento hacia abajo hasta llegar a la " +
                                        "zona de la cintura y volver a la posición inicial. Se pueden realizar de pie con mancuernas o con poleas",
                                1
                            )
                        )
                        TPFitnessDB.db.exerciseDao().addExercise(
                            ExerciseEntity(
                                0, "Aperturas descendentes",
                                "Comenzando con los brazos estirados hacia el frente a la altura de la cintura con las manos juntas, abrirlos hasta extensión máxima con un movimiento hacia arriba y volver a la" +
                                        " posición inicial. Se pueden realizar con poleas", 1
                            )
                        )

                        //Ejercicios del grupo muscular Dorsales
                        TPFitnessDB.db.exerciseDao().addExercise(
                            ExerciseEntity(
                                0,
                                "Dominadas",
                                "Colgarse de una barra suspendida con la anchura de agarre un poco más abierta que la distancia entre hombros, tirar de la barra para que el cuerpo se aproxime a ella, llegando a " +
                                        "superar esta con la barbilla, y vuelta a la posición inicial",
                                2
                            )
                        )
                        TPFitnessDB.db.exerciseDao().addExercise(
                            ExerciseEntity(
                                0,
                                "Jalones al pecho",
                                "En la máquina dedicada para ello, agarrar el tipo de agarre deseado (ya sea barra ancha, triángulo, barra con agarre neutro, etc), llevarlo al pecho y de vuelta a la posición inicial",
                                2
                            )
                        )
                        TPFitnessDB.db.exerciseDao().addExercise(
                            ExerciseEntity(
                                0,
                                "Peso Muerto",
                                "De pie con una barra en el suelo, agarrarla con una anchura de la distancia entre hombros aprox y levantarla hasta estar completamente erguidos, y volver a dejarla en el suelo. Muy importante" +
                                        " en este ejercicio mantener una postura correcta de la espalda para no hacerse daño",
                                2
                            )
                        )
                        TPFitnessDB.db.exerciseDao().addExercise(
                            ExerciseEntity(
                                0,
                                "Remo bajo",
                                "En la máquina dedicada para ello, agarrar el tipo de agarre deseado (ya sea barra ancha, triángulo, barra con agarre neutro, etc), llevarlo al abdomen y de vuelta a la posición inicial",
                                2
                            )
                        )
                        TPFitnessDB.db.exerciseDao().addExercise(
                            ExerciseEntity(
                                0, "Remo con barra",
                                "Con una barra en el suelo y un agarre con la anchura de la distancia de los hombros, inclinar el cuerpo hacia adelante, mantener la barra suspendida del suelo y llevarla hacia el abdomen" +
                                        " tirando de ella, y volver a la posición inicial",
                                2
                            )
                        )
                        TPFitnessDB.db.exerciseDao().addExercise(
                            ExerciseEntity(
                                0,
                                "Remo con mancuernas",
                                "Con una mancuerna en el suelo y un agarre neutro, inclinar el cuerpo hacia adelante, apoyar la mano contraria en un banco para ganar estabilidad, mantener la mancuerna suspendida del suelo" +
                                        " y llevarla hacia el lateral del abdomen tirando de ella, y volver a la posición inicial",
                                2
                            )
                        )
                        TPFitnessDB.db.exerciseDao().addExercise(
                            ExerciseEntity(
                                0,
                                "Pull Over",
                                "Tumbado en un banco plano, sostienes una mancuerna con ambas manos y la llevas desde detrás de tu cabeza hacia arriba y sobre tu pecho. También se puede hacer en máquina dedicada o " +
                                        "con polea alta y agarre plano o cuerda, realizando el mismo movimiento pero de pie en el suelo",
                                2
                            )
                        )
                        TPFitnessDB.db.exerciseDao().addExercise(
                            ExerciseEntity(
                                0,
                                "Exensiones de lumbares",
                                "Tumbado boca abajo en el suelo, o en una máquina específica, levantar el torso contrayendo la zona lumbar",
                                2
                            )
                        )

                        //Ejercicios del grupo muscular Hombros
                        TPFitnessDB.db.exerciseDao().addExercise(
                            ExerciseEntity(
                                0, "Press militar",
                                "Sentado en un banco o en una máquina específica, realizar empujes de una barra o mancuernas desde los hombros hacia arriba realizando una extensión máxima de los brazos, y volver a " +
                                        "la posición inicial",
                                3
                            )
                        )
                        TPFitnessDB.db.exerciseDao().addExercise(
                            ExerciseEntity(
                                0, "Elevaciones frontales",
                                "De pie o sentado en un banco, con mancuernas o poleas, elevar el brazo de forma frontal desde posición neutra en la cadera, hasta la zona frente al pecho hasta que el brazo quede perpendicular" +
                                        " al cuepro, y volver a posición inicial",
                                3
                            )
                        )
                        TPFitnessDB.db.exerciseDao().addExercise(
                            ExerciseEntity(
                                0, "Elevaciones laterales",
                                "De pie o sentado en un banco, con mancuernas o poleas, elevar el brazo de forma lateral desde posición neutra en la cadera, hasta la zona exterior al cuerpo formando una L hasta que el brazo quede " +
                                        "perpendicular al cuerpo, y volver a posición inicial",
                                3
                            )
                        )
                        TPFitnessDB.db.exerciseDao().addExercise(
                            ExerciseEntity(
                                0,
                                "Elevaciones posteriores (pájaro)",
                                "De pie o sentado en un banco, con mancuernas o poleas, inclinarse hacia adelante y elevar el brazo desde posición neutra frente al pectoral, hasta atrás, y volver a posición inicial",
                                3
                            )
                        )
                        TPFitnessDB.db.exerciseDao().addExercise(
                            ExerciseEntity(
                                0,
                                "Jalones a la cara",
                                "De pie frente a una polea alta, con el agarre de cuerda tirar de él hacia la cara con los codos a la altura de los hombros, y volver a la posición inicial",
                                3
                            )
                        )
                        TPFitnessDB.db.exerciseDao().addExercise(
                            ExerciseEntity(
                                0,
                                "Encogimientos de trapecio",
                                "Sosteniendo una barra o unas mancuernas, levantar los hombros hacia arriba y hacia atrás hasta realizar la contracción de la zona trapezoidal y volver a la posición inicial",
                                3
                            )
                        )

                        //Ejercicios del grupo muscular Brazos
                        TPFitnessDB.db.exerciseDao().addExercise(
                            ExerciseEntity(
                                0,
                                "Press francés para Triceps",
                                "Tumbarse en un banco o en el suelo y llevar una barra o unas mancuernas desde una posición de brazo extendida frente al pectoral hacia detrás de la cabeza flexionando los codos y" +
                                        "volver a la posición inicial",
                                4
                            )
                        )
                        TPFitnessDB.db.exerciseDao().addExercise(
                            ExerciseEntity(
                                0,
                                "Pull-down de triceps en polea alta",
                                "En una polea alta con un agarre plano, tipo V o cuerda, tirar de éste con los codos pegados al cuerpo hasta llevarlo a la zona de la cadera realizando una contracción completa del triceps",
                                4
                            )
                        )
                        TPFitnessDB.db.exerciseDao().addExercise(
                            ExerciseEntity(
                                0,
                                "Pull-down de triceps en polea alta tras nuca",
                                "En una polea alta con un agarre plano, tipo V o cuerda, posicionarse de espaldas a la polea, inclinarse hacia adelante y tirar de dicho agarre con los brazos por encima de la cabeza hasta extenderlos" +
                                        "por completo, y volver a la posición inicial",
                                4
                            )
                        )
                        TPFitnessDB.db.exerciseDao().addExercise(
                            ExerciseEntity(
                                0,
                                "Press horizontal con agarre estrecho",
                                "Con la espalda apoyada en un banco plano, realizar empujes con barra o en una máquina dedicada a ello desde el pecho hacia extensión de brazos de forma perpendicular al cuerpo, con la apertura de manos " +
                                        "de aproximadamente ua cuarta entre ellas",
                                4
                            )
                        )
                        TPFitnessDB.db.exerciseDao().addExercise(
                            ExerciseEntity(
                                0,
                                "Curl de biceps concentrado",
                                "Sujetando una barra o unas mancuernas, de pie o sentados en un banco, llevar el brazo desde la zona de la cadera en extensión máxima hasta los hombros, contrayendo el biceps sin despegar los codos del cuerpo, " +
                                        "con un agarre supino (palmas hacia arriba)",
                                4
                            )
                        )
                        TPFitnessDB.db.exerciseDao().addExercise(
                            ExerciseEntity(
                                0,
                                "Curl de biceps martillo",
                                "Sujetando una barra o unas mancuernas, de pie o sentados en un banco, llevar el brazo desde la zona de la cadera en extensión máxima hasta los hombros, contrayendo el biceps sin despegar los codos del cuerpo, " +
                                        "con un agarre neutro (palmas hacia adentro)",
                                4
                            )
                        )
                        TPFitnessDB.db.exerciseDao().addExercise(
                            ExerciseEntity(
                                0,
                                "Curl de biceps en banco predicador",
                                "Sujetando una barra o unas mancuernas y sentados en un banco predicador, también llamado banco Scott, llevar el brazo desde extensión máxima apoyados en el soporte hasta los hombros, contrayendo el biceps sin " +
                                        "despegar los codos de este, con agarre neutro o supino",
                                4
                            )
                        )
                        TPFitnessDB.db.exerciseDao().addExercise(
                            ExerciseEntity(
                                0,
                                "Curl de biceps en banco inclinado",
                                "Sujetando una barra o unas mancuernas y sentados en un banco inclinado a 45º aprox, llevar el brazo desde extensión máxima con los codos lo más atrás del cuerpo posible hasta los hombros, contrayendo el biceps" +
                                        " sin elevar los codos, con agarre neutro o supino",
                                4
                            )
                        )

                        //Ejercicios del grupo muscular Piernas
                        TPFitnessDB.db.exerciseDao().addExercise(
                            ExerciseEntity(
                                0,
                                "Sentadillas",
                                "De pie con una barra apoyada en los hombros por detrás de la cabeza, los pies a la altura de los hombros y una posición de la espalda recta, flexionar las rodillas para bajar en conjunto hasta llegar lo más" +
                                        " cerca del suelo posible, sin despegar los talones de este ni deformar la posición, y volver a la posición original empujando el suelo con los talones. Ejercicio que requiere una buena posición y una buena práctica" +
                                        " para evitar lesiones",
                                5
                            )
                        )
                        TPFitnessDB.db.exerciseDao().addExercise(
                            ExerciseEntity(
                                0,
                                "Extensiones de cuádriceps",
                                "Sentado en la máquina específica para ello, colocar los pies detrás de la almohadilla acolchada y realizar extensión de piernas completa, y volver a posición inicial ",
                                5
                            )
                        )
                        TPFitnessDB.db.exerciseDao().addExercise(
                            ExerciseEntity(
                                0,
                                "Curl de biceps femoral",
                                "Sentado en la máquina específica para ello, colocar los pies delante de la almohadilla acolchada y realizar contracción de piernas completa, y volver a posición inicial ",
                                5
                            )
                        )
                        TPFitnessDB.db.exerciseDao().addExercise(
                            ExerciseEntity(
                                0,
                                "Sentadilla Búlgara",
                                "De pie sosteniendo una mancuerna, o con el propio peso corporal, apoyar una el empeine hacia atrás en un banco plano y flexionar ambas piernas hasta llegar lo más próximo al suelo posible, y voler a la posición inicial",
                                5
                            )
                        )
                        TPFitnessDB.db.exerciseDao().addExercise(
                            ExerciseEntity(
                                0,
                                "Zancadas",
                                "Realizar zancadas largas, sosteniendo unas mancuernas o con el propio peso corporal, ya sea adelantando o retrasando una pierna, bajando todo lo posible hasta el suelo y volver a la posición inicial. También se puede " +
                                        "realizar andando hacia adelante si hay espacio suficiente",
                                5
                            )
                        )
                        TPFitnessDB.db.exerciseDao().addExercise(
                            ExerciseEntity(
                                0,
                                "Extensiones de gemelos",
                                "De pie, con el propio peso corporal, sostenindo una mancuerna o con una barra apoyada en los hombros trás la cabeza, elevar los talones poniéndose de puntillas y volver a la posición inicial",
                                5
                            )
                        )

                        //Ejercicios del grupo muscular Abdominales
                        TPFitnessDB.db.exerciseDao().addExercise(
                            ExerciseEntity(
                                0,
                                "Elevaciones de piernas",
                                "Colgarse de una barra suspendida y elevar los pies tanto como sea posible, o bien con las piernas estiradas o bien con la piernas encogidas siendo las rodillas lo que se pretende elevar, y volver a la " +
                                        "posición inical",
                                6
                            )
                        )
                        TPFitnessDB.db.exerciseDao().addExercise(
                            ExerciseEntity(
                                0,
                                "Crunches (encogimientos abdominales)",
                                "Tumbarse en el suelo boca arriba con las piernas flexionadas y levantar el torso dspegando los hombros del suelo contrayendo el abdomen sin que los pies dejen de tocar el suelo",
                                6
                            )
                        )
                        TPFitnessDB.db.exerciseDao().addExercise(
                            ExerciseEntity(
                                0,
                                "Crunches (encogimientos abdominales oblicuos)",
                                "Tumbarse en el suelo boca arriba con las piernas flexionadas y levantar el torso dspegando los hombros del suelo contrayendo el abdomen intentando llevar un codo a la rodilla contraria, sin que los pies " +
                                        "dejen de tocar el suelo",
                                6
                            )
                        )
                        TPFitnessDB.db.exerciseDao().addExercise(
                            ExerciseEntity(
                                0,
                                "Planchas estáticas",
                                "Tumbarse en el suelo boca abajo sosteniendo el cuerpo con los codos/antebrazos apoyados y sobre las punteras, teniendo el cuerpo suspendido de forma paralela al suelo, y mantener posición el tiempo deseado ",
                                6
                            )
                        )
                        TPFitnessDB.db.exerciseDao().addExercise(
                            ExerciseEntity(
                                0,
                                "Planchas tipo Climbers",
                                "Tumbarse en el suelo boca abajo sosteniendo el cuerpo con los las manos apoyados y brazos extendidos, con los pies sobre las punteras, teniendo el cuerpo suspendido de forma paralela al suelo " +
                                        "y llevar las rodillas al pecho de forma intercalada",
                                6
                            )
                        )
                        TPFitnessDB.db.exerciseDao().addExercise(
                            ExerciseEntity(
                                0,
                                "Planchas tipo Climbers para oblícuos",
                                "Tumbarse en el suelo boca abajo sosteniendo el cuerpo con los las manos apoyados y brazos extendidos, con los pies sobre las punteras, teniendo el cuerpo suspendido de forma paralela al suelo " +
                                        "y llevar las rodillas al hombro contrario de forma intercalada",
                                6
                            )
                        )
                    }
                }
            }
        }
    }
}