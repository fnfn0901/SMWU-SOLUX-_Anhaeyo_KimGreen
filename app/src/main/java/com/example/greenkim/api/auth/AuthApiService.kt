package com.example.greenkim.api.auth

import com.example.greenkim.api.auth.DTO.Login.LoginRequestDto
import com.example.greenkim.api.auth.DTO.Login.LoginResponseDto
import com.example.greenkim.api.auth.DTO.Refresh.RefreshRequestDto
import com.example.greenkim.api.auth.DTO.Refresh.RefreshResponseDto
import com.example.greenkim.api.auth.DTO.Signup.SignUpRequestDto
import com.example.greenkim.api.auth.DTO.Signup.SignUpResponseDto
import com.example.greenkim.api.member.DTO.profile.AllSettingResponseDto
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import java.io.IOException
import kotlin.jvm.Throws


interface AuthApiService {
    @Headers("Content-Type: application/json")
    @POST("auth/sign-up")
    fun signUp(@Body body: SignUpRequestDto): Call<SignUpResponseDto>
//@Body body: SignUpResponseDto
    @Headers("Content-Type: application/json")
    @POST("auth/log-in")
    fun logIn(@Body body: LoginRequestDto): Call<LoginResponseDto>
    //@Body body:LoginRequestDto

    @Headers("Content-Type: application/json")
    //token header
    @POST("auth/reissue")
    fun reIssue(@Body body: RefreshRequestDto): Call<RefreshResponseDto>

    @POST("auth/logout")
    fun logOut(): Call<LoginResponseDto>

    @Headers("Content-Type: application/json")
    @GET("member")
    fun getSetting():Call<AllSettingResponseDto>

    companion object ApiClient {
        private const val BASE_URL = "http://13.237.86.105:8080/"
        fun create(): AuthApiService {

            val gson:Gson = GsonBuilder().setLenient().create()
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            OkHttpClient.Builder().addInterceptor(InterCeptor())

            val client= OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(AuthApiService::class.java)

        }
    }
}