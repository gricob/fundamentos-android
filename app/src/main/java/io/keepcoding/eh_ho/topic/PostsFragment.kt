package io.keepcoding.eh_ho.topic

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import io.keepcoding.eh_ho.R
import io.keepcoding.eh_ho.data.PostsRepo
import io.keepcoding.eh_ho.inflate
import kotlinx.android.synthetic.main.fragment_posts.*

class PostsFragment(private val topicId: String): Fragment() {

    private val postsAdapter: PostsAdapter by lazy {
        val adapter = PostsAdapter()
        adapter
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return container?.inflate(R.layout.fragment_posts)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listPosts.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        listPosts.adapter = postsAdapter

        swipeRefreshPosts.setOnRefreshListener {
            this.loadPosts()
        }
    }

    override fun onResume() {
        super.onResume()

        this.loadPosts()
    }

    private fun loadPosts() {
        context?.let {
            PostsRepo
                .getByTopic(
                    it.applicationContext,
                    this.topicId,
                    {
                        postsAdapter.setPosts(it)
                        swipeRefreshPosts.isRefreshing = false
                    },
                    {
                        Log.e("LoadPosts", it.toString())
                        swipeRefreshPosts.isRefreshing = false
                    }
                )
        }
    }
}