package com.android.blendit.ui.activity.detail

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.blendit.databinding.ActivityDetailBinding
import com.android.blendit.preference.AccountPreference
import com.android.blendit.viewmodel.ViewModelFactory
import com.bumptech.glide.Glide

class DetailActivity : AppCompatActivity() {

    val binding by lazy { ActivityDetailBinding.inflate(layoutInflater) }
    private val intentId by lazy { intent.getStringExtra(ID) }
    private val intentPicture by lazy { intent.getStringExtra(PICTURE) }
    private val intentBrand by lazy { intent.getStringExtra(BRAND) }
    private val intentProductName by lazy { intent.getStringExtra(PRODUCT_NAME) }
    private val intentIsFavorite by lazy { intent.getBooleanExtra(IS_FAVORITE, false) }
    private val accountPreference by lazy { AccountPreference(this) }
    private val detailViewModel by viewModels<DetailItemViewModel> {
        ViewModelFactory(
            accountPreference
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setFullScreen()
        setView()
    }

    private fun setView() {
        with(binding) {
            Glide.with(this@DetailActivity).load(intentPicture).into(ivPicture)
            tvBrand.text = intentBrand
            tvProductName.text = intentProductName
            btnBack.setOnClickListener { finish() }
            btnFavorite.isChecked = intentIsFavorite
            btnFavorite.addOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    detailViewModel.addFavorite(intentId.toString())
                } else {
                    detailViewModel.removeFavorite(intentId.toString())
                }
            }
        }

    }

    private fun setFullScreen() {
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    companion object {
        const val ID = "id"
        const val PICTURE = "picture"
        const val BRAND = "brand"
        const val PRODUCT_NAME = "product_name"
        const val IS_FAVORITE = "is_favorite"
    }
}