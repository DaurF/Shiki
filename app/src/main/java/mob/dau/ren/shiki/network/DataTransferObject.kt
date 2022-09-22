package mob.dau.ren.shiki.network

import com.squareup.moshi.Json
import mob.dau.ren.shiki.util.BASE_URL

data class AnimeItem(
    val id: Int,
    @Json(name = "russian") val name: String,
    val image: Image
)

data class Image(
    @Json(name = "original") val url: String
)

fun Image.fullUrl(): String {
    return BASE_URL + url
}