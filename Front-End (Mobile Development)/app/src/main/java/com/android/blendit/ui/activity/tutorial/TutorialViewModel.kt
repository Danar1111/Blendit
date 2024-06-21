package com.android.blendit.ui.activity.tutorial

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.blendit.remote.response.TutorialResult
import com.android.blendit.remote.retrofit.ApiConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TutorialViewModel : ViewModel() {

    private val _tutorialResult = MutableLiveData<TutorialResult>()
    val tutorialResult: LiveData<TutorialResult> = _tutorialResult

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun getTutorials(token: String, shape: String, skinTone: String, undertone: String, skinType: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val apiService = ApiConfig.getApiService()
                val response = apiService.getTutorial(token, shape, skinTone, undertone, skinType)
                if (response.status == "error") {
                    _errorMessage.postValue("Failed to get tutorials: ${response.message}")
                } else {
                    _tutorialResult.postValue(response.tutorialResult)
                }
            } catch (e: Exception) {
                Log.e("TutorialViewModel", "Exception during API call: ${e.message}", e)
                _errorMessage.postValue("Failed to get tutorials: ${e.message}")
            }
        }
    }
}