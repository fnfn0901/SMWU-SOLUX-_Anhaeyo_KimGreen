package com.example.greenkim

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.greenkim.databinding.FragmentCommunityBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CommunityFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class CommunityFragment : Fragment() {


    val binding by lazy { FragmentCommunityBinding.inflate(layoutInflater) }

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        /*val data: MutableList<posts> = loadData()
        var adapter_post = PostsAdapter()
        adapter_post.listData = data

        binding.recyclerView.adpter = adapter_post*/
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val postList = getPosts()
        val adapter = PostsAdapter()
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

        val fabCommunity = view.findViewById<FloatingActionButton>(R.id.FAB_Community)

        fabCommunity.setOnClickListener {
            // FAB_Community를 클릭할 때의 동작 정의
            val intent = Intent(requireContext(), PostActivity::class.java)
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


    /*fun loadData(): MutableList<posts> {
        val data: MutableList<posts> = mutableListOf()

        for (no in 1..10) {
            val title = "example {$no}"
            val date = System.currentTimeMillis()

            var post = posts(
                no,
                title,
                date,
                contents = "this is Example",
                nickname = "엉금엉금",
                chatCounts = "3",
                likeCounts = "2",
                profile_pic = R.drawable.frog
            )

            data.add(post)

        }
        return data
    }*/
}