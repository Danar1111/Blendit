package com.android.blendit.ui.activity.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.android.blendit.R
import com.android.blendit.preference.AccountPreference
import com.android.blendit.ui.activity.login.LoginActivity
import com.android.blendit.ui.activity.main.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private val launchTime = 2000L
    private val accountLogin by lazy { AccountPreference(this).getLoginInfo() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        setFullscreen()
        launchApp()
    }

    private fun launchApp() {
        lifecycleScope.launch {
            delay(launchTime)
            withContext(Dispatchers.Main) {
                val intent = if (accountLogin.token.isNullOrEmpty()) {
                    Intent(this@SplashActivity, LoginActivity::class.java)
                } else {
                    Intent(this@SplashActivity, MainActivity::class.java)
                }
                startActivity(intent)
                finish()
            }
        }
    }

    private fun setFullscreen() {
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}