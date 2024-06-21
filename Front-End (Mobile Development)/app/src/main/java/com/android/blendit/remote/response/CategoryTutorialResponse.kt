package com.android.blendit.remote.response

import com.google.gson.annotations.SerializedName

data class CategoryTutorialResponse (
    @field:SerializedName("error")
    val status: String,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("items")
    val categoryTutorialResult: CategoryTutorialResult
)

data class CategoryTutorialResult (
    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("category")
    val category: String? = null,

    @field:SerializedName("skinPreparation")
    val skinPreparation: String? = null,

    @field:SerializedName("baseMakeup")
    val baseMakeup: String? = null,

    @field:SerializedName("eyeMakeup")
    val eyeMakeup: String? = null,

    @field:SerializedName("lipsMakeup")
    val lipsMakeup: String? = null,

    @field:SerializedName("skinPrepPic")
    val skinPrepPic: String? = null,

    @field:SerializedName("baseMakeupPic")
    val baseMakeupPic: String? = null,

    @field:SerializedName("eyeMakeupPic")
    val eyeMakeupPic: String? = null,

    @field:SerializedName("lipsMakeupPic")
    val lipsMakeupPic: String? = null,

    @field:SerializedName("thumbnail")
    val thumbnail: String? = null


)