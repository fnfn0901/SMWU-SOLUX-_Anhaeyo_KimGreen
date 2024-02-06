package com.example.greenkim.api.member.DTO.profile

import com.google.gson.annotations.SerializedName

data class AllSettingResponseDto (
    @SerializedName("status")val status:Int,
    @SerializedName("success")val success:Boolean,
    @SerializedName("message")val message:String,
    @SerializedName("data") val data: Data
)
