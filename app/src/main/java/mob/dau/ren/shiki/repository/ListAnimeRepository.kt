package mob.dau.ren.shiki.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mob.dau.ren.shiki.database.ShikiDatabase
import mob.dau.ren.shiki.database.asDomainModel
import mob.dau.ren.shiki.domain.AnimeItemDomain
import mob.dau.ren.shiki.network.ShikimoriNetwork
import mob.dau.ren.shiki.network.asDatabaseModel

class ListAnimeRepository(private val database: ShikiDatabase) {
    suspend fun refreshList() {
        withContext(Dispatchers.IO) {
            val listAnime = ShikimoriNetwork.retrofitService.fetchListAnime()
            database.shikiDao().insertList(listAnime.asDatabaseModel())
        }
    }

    val listAnime: LiveData<List<AnimeItemDomain>> =
        Transformations.map(database.shikiDao().getList().asLiveData()) {
            it.asDomainModel()
        }
}