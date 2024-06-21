package com.android.blendit.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.blendit.preference.AccountPreference
import com.android.blendit.ui.activity.category.CategoryTutorialViewModel
import com.android.blendit.ui.activity.category.CategoryViewModel
import com.android.blendit.ui.activity.detail.DetailItemViewModel
import com.android.blendit.ui.activity.login.LoginViewModel
import com.android.blendit.ui.activity.main.MainViewModel
import com.android.blendit.ui.activity.register.RegisterViewModel
import com.android.blendit.ui.activity.recommendation.RecommendationViewModel
import com.android.blendit.ui.activity.search.SearchViewModel

class ViewModelFactory(
    private val accountPreference: AccountPreference
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> LoginViewModel(
                accountPreference
            ) as T

            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> RegisterViewModel(
                accountPreference
            ) as T

            modelClass.isAssignableFrom(MainViewModel::class.java) -> MainViewModel(
                accountPreference
            ) as T

            modelClass.isAssignableFrom(RecommendationViewModel::class.java) -> RecommendationViewModel(
                accountPreference
            ) as T

            modelClass.isAssignableFrom(CategoryTutorialViewModel::class.java) -> CategoryTutorialViewModel(
                accountPreference
            ) as T

            modelClass.isAssignableFrom(CategoryViewModel::class.java) -> CategoryViewModel(
                accountPreference
            ) as T

            modelClass.isAssignableFrom(DetailItemViewModel::class.java) -> DetailItemViewModel(
                accountPreference
            ) as T

            modelClass.isAssignableFrom(SearchViewModel::class.java) -> SearchViewModel(
                accountPreference
            ) as T

            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(
            accountPreference: AccountPreference
        ): ViewModelFactory = INSTANCE ?: synchronized(this) {
            INSTANCE ?: ViewModelFactory(accountPreference)
        }
    }
}