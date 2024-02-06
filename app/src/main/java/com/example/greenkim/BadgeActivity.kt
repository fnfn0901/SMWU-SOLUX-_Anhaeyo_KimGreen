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
    private lateinit var profileBadgeList: MutableList<BadgeList>
    private lateinit var profileBadgeAdapter: BadgeAdapter
    private lateinit var earnedAdapter: BadgeAdapter
    private val profileBadgeQueue: Queue<BadgeList> = LinkedList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_badge)

        // 뒤로가기 버튼 설정
        val backButton: ImageButton = findViewById(R.id.back_button)
        backButton.setOnClickListener {
            onBackPressed()
        }

        // Profile Badge RecyclerView 설정
        val profileBadgeRecyclerView: RecyclerView = findViewById(R.id.profile_badge_recycler_view)
        profileBadgeRecyclerView.layoutManager = GridLayoutManager(this, 5)
        profileBadgeList = mutableListOf() // 프로필 뱃지 리스트 초기화
        profileBadgeAdapter = BadgeAdapter(profileBadgeList,
            { clickedBadge: BadgeList -> showChangeRepresentativeBadgeDialog(clickedBadge) },
            { clickedBadge: BadgeList -> showBadgePopup(clickedBadge) }
        )
        profileBadgeRecyclerView.adapter = profileBadgeAdapter
        updateProfileBadgeList()

        // 프로필 뱃지 설정 함수
        fun setProfileBadge(badgeData: BadgeList) {
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
    }

    private fun updateProfileBadgeList() {
        profileBadgeList = profileBadgeQueue.toList().toMutableList()
        profileBadgeAdapter.updateBadgeList(profileBadgeList)
    }

    private fun showChangeRepresentativeBadgeDialog(badgeData: BadgeList) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("대표 뱃지 변경")
            .setMessage("대표 뱃지를 변경하시겠습니까?")
            .setPositiveButton("아니오") { dialog, _ ->
                dialog.dismiss()
            }
            .setNegativeButton("예") { dialog, _ ->
                setProfileBadge(badgeData)
                dialog.dismiss()
            }
            .show()
    }

    private fun setProfileBadge(badgeData: BadgeList) {
        if (badgeData.isProfileBadge) {
            return
        }

        profileBadgeQueue.offer(badgeData)
        badgeData.isProfileBadge = true

        if (profileBadgeQueue.size > 5) {
            val removedBadge = profileBadgeQueue.poll()
            removedBadge?.isProfileBadge = false
        }

        updateProfileBadgeList()
    }

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