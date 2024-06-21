package com.android.blendit.ui.activity.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.android.blendit.preference.AccountPreference
import com.android.blendit.remote.response.CategoryResponse
import com.android.blendit.viewmodel.Repository
import com.android.blendit.remote.Result

import kotlinx.coroutines.launch
class CategoryViewModel(accountPreference: AccountPreference) : ViewModel() {
    private val repository = Repository(accountPreference)

    fun getCategory(token: String): LiveData<Result<CategoryResponse>> {
        return repository.getCategory(token)
    }
}
