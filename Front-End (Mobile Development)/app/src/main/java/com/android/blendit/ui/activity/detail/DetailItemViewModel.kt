package com.android.blendit.ui.activity.detail

import androidx.lifecycle.ViewModel
import com.android.blendit.preference.AccountPreference
import com.android.blendit.viewmodel.Repository

class DetailItemViewModel(accountPreference: AccountPreference) : ViewModel() {

    private val repository = Repository(accountPreference)

    fun addFavorite(productId: String) = repository.addFavorite(productId)

    fun removeFavorite(productId: String) = repository.removeFavorite(productId)
}