package com.example.greenkim

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.greenkim.databinding.FragmentNaviBinding

class NaviFragment : Fragment() {

    private lateinit var binding: FragmentNaviBinding
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNaviBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // NavController 초기화
        navController = findNavController()

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.action_home -> {
                    navController.navigate(R.id.action_naviFragment_to_mainActivity)
                    true
                }
                R.id.action_community -> {
                    navController.navigate(R.id.action_naviFragment_to_communityActivity)
                    true
                }
                R.id.action_my_page -> {
                    navController.navigate(R.id.action_naviFragment_to_settingActivity)
                    true
                }
                else -> false
            }
        }
    }
}
