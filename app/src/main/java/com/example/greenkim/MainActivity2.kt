package com.example.greenkim

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.greenkim.databinding.ActivityMain2Binding

class MainActivity2 : AppCompatActivity() {
    private val binding by lazy { ActivityMain2Binding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        with(binding) {
            viewPager.run {
                adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)
                offscreenPageLimit = 2
                isUserInputEnabled = false
            }

            bottomNavigationView.setOnItemSelectedListener {
                val id = it.itemId

                viewPager.currentItem = when (id) {
                    R.id.action_home -> 0
                    R.id.action_community -> 1
                    else -> 2
                }

                return@setOnItemSelectedListener true
            }
        }
    }

    private class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
        FragmentStateAdapter(fragmentManager, lifecycle) {
        override fun getItemCount() = 3

        override fun createFragment(position: Int): Fragment {
            return when (position) {
//                0 -> HomeFragment()
                1 -> CommunityFragment()
                else -> SettingFragment()
            }
        }
    }
}