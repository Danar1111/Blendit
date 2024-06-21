package com.android.blendit.remote.response

import com.google.gson.annotations.SerializedName

data class ResponseListProduct(

	@field:SerializedName("totalItems")
	val totalItems: Int,

	@field:SerializedName("totalPages")
	val totalPages: Int,

	@field:SerializedName("pageSize")
	val pageSize: Int,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("currentPage")
	val currentPage: Int,

	@field:SerializedName("items")
	val items: List<ItemsProduct>
)

data class ItemsProduct(

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("brand")
	val brand: String,

	@field:SerializedName("product_name")
	val productName: String,

	@field:SerializedName("picture")
	val picture: String,

	@field:SerializedName("type")
	val type: String,

	@field:SerializedName("skintone")
	val skintone: String,

	@field:SerializedName("skin_type")
	val skinType: String,

	@field:SerializedName("undertone")
	val undertone: String,

	@field:SerializedName("shade")
	val shade: String,

	@field:SerializedName("makeup_type")
	val makeupType: String,
)
