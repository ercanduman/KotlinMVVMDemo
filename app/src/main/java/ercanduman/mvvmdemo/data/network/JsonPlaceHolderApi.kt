package ercanduman.mvvmdemo.data.network

import ercanduman.mvvmdemo.data.db.entities.album.Album
import ercanduman.mvvmdemo.data.db.entities.photo.Photo
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://jsonplaceholder.typicode.com/"

interface JsonPlaceHolderApi {
    /**
     * "suspend" keyword is part of Coroutines library and makes functions  suspending,
     * suspending functions can run long-running code blocks and retrieve content.
     * getPhotos function is performs network operation which can take long
     */
    @GET("photos")
    suspend fun getPhotos(@Query("albumId") albumId: Int): Response<List<Photo>>

    @GET("albums")
    suspend fun getAlbums(): Response<List<Album>>

    companion object {
        operator fun invoke(
            networkConnectionInterceptor: NetworkConnectionInterceptor
        ): JsonPlaceHolderApi {

            val httpClient = OkHttpClient.Builder()
                .addInterceptor(networkConnectionInterceptor)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(JsonPlaceHolderApi::class.java)
        }
    }

}