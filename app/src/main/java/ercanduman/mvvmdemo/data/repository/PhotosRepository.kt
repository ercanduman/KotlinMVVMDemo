package ercanduman.mvvmdemo.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ercanduman.mvvmdemo.data.network.JsonPlaceHolderApi
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PhotosRepository {
    fun getPhotos(albumId: Int): LiveData<String> {
        val apiResponse = MutableLiveData<String>()

        // TODO: Dependency Injection will be applied here!
        JsonPlaceHolderApi().getPhotos(albumId).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                apiResponse.value = t.message
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    apiResponse.value = response.body()?.string()
                } else {
                    apiResponse.value = response.errorBody()?.string()
                }
            }
        })
        return apiResponse
    }
}