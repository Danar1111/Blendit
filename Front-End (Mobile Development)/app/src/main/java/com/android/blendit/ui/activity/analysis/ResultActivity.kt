package com.android.blendit.ui.activity.analysis

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.android.blendit.databinding.ActivityResultBinding
import com.android.blendit.ui.activity.tutorial.TutorialActivity

class ResultActivity : AppCompatActivity() {

    private val binding by lazy { ActivityResultBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val faceType = intent.getStringExtra(EXTRA_FACE_TYPE)
        val skinTone = intent.getStringExtra(EXTRA_SKIN_TONE)
        val undertone = intent.getStringExtra(EXTRA_UNDERTONE)
        val skinType = intent.getStringExtra(EXTRA_SKIN_TYPE)
        val description = intent.getStringExtra(EXTRA_DESCRIPTION)

        // Log check value
        Log.d("ResultActivity", "Face Type: $faceType")
        Log.d("ResultActivity", "Skin Tone: $skinTone")
        Log.d("ResultActivity", "Undertone: $undertone")
        Log.d("ResultActivity", "Skin Type: $skinType")
        Log.d("ResultActivity", "Description: $description")

        binding.tvFaceType.text = faceType
        binding.tvSkinTone.text = skinTone
        binding.tvUndertone.text = undertone
        binding.tvSkinType.text = skinType
        binding.tvDescription.text = description

        binding.buttonResult.setOnClickListener {
            val tutorialIntent = Intent(this, TutorialActivity::class.java).apply {
                putExtra(TutorialActivity.EXTRA_FACE_TYPE, faceType)
                putExtra(TutorialActivity.EXTRA_SKIN_TONE, skinTone)
                putExtra(TutorialActivity.EXTRA_UNDERTONE, undertone)
                putExtra(TutorialActivity.EXTRA_SKIN_TYPE, skinType)
            }
            startActivity(tutorialIntent)
        }
        binding.toolbar.setNavigationOnClickListener { finish() }
    }

    companion object {
        const val EXTRA_FACE_TYPE = "EXTRA_FACE_TYPE"
        const val EXTRA_SKIN_TONE = "EXTRA_SKIN_TONE"
        const val EXTRA_UNDERTONE = "EXTRA_UNDERTONE"
        const val EXTRA_SKIN_TYPE = "EXTRA_SKIN_TYPE"
        const val EXTRA_DESCRIPTION = "EXTRA_DESCRIPTION"
    }
}