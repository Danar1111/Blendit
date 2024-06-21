package com.android.blendit.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.android.blendit.BuildConfig
import com.android.blendit.data.FindProductPagingSource
import com.android.blendit.data.ProductPagingSource
import com.android.blendit.preference.AccountPreference
import com.android.blendit.remote.Result
import com.android.blendit.remote.response.CategoryResponse
import com.android.blendit.remote.response.CategoryTutorialResponse
import com.android.blendit.remote.response.FindItems
import com.android.blendit.remote.response.ItemsFavorite
import com.android.blendit.remote.response.ItemsProduct
import com.android.blendit.remote.response.LoginResult
import com.android.blendit.remote.response.ResponseChangePassword
import com.android.blendit.remote.response.ResponseDeleteProfilePicture
import com.android.blendit.remote.response.ResponseFavorite
import com.android.blendit.remote.response.ResponseLogin
import com.android.blendit.remote.response.ResponseRegister
import com.android.blendit.remote.response.ResponseUploadProfilePicture
import com.android.blendit.remote.response.ResultsItemUnsplash
import com.android.blendit.remote.retrofit.ApiConfig
import com.android.blendit.remote.retrofit.ApiService
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository(private val accountPreference: AccountPreference) {

    private val apiService: ApiService = ApiConfig.getApiService()

    private val _loginInfo = MutableLiveData<LoginResult>()
    val loginInfo: LiveData<LoginResult> = _loginInfo

    init {
        loadLoginInfo()
    }

    private fun loadLoginInfo() {
        val loginResult = accountPreference.getLoginInfo()
        _loginInfo.value = loginResult
    }

    fun register(
        name: String, email: String, password: String
    ): LiveData<Result<ResponseRegister>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.register(
                name, email, password
            )
            if (response.error) {
                emit(Result.Error(response.message))
            } else {
                emit(Result.Success(response))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun login(email: String, password: String): LiveData<Result<ResponseLogin>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.login(
                email, password
            )
            if (response.error) {
                emit(Result.Error(response.message))
            } else {
                emit(Result.Success(response))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getListProduct(): LiveData<PagingData<ItemsProduct>> {
        return Pager(
            PagingConfig(20), null
        ) { ProductPagingSource(accountPreference, apiService) }.liveData
    }

    fun getListFavorite(): LiveData<Result<List<ItemsFavorite>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.listFavorite(
                accountPreference.getLoginInfo().token.toString(),
                accountPreference.getLoginInfo().userId.toString()
            )
            if (response.isSuccessful) {
                response.body()?.let { Result.Success(it.items) }?.let { emit(it) }
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "Unknown error"))
        }
    }

    fun addFavorite(productId: String) {
        val client = apiService.addFavorite(
            accountPreference.getLoginInfo().token.toString(),
            accountPreference.getLoginInfo().userId.toString(),
            productId
        )
        client.enqueue(object : Callback<ResponseFavorite> {
            override fun onResponse(p0: Call<ResponseFavorite>, p1: Response<ResponseFavorite>) {
                if (!p1.isSuccessful) {
                    Log.d("addFavorite", p1.message())
                }
            }

            override fun onFailure(p0: Call<ResponseFavorite>, p1: Throwable) {
                Log.d("addFavorite", p1.message.toString())
            }

        })
    }

    fun changePass(password: String): LiveData<Result<ResponseChangePassword>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.changePass(
                accountPreference.getLoginInfo().token.toString(),
                accountPreference.getLoginInfo().userId.toString(),
                password
            )
            if (response.isSuccessful) {
                emit(Result.Success(response.body()!!))
            } else {
                emit(Result.Error(response.message()))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "Unknown error"))
        }
    }

    fun removeFavorite(productId: String) {
        val client = apiService.removeFavorite(
            accountPreference.getLoginInfo().token.toString(),
            accountPreference.getLoginInfo().userId.toString(),
            productId
        )
        client.enqueue(object : Callback<ResponseFavorite> {
            override fun onResponse(p0: Call<ResponseFavorite>, p1: Response<ResponseFavorite>) {
                if (!p1.isSuccessful) {
                    Log.d("addFavorite", p1.message())
                }
            }

            override fun onFailure(p0: Call<ResponseFavorite>, p1: Throwable) {
                Log.d("addFavorite", p1.message.toString())
            }

        })
    }

    fun getCategory(token: String): LiveData<Result<CategoryResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getCategory(token)
            if (response.status == "error") {
                emit(Result.Error(response.message))
            } else {
                emit(Result.Success(response))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getCategoryTutorial(
        token: String, categoryId: String
    ): LiveData<Result<CategoryTutorialResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getCategoryTutorial(token, categoryId)
            if (response.status == "error") {
                emit(Result.Error(response.message))
            } else {
                emit(Result.Success(response))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun uploadProfilePicture(
        profilePicture: MultipartBody.Part
    ) {
        val client = apiService.uploadProfilePicture(
            accountPreference.getLoginInfo().token.toString(), profilePicture
        )
        client.enqueue(object : Callback<ResponseUploadProfilePicture> {
            override fun onResponse(
                p0: Call<ResponseUploadProfilePicture>,
                response: Response<ResponseUploadProfilePicture>
            ) {
                if (response.isSuccessful) {
                    accountPreference.setProfilePict(response.body()?.photoUrl)
                    loadLoginInfo()
                }
            }

            override fun onFailure(p0: Call<ResponseUploadProfilePicture>, p1: Throwable) {
                Log.d("uploadProfilePicture", p1.message.toString())
            }
        })
    }

    fun deleteProfilePicture() {
        val client = apiService.deleteProfilePict(
            accountPreference.getLoginInfo().token.toString()
        )
        client.enqueue(object : Callback<ResponseDeleteProfilePicture> {
            override fun onResponse(
                p0: Call<ResponseDeleteProfilePicture>,
                response: Response<ResponseDeleteProfilePicture>
            ) {
                if (response.isSuccessful) {
                    accountPreference.setProfilePict(null)
                    loadLoginInfo()
                }
            }

            override fun onFailure(p0: Call<ResponseDeleteProfilePicture>, p1: Throwable) {
                Log.d("deleteProfilePicture", p1.message.toString())
            }
        })
    }

    fun getListFindProduct(query: String): LiveData<PagingData<FindItems>> {
        return Pager(
            PagingConfig(20), null
        ) { FindProductPagingSource(query, accountPreference, apiService) }.liveData
    }

    fun getUnsplashImage(
    ): LiveData<Result<List<ResultsItemUnsplash>>> = liveData {
        emit(Result.Loading)
        try {
            val response = ApiConfig.getApiServiceUnsplash()
                .unsplashImage("make up", 5, BuildConfig.UNSPLASH_CLIENT_ID)
            if (response.isSuccessful) {
                emit(Result.Success(response.body()?.results!!))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }
}