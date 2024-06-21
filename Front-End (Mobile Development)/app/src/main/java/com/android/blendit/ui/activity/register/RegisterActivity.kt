package com.android.blendit.ui.activity.register

import android.content.DialogInterface
import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doAfterTextChanged
import com.android.blendit.databinding.ActivityRegisterBinding
import com.android.blendit.preference.AccountPreference
import com.android.blendit.remote.Result
import com.android.blendit.viewmodel.ViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class RegisterActivity : AppCompatActivity() {

    private val loginPreferences by lazy { AccountPreference(this) }
    private val binding by lazy { ActivityRegisterBinding.inflate(layoutInflater) }
    private val registerViewModel by viewModels<RegisterViewModel> {
        ViewModelFactory.getInstance(
            loginPreferences
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setFullScreen()
        setView()
    }

    private fun setFullScreen() {
//        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setView() {
        with(binding) {
            registerButton.setOnClickListener { checkForm() }
            toLoginPage.setOnClickListener { finish() }

            emailEditText.doAfterTextChanged {
                if (it != null) {
                    emailErrorTextView.visibility =
                        if (Patterns.EMAIL_ADDRESS.matcher(it)
                                .matches() || it.isEmpty()
                        ) View.GONE else View.VISIBLE
                }
            }
            passwordEditText.doAfterTextChanged {
                if (it != null) {
                    passwordErrorTextView.visibility =
                        if (it.length >= 6 || it.isEmpty()) View.GONE else View.VISIBLE
                }
            }
            retypePasswordEditText.doAfterTextChanged {
                if (it != null) {
                    retypePasswordErrorTextView.visibility =
                        if (it.toString() == passwordEditText.text.toString() || it.isEmpty()) View.GONE else View.VISIBLE
                }
            }
        }
    }

    private fun checkForm() {
        val name = binding.nameEditText.text.toString()
        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()
        val retypePassword = binding.retypePasswordEditText.text.toString()

        if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && retypePassword.isNotEmpty()) {
            registeringAccount(name, email, password)
        }
    }

    private fun registeringAccount(name: String, email: String, password: String) {
        registerViewModel.userRegister(name, email, password)
            .observe(this@RegisterActivity) { result ->
                if (result != null) {
                    when (result) {
                        is Result.Loading -> {
                            showLoading(true)
                        }

                        is Result.Error -> {
                            showLoading(false)
                            showDialog(
                                "Pendaftaran gagal", "Harap coba kembali"
                            ) { it.dismiss() }
                        }

                        is Result.Success -> {
                            showLoading(false)
                            registerSuccess()
                        }
                    }
                }
            }
    }

    private fun showDialog(
        title: String, message: String, positiveAction: (dialog: DialogInterface) -> Unit
    ) {
        MaterialAlertDialogBuilder(this).apply {
            setTitle(title)
            setMessage(message)
            setPositiveButton("OK") { dialog, _ ->
                positiveAction.invoke(dialog)
            }
            setCancelable(false)
            create()
            show()
        }
    }

    private fun registerSuccess() {
        showDialog(
            "Sukses", "Pendaftaran berhasil, silahkan login"
        ) { finish() }
        binding.nameEditText.text?.clear()
        binding.emailEditText.text?.clear()
        binding.passwordEditText.text?.clear()
        binding.retypePasswordEditText.text?.clear()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}