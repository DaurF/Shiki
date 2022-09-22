package mob.dau.ren.shiki.viewmodels

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import mob.dau.ren.shiki.network.AnimeItem
import mob.dau.ren.shiki.network.ShikimoriNetwork

class ListAnimeViewModel : ViewModel() {
    private val _listAnime = MutableLiveData<List<AnimeItem>>()
    val listAnime: LiveData<List<AnimeItem>>
        get() = _listAnime

    init {
        refreshData()
    }

    private fun refreshData() = viewModelScope.launch {
        _listAnime.value = ShikimoriNetwork.retrofitService.fetchListAnime()
    }
}