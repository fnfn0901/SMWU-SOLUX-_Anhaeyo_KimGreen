package com.example.greenkim

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.greenkim.databinding.FragmentConfirmationBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ConfirmationFragment : Fragment() {

    val binding by lazy { FragmentConfirmationBinding.inflate(layoutInflater) }
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val postList = getPosts()
        val adapter = PostsAdapter(requireContext())
        adapter.listData = postList
        adapter.onPostItemClickListener = object : PostsAdapter.OnPostItemClickListener {
            override fun onPostItemClick(post: posts) {
                val intent = Intent(requireContext(), DetailPostActivity::class.java)
                intent.putExtra("postId", post.no)
                startActivity(intent)
            }
        }
        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager = LinearLayoutManager(context)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fabProof = view.findViewById<FloatingActionButton>(R.id.FAB_Proof)

        fabProof.setOnClickListener {
            // FAB_Proof를 클릭할 때의 동작 정의
            val intent = Intent(requireContext(), CheckPostActivity::class.java)
            startActivity(intent)
        }
    }


        fun getPosts(): MutableList<posts> {

        var postList = mutableListOf<posts>()
        for (i in 1..10) {
            val title = "샘플: ${i} "
            val day = "202"
            val postdata = posts(
                i,
                date = day,
                title = title,
                profile_pic = R.drawable.plastic_free,
                nickname = "엉금",
                contents = "ex",
                chatCounts = 1,
                likeCounts = 2
            )

            postList.add(postdata)

        }
        return postList
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ConfirmationFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}