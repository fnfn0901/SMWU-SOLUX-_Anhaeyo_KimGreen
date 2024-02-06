package com.example.greenkim

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView

class SplashActivity : AppCompatActivity() {
    private val SPLASH_DELAY: Long = 1000 // 지연 시간
    private lateinit var splashImage: ImageView // ImageView 선언

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        splashImage = findViewById(R.id.splashImage) // ImageView 초기화

        // 지연 후에 ImageView 나타나기
        Handler().postDelayed({
            splashImage.animate()
                .alpha(1f) // 최종 알파 값
                .setDuration(1000) // 1초 동안 알파 값 변경
                .start()
        }, SPLASH_DELAY)

        // 튜토리얼 액티비티로 이동
        Handler().postDelayed({
            val mainIntent = Intent(this@SplashActivity, TutorialActivity::class.java)
            startActivity(mainIntent)
            finish()
        }, SPLASH_DELAY + 3000) // 이미지 나타나는 시간을 추가하여 메인 액티비티로 이동
    }
}
