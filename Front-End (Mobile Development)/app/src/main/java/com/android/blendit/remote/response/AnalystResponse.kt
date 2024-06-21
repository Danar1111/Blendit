package com.android.blendit.remote.response

import com.google.gson.annotations.SerializedName

data class AnalystResponse (
    @field:SerializedName("status")
    val status: String,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("data")
    val analystResult: AnalystResult
)

data class AnalystResult (
    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("shape")
    val shape: String? = null,

    @field:SerializedName("skintone")
    val skintone: String? = null,

    @field:SerializedName("undertone")
    val undertone: String? = null,

    @field:SerializedName("skin_type")
    val skinType: String? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("confidenceScore")
    val confidenceScore: Double? = null,

    @field:SerializedName("createdAt")
    val createdAt: String? = null


)