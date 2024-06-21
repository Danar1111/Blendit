package com.android.blendit.ui.activity.analysis

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.blendit.remote.response.AnalystResult
import com.android.blendit.remote.retrofit.ApiConfig
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class AnalysisViewModel : ViewModel() {

    private val _analysisResult = MutableLiveData<AnalystResult>()
    val analysisResult: LiveData<AnalystResult> = _analysisResult

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun analyzeImage(token: String, imageUri: String, skinTone: String, undertone: String, skinType: String) {
        viewModelScope.launch {
            try {
                val apiService = ApiConfig.getApiService()
                val file = File(Uri.parse(imageUri).path)
                val requestFile = RequestBody.create("image/jpeg".toMediaTypeOrNull(), file)
                val body = MultipartBody.Part.createFormData("image", file.name, requestFile)
                val skintonePart = RequestBody.create("text/plain".toMediaTypeOrNull(), skinTone)
                val undertonePart = RequestBody.create("text/plain".toMediaTypeOrNull(), undertone)
                val skinTypePart = RequestBody.create("text/plain".toMediaTypeOrNull(), skinType)

                val response = apiService.predict(token, body, skintonePart, undertonePart, skinTypePart)
                if (response.status == "success") {
                    Log.d("AnalysisViewModel", "Analysis successful: ${response.analystResult}")
                    _analysisResult.postValue(response.analystResult)
                } else {
                    Log.e("AnalysisViewModel", "Analysis error: ${response.message}")
                    _errorMessage.postValue(response.message)
                }
            } catch (e: Exception) {
                Log.e("AnalysisViewModel", "Failed to connect to the server: ${e.message}")
                _errorMessage.postValue("Failed to connect to the server.")
            }
        }
    }
}