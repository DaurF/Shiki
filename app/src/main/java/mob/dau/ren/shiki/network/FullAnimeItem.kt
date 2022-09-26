package mob.dau.ren.shiki.network

import com.squareup.moshi.Json
import mob.dau.ren.shiki.util.BASE_URL

data class FullAnimeItem(
    val id: Int,
    @Json(name = "russian") val name: String,
    val image: Image,
    val kind: String,
    val episodes: Int,
    val videos: List<Video>?,
    val genres: List<Genre>,
    val description: String?,
    val score: String,
    val rating: String,
    val duration: String,
    val status: String,
    val studios: List<Studio>
)

data class Video(
    @Json(name = "url") val videoUrl: String,
    @Json(name = "image_url") val previewUrl: String
)

data class Genre(
    val id: Int,
    @Json(name = "russian") val name: String
)

data class Studio(
    val id: Int,
    val image: String
)

fun Studio.getFullImageUrl(): String {
    return BASE_URL + image
}