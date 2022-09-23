package mob.dau.ren.shiki.viewmodels

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import mob.dau.ren.shiki.network.AnimeItem
import mob.dau.ren.shiki.network.FullAnimeItem
import mob.dau.ren.shiki.network.ShikimoriNetwork
import mob.dau.ren.shiki.repository.ListAnimeRepository

class ListAnimeViewModel(private val repository: ListAnimeRepository) : ViewModel() {
    private val _listAnime = MutableLiveData<List<AnimeItem>>()
    val listAnime: LiveData<List<AnimeItem>> get() = _listAnime

    private val _singleAnime = MutableLiveData<FullAnimeItem>()
    val singleAnime: LiveData<FullAnimeItem> get() = _singleAnime

    init {
        refreshData()
    }

    private fun refreshData() = viewModelScope.launch {
        _listAnime.value = ShikimoriNetwork.retrofitService.fetchListAnime()
    }

    fun refreshItem(id: Int) {
        viewModelScope.launch {
            _singleAnime.value = ShikimoriNetwork.retrofitService.fetchAnimeItem(id)
        }
    }
}

class Factory(private val repository: ListAnimeRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListAnimeViewModel::class.java)) {
            return ListAnimeViewModel(repository) as T
        }
        throw IllegalAccessException("Error")
    }
}