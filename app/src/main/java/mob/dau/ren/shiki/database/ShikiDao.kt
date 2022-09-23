package mob.dau.ren.shiki.database

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ShikiDao {
    @Query("SELECT * FROM list_anime_data")
    fun getList(): Flow<List<AnimeItemDatabase>>

    @Insert
    fun insertList(list: List<AnimeItemDatabase>)
}