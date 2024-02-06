package com.example.greenkim

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.greenkim.databinding.Tutorial5Binding

class TutorialFragment5 : Fragment() {

    private var _binding: Tutorial5Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = Tutorial5Binding.inflate(inflater, container, false)
        val view = binding.root

        // startBtn 클릭 시 SignUpActivity로 이동
        binding.startBtn.setOnClickListener {
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
