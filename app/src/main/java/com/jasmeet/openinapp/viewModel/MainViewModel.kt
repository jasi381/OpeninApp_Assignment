package com.jasmeet.openinapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.jasmeet.openinapp.data.ApiResponse
import com.jasmeet.openinapp.data.RecentLink
import com.jasmeet.openinapp.data.TopLink
import com.jasmeet.openinapp.tokenManager.TokenManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

/**
 * @author: Jasmeet Singh
 */

class MainViewModel(private val tokenManager: TokenManager) :ViewModel(){

    private val _apiResult = MutableLiveData<String>()
    val apiResult: LiveData<String> get() = _apiResult

    private val _apiResponse = MutableLiveData<ApiResponse>()
    val apiResponse: LiveData<ApiResponse> get() = _apiResponse

    private val apiUrl = "https://api.inopenapp.com/api/v1/dashboardNew"

    private val _recentLinksResult = MutableLiveData<List<RecentLink>>()
    val recentLinks: LiveData<List<RecentLink>> get() = _recentLinksResult

    private val _topLinksResult = MutableLiveData<List<TopLink>>()
    val topLinks: LiveData<List<TopLink>> get() = _topLinksResult

    fun fetchData() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = makeAPICall()
            _apiResult.postValue(result)
            Log.d("MainViewModel", "fetchData: $result")
            parseApiResponse(result)
        }
    }

    private fun parseApiResponse(response: String) {
        val gson = Gson()
        val apiResponse = gson.fromJson(response, ApiResponse::class.java)
        _apiResponse.postValue(apiResponse)
        _recentLinksResult.postValue(apiResponse.data.recent_links)
        _topLinksResult.postValue(apiResponse.data.top_links)
    }

    private fun makeAPICall(): String {
        val client = OkHttpClient()

        val token = tokenManager.getToken() ?: return "Error: Token not available"

        val request = Request.Builder()
            .url(apiUrl)
            .addHeader("Authorization", "Bearer $token")
            .build()

        return try {
            val response = client.newCall(request).execute()

            if (response.isSuccessful) {
                response.body?.string() ?: "Empty response body"
            } else {
                "Error: ${response.message}"
            }
        } catch (e: IOException) {
            "Error: ${e.message}"
        }
    }
}