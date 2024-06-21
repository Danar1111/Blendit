package com.android.blendit.remote.response

import com.google.gson.annotations.SerializedName

data class ResponseListFavorite(

	@field:SerializedName("totalItems")
	val totalItems: Int,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("userId")
	val userId: String,

	@field:SerializedName("items")
	val items: List<ItemsFavorite>
)

data class ItemsFavorite(

	@field:SerializedName("product_id")
	val productId: String,

	@field:SerializedName("undertone")
	val undertone: String,

	@field:SerializedName("shade")
	val shade: String,

	@field:SerializedName("type")
	val type: String,

	@field:SerializedName("skintone")
	val skintone: String,

	@field:SerializedName("skin_type")
	val skinType: String,

	@field:SerializedName("brand")
	val brand: String,

	@field:SerializedName("product_name")
	val productName: String,

	@field:SerializedName("makeup_type")
	val makeupType: String,

	@field:SerializedName("picture")
	val picture: String
)
