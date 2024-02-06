package com.example.greenkim.api.auth

import com.example.greenkim.api.auth.App
import okhttp3.Interceptor
import okhttp3.Response

class InterCeptor:Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val req =
            chain.request().newBuilder().addHeader("accessToken", App.prefs.token ?: "").build()
        return chain.proceed(req)
    }

}