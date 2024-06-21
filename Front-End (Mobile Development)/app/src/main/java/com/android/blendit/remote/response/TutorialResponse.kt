package com.android.blendit.remote.response

import com.google.gson.annotations.SerializedName

data class TutorialResponse (
    @field:SerializedName("error")
    val status: String,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("analystResult")
    val tutorialResult: TutorialResult
)

data class TutorialResult (
    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("idRecommendation")
    val idRecommendation: String? = null,

    @field:SerializedName("facetype")
    val faceType: String? = null,

    @field:SerializedName("skin_preparation")
    val skinPreparation: String? = null,

    @field:SerializedName("base_makeup")
    val baseMakeup: String? = null,

    @field:SerializedName("eye_makeup")
    val eyeMakeup: String? = null,

    @field:SerializedName("shade_lipstik")
    val shadeLipstik: String? = null,

    @field:SerializedName("image_base")
    val imageBase: String? = null,

    @field:SerializedName("image_eye")
    val imageEye: String? = null,

    @field:SerializedName("image_lips")
    val imageLips: String? = null

)