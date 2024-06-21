package com.android.blendit.remote.response

import com.google.gson.annotations.SerializedName

data class ResponseUploadProfilePicture(

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("photoUrl")
	val photoUrl: String
)
