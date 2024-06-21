package com.android.blendit.ui.activity.category

import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.android.blendit.databinding.ActivityCategoryTutorialBinding
import com.android.blendit.preference.AccountPreference
import com.android.blendit.remote.Result
import com.android.blendit.viewmodel.ViewModelFactory
import com.bumptech.glide.Glide

class CategoryTutorialActivity : AppCompatActivity() {

    private val binding by lazy { ActivityCategoryTutorialBinding.inflate(layoutInflater) }
    private val accountPreference by lazy { AccountPreference(this) }
    private val tutorialViewModel: CategoryTutorialViewModel by viewModels {
        ViewModelFactory.getInstance(accountPreference)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setTutorial()
        binding.toolbar.setNavigationOnClickListener { finish() }
    }

    private fun setTutorial() {
        val token = accountPreference.getLoginInfo().token
        val categoryId = intent.getStringExtra("CATEGORY_ID") ?: return
        tutorialViewModel.getTutorial(token.toString(), categoryId).observe(this) { result ->
            when (result) {
                is Result.Loading -> {
                    // Show loading indicator if needed
                }

                is Result.Success -> {
                    val tutorial = result.data.categoryTutorialResult
                    // Bind the data to the UI
                    binding.tvSkinPreparation.text = tutorial.skinPreparation
                    binding.tvBaseMakeup.text = tutorial.baseMakeup
                    binding.tvEyeMakeup.text = tutorial.eyeMakeup
                    binding.tvLipstik.text = tutorial.lipsMakeup

                    // Load images using Glide
                    loadImage(tutorial.skinPrepPic.toString(), binding.ivSkinPreparation)
                    loadImage(tutorial.baseMakeupPic.toString(), binding.ivBaseMakeup)
                    loadImage(tutorial.eyeMakeupPic.toString(), binding.ivEyeMakeup)
                    loadImage(tutorial.lipsMakeupPic.toString(), binding.ivLipstik)
                }

                is Result.Error -> {
                    Toast.makeText(this, result.data, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun loadImage(imageUrl: String, imageView: ImageView) {
        Glide.with(this)
            .load(imageUrl)
            .into(imageView)
    }
}