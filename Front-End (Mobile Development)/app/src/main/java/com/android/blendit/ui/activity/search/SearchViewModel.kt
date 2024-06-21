package com.android.blendit.ui.activity.search

import androidx.lifecycle.ViewModel
import com.android.blendit.preference.AccountPreference
import com.android.blendit.viewmodel.Repository

class SearchViewModel(accountPreference: AccountPreference) : ViewModel() {

    private val repository = Repository(accountPreference)

    fun getListFavorite() = repository.getListFavorite()

    fun findProduct(query: String) = repository.getListFindProduct(query)
}
