package com.example.greenkim

import java.io.Serializable

data class posts(
    var no: Int,
    var date: String,
    var title: String,
    var profile_pic: Int,
    var nickname: String,
    var contents: String,
    var likeCounts: Int,
    var chatCounts: Int,
    var liked: Boolean = false
): Serializable
