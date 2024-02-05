/*
package com.example.greenkim.api.member

import com.example.greenkim.api.auth.AuthApiService
import com.example.greenkim.api.auth.InterCeptor
import com.example.greenkim.api.member.DTO.profile.AllSettingResponseDto
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface MemberApiService {

    //requestbody 없고 response만 존재
    @Headers("Content-Type: application/json")
    @GET("/member")
    fun getSetting():Call<AllSettingResponseDto>

    companion object {
        private const val BASE_URL = "http://13.237.86.105:8080/"
        fun create(): MemberApiService {
            val gson: Gson = GsonBuilder().setLenient().create()
            val interceptor = HttpLoggingInterceptor().apply {
                this.level = HttpLoggingInterceptor.Level.BODY
            }
            val client= OkHttpClient.Builder()
                .addInterceptor(InterCeptor())
                .addInterceptor(interceptor)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(MemberApiService::class.java)

        }
    }
}*/
