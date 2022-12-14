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

    private val statuses: MutableList<String> = mutableListOf()
    private val genres: MutableList<String> = mutableListOf()
    private val studios: MutableList<String> = mutableListOf()

    private var searchQuery: String = ""

    init {
        fetchAnimeByStatusAndGenre()
    }

    fun refreshItem(id: Int) {
        viewModelScope.launch {
            _singleAnime.value = ShikimoriNetwork.retrofitService.fetchAnimeItem(id)
        }
    }

    fun setSearchQuery(query: String) {
        searchQuery = query
    }

    fun addGenre(genre: String) {
        if (genres.contains(genre)) {
            genres.remove(genre)
            return
        }
        genres.add(genre)
    }

    fun addStatus(status: String) {
        if (statuses.contains(status)) {
            statuses.remove(status)
            return
        }
        statuses.add(status)
    }

    fun setGenre(genre: String) {
        genres.apply {
            this.clear()
            this.add(genre)
        }
    }

    fun setStudio(studio: String) {
        studios.apply {
            this.clear()
            this.add(studio)
        }
    }

    fun addStudio(studio: String) {
        if (studios.contains(studio)) {
            studios.remove(studio)
            return
        }
        studios.add(studio)
    }

    fun fetchAnimeByStatusAndGenre() = viewModelScope.launch {
        val statusesStr = statuses.toString().replace("[", "")
            .replace("]", "").replace(" ", "")
        var genresStr = genres.toString().replace("[", "")
            .replace("]", "").replace(" ", "")
        val studiosStr = studios.toString().replace("[", "")
            .replace("]", "").replace(" ", "")
        _listAnime.value = ShikimoriNetwork.retrofitService
            .fetchAnimeByStatusAndGenre(
                statusesStr,
                genresStr,
                studiosStr,
                searchQuery,
                "popularity"
            )
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