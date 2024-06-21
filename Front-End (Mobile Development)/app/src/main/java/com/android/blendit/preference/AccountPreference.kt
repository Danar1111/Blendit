package com.android.blendit.preference

import android.content.Context
import com.android.blendit.remote.response.LoginResult

class AccountPreference(context: Context) {

    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setLoginInfo(loginResult: LoginResult) {
        val editor = preferences.edit()
        editor.putString(USER_ID, loginResult.userId)
        editor.putString(USERNAME, loginResult.username)
        editor.putString(TOKEN, loginResult.token)
        editor.putString(EMAIL, loginResult.email)
        editor.putString(PROFILEPIC, loginResult.profilePic)
        editor.apply()
    }

    fun setProfilePict(profilePicUrl: String?) {
        val editor = preferences.edit()
        editor.putString(PROFILEPIC, profilePicUrl)
        editor.apply()
    }

    fun getLoginInfo(): LoginResult {
        val userId = preferences.getString(USER_ID, null)
        val userName = preferences.getString(USERNAME, null)
        val token = preferences.getString(TOKEN, null)
        val email = preferences.getString(EMAIL, null)
        val profilePic = preferences.getString(PROFILEPIC, null)
        return LoginResult(userId, email, userName, token, profilePic)
    }

    fun removeLoginUser() {
        val editor = preferences.edit().clear()
        editor.apply()
    }

    companion object {
        const val PREFS_NAME = "login_pref"
        const val USER_ID = "userId"
        const val EMAIL = "email"
        const val USERNAME = "username"
        const val TOKEN = "token"
        const val PROFILEPIC = "profilepic"
    }
}