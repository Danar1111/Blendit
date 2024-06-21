package com.android.blendit.ui.activity.main

import androidx.lifecycle.ViewModel
import com.android.blendit.preference.AccountPreference
import com.android.blendit.viewmodel.Repository
import okhttp3.MultipartBody

class MainViewModel(accountPreference: AccountPreference) : ViewModel() {

    private val repository = Repository(accountPreference)

    val loginInfo = repository.loginInfo

    fun getListProduct() = repository.getListProduct()

    fun getListFavorite() = repository.getListFavorite()

    fun uploadProfilePicture(profilePicture: MultipartBody.Part) =
        repository.uploadProfilePicture(profilePicture)

    fun deleteProfilePicture() = repository.deleteProfilePicture()

    fun addFavorite(productId: String) = repository.addFavorite(productId)

    fun removeFavorite(productId: String) = repository.removeFavorite(productId)

    fun changePass(password: String) = repository.changePass(password)

    fun getUnsplashImage() = repository.getUnsplashImage()

}