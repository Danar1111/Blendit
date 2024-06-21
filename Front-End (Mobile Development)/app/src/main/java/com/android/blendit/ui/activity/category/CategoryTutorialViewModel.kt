package com.android.blendit.ui.activity.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.android.blendit.preference.AccountPreference
import com.android.blendit.remote.response.CategoryTutorialResponse
import com.android.blendit.viewmodel.Repository
import com.android.blendit.remote.Result

class CategoryTutorialViewModel(accountPreference: AccountPreference) : ViewModel() {
    private val repository = Repository(accountPreference)

    fun getTutorial(token: String, categoryId: String): LiveData<Result<CategoryTutorialResponse>> {
        return repository.getCategoryTutorial(token, categoryId)
    }
}