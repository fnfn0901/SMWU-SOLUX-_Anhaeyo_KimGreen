package com.example.greenkim

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.LinkedList
import java.util.Queue

class BadgeActivity : AppCompatActivity() {
    //프로필 뱃지
    private var profileBadgeList: MutableList<BadgeList> = mutableListOf()

    //획득한 뱃지
    private val earnedBadgeList: List<BadgeList> = listOf(
        BadgeList.ADVENTURER.apply { isEarned = true },
        BadgeList.MENTEE.apply { isEarned = true },
        BadgeList.PLASTIC_3.apply { isEarned = true },
        BadgeList.GOLDEN_KIMGREEN.apply { isEarned = true },
        BadgeList.YEONDU.apply { isEarned = true },
        BadgeList.PLOGGING_3.apply { isEarned = true },
        BadgeList.PLOGGING_10.apply { isEarned = true },
        BadgeList.EARLYBIRD.apply { isEarned = true }
    )

    //미획득 뱃지
    private val unearnedBadgeList: List<BadgeList> = BadgeList.values().filter { !it.isEarned }

    // 어댑터 선언
    private lateinit var earnedAdapter: BadgeAdapter
    private lateinit var profileBadgeAdapter: BadgeAdapter

    // 프로필 뱃지를 관리하는 큐
    private val profileBadgeQueue: Queue<BadgeList> = LinkedList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_badge)

        // 뒤로가기
        val backButton: ImageButton = findViewById(R.id.back_button)
        backButton.setOnClickListener {
            onBackPressed()
        }

        /// Profile Badge 설정
        val profileBadgeRecyclerView: RecyclerView = findViewById(R.id.profile_badge_recycler_view)
        profileBadgeRecyclerView.layoutManager = GridLayoutManager(this, 5)
        // isProfileBadge가 true로 설정된 뱃지를 필터링
        val profileBadgeList = BadgeList.values().filter { it.isProfileBadge }
        profileBadgeAdapter = BadgeAdapter(profileBadgeList,
            { clickedBadge: BadgeList -> showChangeRepresentativeBadgeDialog(clickedBadge) },
            { clickedBadge: BadgeList -> showBadgePopup(clickedBadge) }
        )
        profileBadgeRecyclerView.adapter = profileBadgeAdapter
        updateProfileBadgeList()

        // Earned Badge 설정
        val earnedBadgeRecyclerView: RecyclerView = findViewById(R.id.earned_badge_recycler_view)
        earnedBadgeRecyclerView.layoutManager = GridLayoutManager(this, 5)
        earnedAdapter = BadgeAdapter(earnedBadgeList,
            { clickedBadge: BadgeList -> showChangeRepresentativeBadgeDialog(clickedBadge) },
            { clickedBadge: BadgeList -> showBadgePopup(clickedBadge) }
        )
        earnedBadgeRecyclerView.adapter = earnedAdapter

        // Unearned Badge 설정
        val unearnedBadgeRecyclerView: RecyclerView = findViewById(R.id.unearned_badge_recycler_view)
        unearnedBadgeRecyclerView.layoutManager = GridLayoutManager(this, 5)
        val unearnedAdapter = BadgeAdapter(unearnedBadgeList,
            { clickedBadge: BadgeList -> showChangeRepresentativeBadgeDialog(clickedBadge) },
            { clickedBadge: BadgeList -> showBadgePopup(clickedBadge) }
        )
        unearnedBadgeRecyclerView.adapter = unearnedAdapter
    }

    // 대표 뱃지 변경 다이얼로그 표시 함수
    private fun showChangeRepresentativeBadgeDialog(badgeData: BadgeList) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("대표 뱃지 변경")
            .setMessage("대표 뱃지를 변경하시겠습니까?")
            .setPositiveButton("아니오") { dialog, _ ->
                dialog.dismiss()
            }
            .setNegativeButton("예") { dialog, _ ->
                // 넘기는 로직 필요
                setProfileBadge(badgeData)
                dialog.dismiss()
            }
            .show()
    }

    // 프로필 뱃지 설정 함수
    private fun setProfileBadge(badgeData: BadgeList) {
        // 이미 isProfileBadge가 true인 경우 아무런 동작도 하지 않음
        if (badgeData.isProfileBadge) {
            return
        }

        // 새로운 뱃지를 큐에 추가
        profileBadgeQueue.offer(badgeData)
        badgeData.isProfileBadge = true

        // 큐의 크기를 5개로 제한
        if (profileBadgeQueue.size > 5) {
            val removedBadge = profileBadgeQueue.poll()
            removedBadge?.isProfileBadge = false
        }

        // RecyclerView 갱신
        updateProfileBadgeList()
    }

    // Profile Badge List 업데이트 함수
    private fun updateProfileBadgeList() {
        // 큐에 있는 뱃지를 리스트로 변환하여 업데이트
        profileBadgeList = profileBadgeQueue.toList().toMutableList()
        profileBadgeAdapter.updateBadgeList(profileBadgeList)
    }

    // 뱃지 정보를 받아 팝업창에 표시하는 함수
    private fun showBadgePopup(badgeData: BadgeList) {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.badge_popup_layout)

        val badgeImage = dialog.findViewById<ImageView>(R.id.popup_badge_image)
        val badgeText = dialog.findViewById<TextView>(R.id.popup_badge_text)
        val badgeAchievementText = dialog.findViewById<TextView>(R.id.badge_achievement)

        badgeImage.setImageResource(badgeData.badgeImg)
        badgeText.text = badgeData.badge
        badgeAchievementText.text = badgeData.badgeAchieve

        val closeButton = dialog.findViewById<ImageView>(R.id.popup_close_button)
        closeButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}
