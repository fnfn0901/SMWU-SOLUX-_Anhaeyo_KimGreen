package com.example.greenkim

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.greenkim.databinding.FragmentSettingBinding
import com.google.android.material.card.MaterialCardView
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.CalendarMonth
import com.kizitonwose.calendar.view.MonthDayBinder
import com.kizitonwose.calendar.view.MonthScrollListener
import com.kizitonwose.calendar.view.ViewContainer
import java.text.SimpleDateFormat
import java.time.YearMonth
import java.time.temporal.WeekFields
import java.util.Calendar
import java.util.Locale
import kotlin.random.Random


class SettingFragment : Fragment() {
    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    private val uploadedImages = mutableListOf<Uri>()

    private val pickMedia =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                Log.d("SettingFragment", "Selected URI: $uri")
                uploadedImages.add(uri)
            } else {
                Log.d("SettingFragment", "No media selected")
            }
        }

    private val badgeList by lazy {
        listOf(
            BadgeList.ADVENTURER.apply { isEarned = true },
            BadgeList.MENTEE.apply { isEarned = true },
            BadgeList.PLASTIC_3.apply { isEarned = true },
            BadgeList.GOLDEN_KIMGREEN.apply { isEarned = true },
            BadgeList.YEONDU.apply { isEarned = true },
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
    }

    private fun initUi() = with(binding) {
        imageView7.setOnClickListener {
            showImageSourceDialog()
        }

        switchView1.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                textView1.text = "댓글 알림"
            } else {
                textView1.text = "댓글 알림"
            }
        }

        button2.setOnClickListener {
            val nextIntent = Intent(requireContext(), MyPageActivity1::class.java)
            startActivity(nextIntent)
        }

        button3.setOnClickListener {
            val nextIntent = Intent(requireContext(), MyPageActivity2::class.java)
            startActivity(nextIntent)
        }

        button7.setOnClickListener {
            val nextIntent = Intent(requireContext(), WithdrawActivity::class.java)
            startActivity(nextIntent)
        }

        questionButton.setOnClickListener {
            startActivity(Intent(requireContext(), BadgeActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            })
        }

        //region Initialize badge list
        badgeRecyclerView.adapter = BadgeAdapter(badgeList, {}, {})
        //endregion

        //region Initialize calendar view
        // 이번달
        val currentMonth = YearMonth.now()

        // 시작월 (시작월 전으로는 달력 표시 안함)
        val startMonth = currentMonth.minusMonths(10)

        // 종료월 (종료월 이후로는 달력 표시 안함)
        val endMonth = currentMonth.plusMonths(10)

        // Week 의 시작 요일 (한국은 일요일부터 시작)
        val firstDayOfWeek = WeekFields.of(Locale.KOREA).firstDayOfWeek

        calendarView.monthScrollListener = object : MonthScrollListener {
            override fun invoke(month: CalendarMonth) {
                previousMonthButton.run {
                    isEnabled = startMonth != month.yearMonth
                    setOnClickListener {
                        calendarView.smoothScrollToMonth(month.yearMonth.minusMonths(1))
                    }
                }

                nextMonthButton.run {
                    isEnabled = endMonth != month.yearMonth
                    setOnClickListener {
                        calendarView.smoothScrollToMonth(month.yearMonth.plusMonths(1))
                    }
                }

                currentMonthTextView.text = SimpleDateFormat("MMM", Locale.US).format(
                    Calendar.getInstance()
                        .apply {
                            set(month.yearMonth.year, month.yearMonth.monthValue - 1, 1)
                        }
                        .time
                )
            }
        }

        calendarView.run {
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
                                    requireContext(),
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
        //endregion
    }

    private fun showImageSourceDialog() {
        val options = arrayOf("갤러리에서 사진 가져오기")

        AlertDialog.Builder(requireContext())
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

    private class DayViewContainer(view: View) : ViewContainer(view) {
        val cardView: MaterialCardView = view.findViewById(R.id.card_view)
    }
}
