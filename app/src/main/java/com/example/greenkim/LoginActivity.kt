package com.example.greenkim

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.greenkim.api.auth.App
import com.example.greenkim.api.auth.AuthApiService
import com.example.greenkim.api.auth.DTO.Login.LoginRequestDto
import com.example.greenkim.api.auth.DTO.Login.LoginResponseDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class LoginActivity : AppCompatActivity() {

    private lateinit var idEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var signinErrorMessage: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginButton = findViewById<Button>(R.id.login_button)
        val signupButton = findViewById<Button>(R.id.signup_button)

        idEditText = findViewById(R.id.id_edit_text)
        passwordEditText = findViewById(R.id.password_edit_text)
        signinErrorMessage = findViewById(R.id.signin_error_message)

        fun loginService(email: String, password: String) {
            val loginService = AuthApiService.create()

            loginService.logIn(LoginRequestDto(email,password))
                .enqueue(object : Callback<LoginResponseDto> {
                    override fun onResponse(
                        call: Call<LoginResponseDto>,
                        response: Response<LoginResponseDto>
                    ) {
                        if (response.isSuccessful) {
                            val loginResponse = response.body() ?: return

                            //save token
                            App.prefs.token = loginResponse.data.accessToken

                            //get token
                            //val token = App.prefs.token

                        } else{
                            Log.v("fail!!","fail")
                        }
                    }
                    override fun onFailure(call: Call<LoginResponseDto>, t: Throwable) {
                        Log.d("f!!", t.localizedMessage!!.toString())
                        Toast.makeText(this@LoginActivity, "server X", Toast.LENGTH_SHORT).show()
                    }
                })
        }
        loginButton.setOnClickListener {
            val id = idEditText.text.toString()
            val password = passwordEditText.text.toString()

            // 아이디와 비밀번호가 빈 경우
            if (id.isEmpty()) {
                showErrorMessage("아이디를 입력해주세요.")
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                showErrorMessage("비밀번호를 입력해주세요.")
                return@setOnClickListener
            }
            loginService(id,password)
            val intent = Intent(this, setgetActivity::class.java)
            startActivity(intent)
            //finish()
        }
        signupButton.setOnClickListener {
            // SignUpActivity로 이동
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showErrorMessage(message: String) {
        signinErrorMessage.text = message
        signinErrorMessage.visibility = View.VISIBLE
    }
}