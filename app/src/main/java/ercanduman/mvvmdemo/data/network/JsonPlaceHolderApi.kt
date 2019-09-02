package ercanduman.mvvmdemo.data.network

import ercanduman.mvvmdemo.data.db.entities.Photo
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://jsonplaceholder.typicode.com/"

interface JsonPlaceHolderApi {
    /**
     * suspend keyword is part of Coroutines library and makes functions  suspending,
     * suspending functions can run long-running code blocks and retrieve content.
     * getPhotos function is performs network operation which can take long
     */
    @GET("photos")
    suspend fun getPhotos(@Query("albumId") albumId: Int): Response<List<Photo>>

    companion object {
        operator fun invoke(): JsonPlaceHolderApi {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(JsonPlaceHolderApi::class.java)
        }
    }

}