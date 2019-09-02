package ercanduman.mvvmdemo.data.network

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://jsonplaceholder.typicode.com/"

interface JsonPlaceHolderApi {
    @GET("photos")
    fun getPhotos(@Query("albumId") albumId: Int): Call<ResponseBody>

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