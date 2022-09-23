package mob.dau.ren.shiki.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import mob.dau.ren.shiki.domain.AnimeItemDomain

@Entity(tableName = "list_anime_data")
data class AnimeItemDatabase(
    @PrimaryKey val id: Int,
    val name: String,
    val imageUrl: String
)

fun List<AnimeItemDatabase>.asDomainModel(): List<AnimeItemDomain> {
    return map {
        AnimeItemDomain(
            id = it.id,
            name = it.name,
            imageUrl = it.imageUrl
        )
    }
}