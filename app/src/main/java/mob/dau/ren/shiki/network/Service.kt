package mob.dau.ren.shiki.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import mob.dau.ren.shiki.util.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET


interface ShikimoriService {
        @GET("api/animes?order=popularity&page=1&limit=50")
    suspend fun fetchListAnime(): List<AnimeItem>
}

object ShikimoriNetwork {
    val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    val retrofitService = retrofit.create(ShikimoriService::class.java)

}