package mob.dau.ren.shiki.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [AnimeItemDatabase::class], version = 2)
abstract class ShikiDatabase : RoomDatabase() {
    abstract fun shikiDao(): ShikiDao

    companion object {
        @Volatile
        var INSTANCE: ShikiDatabase? = null

        fun getDatabase(context: Context): ShikiDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    ShikiDatabase::class.java,
                    "shiki_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}