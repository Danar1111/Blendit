package com.android.blendit.remote.response

import com.google.gson.annotations.SerializedName

data class CategoryResponse (
    @field:SerializedName("error")
    val status: String,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("totalItems")
    val totalItems: Int,

    @field:SerializedName("items")
    val listCategory: List<ListCategory>
)

data class ListCategory (
    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("category")
    val category: String? = null,

    @field:SerializedName("thumbnail")
    val thumbnail: String? = null
)