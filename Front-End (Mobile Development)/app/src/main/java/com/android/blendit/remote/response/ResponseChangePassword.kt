package com.android.blendit.remote.response

import com.google.gson.annotations.SerializedName

data class ResponseChangePassword (
    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)