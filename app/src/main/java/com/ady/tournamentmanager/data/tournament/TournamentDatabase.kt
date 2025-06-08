import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ady.tournamentmanager.data.tournament.Tournament
import com.ady.tournamentmanager.data.tournament.TournamentDao

/**
 * Trieda reprezentujuca Room databazu pre turnaje
 */
@Database(entities = [Tournament::class], version = 1, exportSchema = false)
abstract class TournamentDatabase : RoomDatabase() {
    abstract fun tournamentDao(): TournamentDao
    companion object {
        @Volatile
        private var Instance: TournamentDatabase? = null
        fun getDatabase(context: Context): TournamentDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, TournamentDatabase::class.java, "tournament_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}