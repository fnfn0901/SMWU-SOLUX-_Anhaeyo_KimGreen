package com.example.greenkim

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.greenkim.databinding.ActivityCommunityBinding
import com.google.android.material.tabs.TabLayoutMediator

class CommunityActivity : AppCompatActivity() {

    private val binding by lazy { ActivityCommunityBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val fragmentToLoad = intent.getStringExtra("fragmentToLoad")

        val fragmentList = listOf(CommunityFragment(), ConfirmationFragment())

        val adapterFragment = FragmentAdapter(this)
        adapterFragment.fragmentList = fragmentList

        binding.viewPager.adapter = adapterFragment

        val tabTitles = listOf<String>("Community", "Proof")
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()

        fragmentToLoad?.let {
            when (it) {
                "ConfirmationFragment" -> binding.viewPager.currentItem = 1
                "ProofFragment" -> binding.viewPager.currentItem = 2
            }
        }
    }
}