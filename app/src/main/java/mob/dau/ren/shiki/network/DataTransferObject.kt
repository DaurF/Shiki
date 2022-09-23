package mob.dau.ren.shiki.network

import com.squareup.moshi.Json
import mob.dau.ren.shiki.database.AnimeItemDatabase
import mob.dau.ren.shiki.domain.AnimeItemDomain
import mob.dau.ren.shiki.util.BASE_URL

data class AnimeItem(
    val id: Int,
    @Json(name = "russian") val name: String,
    val image: Image
)

data class Image(
    @Json(name = "original") val url: String
)

fun Image.fullImageUrl(): String {
    return BASE_URL + url
}

fun List<AnimeItem>.asDomainModel(): List<AnimeItemDomain> {
    return map {
        AnimeItemDomain(
            id = it.id,
            name = it.name,
            imageUrl = it.image.fullImageUrl()
        )
    }
}

fun List<AnimeItem>.asDatabaseModel(): List<AnimeItemDatabase> {
    return map {
        AnimeItemDatabase(
            id = it.id,
            name = it.name,
            imageUrl = it.image.fullImageUrl()
        )
    }
}