package com.android.blendit.remote.response

import com.google.gson.annotations.SerializedName

data class RecommendationResponse (
    @field:SerializedName("error")
    val status: String,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("totalItems")
    val totalItems: Int,

    @field:SerializedName("items")
    val recommendationResult: List<RecommendationResult>
)

data class RecommendationResult (
    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("brand")
    val brand: String? = null,

    @field:SerializedName("product_name")
    val productName: String? = null,

    @field:SerializedName("shade")
    val shade: String? = null,

    @field:SerializedName("type")
    val type: String? = null,

    @field:SerializedName("undertone")
    val undertone: String? = null,

    @field:SerializedName("skintone")
    val skintone: String? = null,

    @field:SerializedName("makeup_type")
    val makeupType: String? = null,

    @field:SerializedName("skin_type")
    val skinType: String? = null,

    @field:SerializedName("picture")
    val picture: String? = null

)