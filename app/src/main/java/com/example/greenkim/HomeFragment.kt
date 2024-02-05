package com.example.greenkim

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.greenkim.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            communityButton.setOnClickListener {
                startActivity(
                    Intent(requireContext(), CommunityActivity::class.java).apply {
                        addFlags(
                            Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
                        )
                    }
                )
            }

            confirmationButton.setOnClickListener {
                startActivity(
                    Intent(requireContext(), CommunityActivity::class.java).apply {
                        putExtra("fragmentToLoad", "ProofFragment")
                        addFlags(
                            Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
                        )
                    }
                )
            }

            btnEReceipt.setOnClickListener {
                Intent(requireContext(), CheckPostActivity::class.java).apply {
                    putExtra("selectedBoard", "전자영수증 발급받기")
                    addFlags(
                        Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    )
                }
            }

            btnReuse.setOnClickListener {
                Intent(requireContext(), CheckPostActivity::class.java).apply {
                    putExtra("selectedBoard", "리유저블 활동")
                    addFlags(
                        Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    )
                }
            }

            btnPlasticFree.setOnClickListener {
                Intent(requireContext(), CheckPostActivity::class.java).apply {
                    putExtra("selectedBoard", "플라스틱 프리")
                    addFlags(
                        Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    )
                }
            }

            btnPlogging.setOnClickListener {
                Intent(requireContext(), CheckPostActivity::class.java).apply {
                    putExtra("selectedBoard", "플로깅")
                    addFlags(
                        Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    )
                }
            }

            btnReform.setOnClickListener {
                Intent(requireContext(), com.example.greenkim.CheckPostActivity::class.java).apply {
                    putExtra("selectedBoard", "리폼")
                    addFlags(
                        android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP or android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
                    )
                }
            }

            btnReform.setOnClickListener {
                Intent(requireContext(), com.example.greenkim.CheckPostActivity::class.java).apply {
                    putExtra("selectedBoard", "대중교통 자전거")
                    addFlags(
                        android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP or android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
                    )
                }
            }

            btnEtc.setOnClickListener {
                Intent(requireContext(), com.example.greenkim.CheckPostActivity::class.java).apply {
                    putExtra("selectedBoard", "기타")
                    addFlags(
                        android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP or android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
                    )
                }
            }
        }
    }
}