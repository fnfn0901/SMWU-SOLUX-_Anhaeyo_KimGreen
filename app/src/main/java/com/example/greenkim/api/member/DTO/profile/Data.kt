package com.example.greenkim.api.member.DTO.profile

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("nickname") val nickname: String,
    @SerializedName("profileImg") val profileImg: String,
    @SerializedName("profileBadge") val profileBadge: String,
    @SerializedName("profileBadgeImg") val profileBadgeImg: String,
    @SerializedName("commentAgreement") val commentAgreement:Boolean,
    @SerializedName("likeAgreement") val likeAgreement:Boolean
)

