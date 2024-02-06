package com.example.greenkim

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.greenkim.databinding.ActivitySettingBinding
import com.google.android.material.card.MaterialCardView
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.CalendarMonth
import com.kizitonwose.calendar.view.MonthDayBinder
import com.kizitonwose.calendar.view.MonthScrollListener
import com.kizitonwose.calendar.view.ViewContainer
import java.text.SimpleDateFormat
import java.time.YearMonth
import java.time.temporal.WeekFields
import java.util.*
import kotlin.random.Random

class SettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingBinding
    private val uploadedImages = mutableListOf<Uri>()

    private val pickMedia =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                Log.d("SettingActivity", "선택된 URI: $uri")
                uploadedImages.add(uri)
            } else {
                Log.d("SettingActivity", "미디어가 선택되지 않았습니다.")
            }
        }

    private val badgeList by lazy {
        listOf(
            BadgeList.ADVENTURER.apply { isProfileBadge = true },
            BadgeList.MENTEE.apply { isProfileBadge = true },
            BadgeList.PLASTIC_3.apply { isProfileBadge = true },
            BadgeList.GOLDEN_KIMGREEN.apply { isProfileBadge = true },
            BadgeList.YEONDU.apply { isProfileBadge = true },
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUi()
    }

    private fun initUi() = with(binding) {
        // 이미지 업로드 다이얼로그 표시
        imageView7.setOnClickListener {
            showImageSourceDialog()
        }

        // 댓글 알림 스위치 리스너 설정
        switchView1.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                textView1.text = "댓글 알림"
            } else {
                textView1.text = "댓글 알림"
            }
        }

        // MyPageActivity1으로 이동
        button2.setOnClickListener {
            val nextIntent = Intent(this@SettingActivity, MyPageActivity1::class.java)
            startActivity(nextIntent)
        }

        // MyPageActivity2으로 이동
        button3.setOnClickListener {
            val nextIntent = Intent(this@SettingActivity, MyPageActivity2::class.java)
            startActivity(nextIntent)
        }

        // 로그인 페이지로 이동
        button6.setOnClickListener {
            val nextIntent = Intent(this@SettingActivity, LoginActivity::class.java)
            startActivity(nextIntent)
        }

        // 탈퇴 페이지로 이동
        button7.setOnClickListener {
            val nextIntent = Intent(this@SettingActivity, WithdrawActivity::class.java)
            startActivity(nextIntent)
        }

        // 캘린더뷰 초기화
        initializeCalendarView()
    }

    private fun showImageSourceDialog() {
        val options = arrayOf("갤러리에서 사진 가져오기")

        AlertDialog.Builder(this@SettingActivity)
            .setTitle("이미지 업로드")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> openGallery()
                }
            }
            .show()
    }

    private fun openGallery() {
        pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun initializeCalendarView() {
        // 이번달
        val currentMonth = YearMonth.now()

        // 시작월 (시작월 전으로는 달력 표시 안함)
        val startMonth = currentMonth.minusMonths(10)

        // 종료월 (종료월 이후로는 달력 표시 안함)
        val endMonth = currentMonth.plusMonths(10)

        // Week 의 시작 요일 (한국은 일요일부터 시작)
        val firstDayOfWeek = WeekFields.of(Locale.KOREA).firstDayOfWeek

        binding.calendarView.monthScrollListener = object : MonthScrollListener {
            override fun invoke(month: CalendarMonth) {
                binding.previousMonthButton.run {
                    isEnabled = startMonth != month.yearMonth
                    setOnClickListener {
                        binding.calendarView.smoothScrollToMonth(month.yearMonth.minusMonths(1))
                    }
                }

                binding.nextMonthButton.run {
                    isEnabled = endMonth != month.yearMonth
                    setOnClickListener {
                        binding.calendarView.smoothScrollToMonth(month.yearMonth.plusMonths(1))
                    }
                }

                binding.currentMonthTextView.text = SimpleDateFormat("MMM", Locale.US).format(
                    Calendar.getInstance()
                        .apply {
                            set(month.yearMonth.year, month.yearMonth.monthValue - 1, 1)
                        }
                        .time
                )
            }
        }

        binding.calendarView.run {
            dayBinder = object : MonthDayBinder<DayViewContainer> {
                // 작성한 글이 없을때의 색상
                private val noItemColor = Color.parseColor("#f0f0f0")

                // 작성한 글이 있을때의 색상 (글 갯수에 따라 해당 색상의 Alpha (투명도) 값을 달리 한다.)
                private val hasItemColor = Color.parseColor("#ff868c")

                override fun bind(container: DayViewContainer, data: CalendarDay) {
                    val view = container.cardView

                    // 글 갯수 (랜덤)
                    val count = Random.nextInt(0, 5)

                    if (count == 0) {
                        view.run {
                            setCardBackgroundColor(noItemColor)
                            alpha = 1f
                        }

                        container.view.setOnClickListener(null)
                    } else {
                        // Alpha 값
                        // 랜덤으로 가져오는 글 갯수는 0 ~ 4 까지 이다.
                        // 0을 제외한 나머지 1 ~ 4 값을 4 (0을 제외한 최대 갯수) 로 나눈 값을 알파값이다고 한다.
                        val alpha = (0xff * (count / 4f)) / 0xff

                        view.run {
                            setCardBackgroundColor(hasItemColor)
                            this.alpha = alpha
                        }

                        container.view.setOnClickListener {
                            startActivity(
                                Intent(
                                    this@SettingActivity,
                                    CalendarActivity::class.java
                                ).apply {
                                    addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                })
                        }
                    }
                }

                override fun create(view: View): DayViewContainer = DayViewContainer(view)
            }

            setup(startMonth, endMonth, firstDayOfWeek)
            scrollToMonth(currentMonth)
        }
    }

    private class DayViewContainer(view: View) : ViewContainer(view) {
        val cardView: MaterialCardView = view.findViewById(R.id.card_view)
    }
}
