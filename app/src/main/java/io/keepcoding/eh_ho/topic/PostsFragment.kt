package io.keepcoding.eh_ho.topic

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import io.keepcoding.eh_ho.R
import io.keepcoding.eh_ho.data.PostsRepo
import io.keepcoding.eh_ho.inflate
import kotlinx.android.synthetic.main.fragment_posts.*

class PostsFragment(private val topicId: String): Fragment() {

    var postsInteractionListener: PostsInteractionListener? = null

    private val postsAdapter: PostsAdapter by lazy {
        val adapter = PostsAdapter()
        adapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_topic_posts, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_send_post -> this.postsInteractionListener?.onCreatePost()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is PostsInteractionListener)
            postsInteractionListener = context
        else
            throw IllegalArgumentException("Context doesn't implement ${PostsInteractionListener::class.java.canonicalName}")
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
                        swipeRefreshPosts.isRefreshing = false
                    }
                )
        }
    }

    interface PostsInteractionListener {
        fun onCreatePost()
    }
}