package com.android.blendit.ui.activity.tutorial

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.android.blendit.databinding.ActivityTutorialBinding
import com.android.blendit.preference.AccountPreference
import com.android.blendit.remote.response.TutorialResult
import com.android.blendit.ui.activity.recommendation.RecommendationActivity
import com.bumptech.glide.Glide

class TutorialActivity : AppCompatActivity() {

    private val binding by lazy { ActivityTutorialBinding.inflate(layoutInflater) }
    private val tutorialViewModel: TutorialViewModel by viewModels()
    private val accountPreference by lazy { AccountPreference(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        tutorialViewModel.tutorialResult.observe(this, Observer { tutorial ->
            tutorial?.let {
                Log.d("TutorialActivity", "Tutorial received: $tutorial")
                updateUIWithTutorial(tutorial)
            }
        })

        tutorialViewModel.errorMessage.observe(this, Observer { message ->
            message?.let {
                showToast(it)
                Log.e("TutorialActivity", "Error message: $it")
            }
        })

        val loginResult = accountPreference.getLoginInfo()
        val token = loginResult.token
        val shape = intent.getStringExtra(EXTRA_FACE_TYPE) ?: ""
        val skinTone = intent.getStringExtra(EXTRA_SKIN_TONE) ?: ""
        val undertone = intent.getStringExtra(EXTRA_UNDERTONE) ?: ""
        val skinType = intent.getStringExtra(EXTRA_SKIN_TYPE) ?: ""

        tutorialViewModel.getTutorials(token.toString(), shape, skinTone, undertone, skinType)

        binding.buttonTutorial.setOnClickListener {
            navigateToRecommendationActivity(skinTone, undertone, skinType)
        }

        binding.toolbar.setNavigationOnClickListener { finish() }
    }

    private fun updateUIWithTutorial(tutorial: TutorialResult) {
        binding.tvSkinPreparation.text = tutorial.skinPreparation ?: ""
        binding.tvBaseMakeup.text = tutorial.baseMakeup ?: ""
        binding.tvEyeMakeup.text = tutorial.eyeMakeup ?: ""
        binding.tvLipstik.text = tutorial.shadeLipstik ?: ""

        tutorial.imageBase?.let { imageUrl ->
            loadImage(imageUrl, binding.ivBaseMakeup)
        }
        tutorial.imageEye?.let { imageUrl ->
            loadImage(imageUrl, binding.ivEyeMakeup)
        }
        tutorial.imageLips?.let { imageUrl ->
            loadImage(imageUrl, binding.ivLipstik)
        }
    }

    private fun loadImage(imageUrl: String, imageView: ImageView) {
        Glide.with(this)
            .load(imageUrl)
            .into(imageView)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun navigateToRecommendationActivity(
        skinTone: String,
        undertone: String,
        skinType: String
    ) {
        val intent = Intent(this, RecommendationActivity::class.java).apply {
            putExtra(RecommendationActivity.EXTRA_SKIN_TONE, skinTone)
            putExtra(RecommendationActivity.EXTRA_UNDERTONE, undertone)
            putExtra(RecommendationActivity.EXTRA_SKIN_TYPE, skinType)
        }
        startActivity(intent)
    }

    companion object {
        const val EXTRA_FACE_TYPE = "EXTRA_FACE_TYPE"
        const val EXTRA_SKIN_TONE = "EXTRA_SKIN_TONE"
        const val EXTRA_UNDERTONE = "EXTRA_UNDERTONE"
        const val EXTRA_SKIN_TYPE = "EXTRA_SKIN_TYPE"
    }
}