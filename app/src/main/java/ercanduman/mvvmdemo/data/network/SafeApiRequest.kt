package ercanduman.mvvmdemo.data.network

import ercanduman.mvvmdemo.util.ApiException
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response

abstract class SafeApiRequest {
    suspend fun <T : Any> apiRequest(call: suspend () -> Response<T>): T {
        val response = call.invoke()
        if (response.isSuccessful) {
            return response.body()!!
        } else {
            val apiErrorMessage = StringBuilder()
            val error = response.errorBody()?.string()
            error?.let {
                try {
                    val errorMessage = JSONObject(it).getString("message")
                    apiErrorMessage.append(errorMessage)
                } catch (e: JSONException) {
                }
                apiErrorMessage.append("\n")
            }
            apiErrorMessage.append("Error code: ${response.code()}")
            throw ApiException(apiErrorMessage.toString())
        }
    }
}