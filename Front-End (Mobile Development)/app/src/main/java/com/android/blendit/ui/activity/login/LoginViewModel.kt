package com.android.blendit.ui.activity.login

import androidx.lifecycle.ViewModel
import com.android.blendit.preference.AccountPreference
import com.android.blendit.viewmodel.Repository

class LoginViewModel(
    accountPreference: AccountPreference
) : ViewModel() {

    private val repository = Repository(accountPreference)

    fun userLogin(email: String, password: String) = repository.login(email, password)
}