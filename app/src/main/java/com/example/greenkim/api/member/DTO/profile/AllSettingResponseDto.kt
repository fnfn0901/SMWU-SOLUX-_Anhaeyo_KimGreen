package com.example.greenkim.api.member.DTO.profile

import com.example.greenkim.api.member.DTO.MemberData
import com.google.gson.annotations.SerializedName

data class AllSettingResponseDto (
    @SerializedName("code")val status:Int,
    @SerializedName("success")val success:Boolean,
    @SerializedName("msg")val message:String,
    @SerializedName("data") val data: MemberData
)

