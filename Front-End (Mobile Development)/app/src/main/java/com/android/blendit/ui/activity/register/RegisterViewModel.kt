package com.android.blendit.ui.activity.register

import androidx.lifecycle.ViewModel
import com.android.blendit.preference.AccountPreference
import com.android.blendit.viewmodel.Repository

class RegisterViewModel(
    loginPreferences: AccountPreference
) : ViewModel() {

    private val repository = Repository(loginPreferences)

    fun userRegister(name: String, email: String, password: String) =
        repository.register(name, email, password)
}