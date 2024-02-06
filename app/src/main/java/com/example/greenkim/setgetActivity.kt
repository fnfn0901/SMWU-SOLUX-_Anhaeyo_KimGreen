package com.example.greenkim

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.example.greenkim.api.auth.App
import com.example.greenkim.api.auth.AuthApiService
import com.example.greenkim.api.member.DTO.profile.AllSettingResponseDto
import com.example.greenkim.api.member.MemberApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class setgetActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setget)
        val t = findViewById<TextView>(R.id.text1)
        fun getSet() {
            val settingService = AuthApiService.create()
            settingService.getSetting()
                .enqueue(object : Callback<AllSettingResponseDto> {
                    override fun onResponse(
                        call: Call<AllSettingResponseDto>,
                        response: Response<AllSettingResponseDto>
                    ) {
                        if (response.isSuccessful) {
                            val issettingResponse = response.body() ?: return
                            t.text = issettingResponse.data.nickname
                            Toast.makeText(
                                this@setgetActivity,
                                "${issettingResponse.data}",
                                Toast.LENGTH_SHORT
                            ).show()
                            Log.d("OK", "${issettingResponse.data}")
                        } else {
                            Log.d("bad request", "?")
                        }
                    }
                    override fun onFailure(call: Call<AllSettingResponseDto>, t: Throwable) {
                        Log.d("No Internet", t.localizedMessage.toString())
                    }
                })


        }
        getSet()
    }

}