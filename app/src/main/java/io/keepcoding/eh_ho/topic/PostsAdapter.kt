package io.keepcoding.eh_ho.topic

import android.text.Html
import android.text.format.DateUtils
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.keepcoding.eh_ho.R
import io.keepcoding.eh_ho.data.Post
import io.keepcoding.eh_ho.inflate
import kotlinx.android.synthetic.main.item_post.view.*

class PostsAdapter(): RecyclerView.Adapter<PostsAdapter.PostHolder>() {
    private val posts = mutableListOf<Post>()

    override fun onCreateViewHolder(list: ViewGroup, viewType: Int): PostHolder {
        val view = list.inflate(R.layout.item_post)
        return PostHolder(view)
    }

    override fun getItemCount(): Int {
        return this.posts.size
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        val post = this.posts[position]
        holder.post = post
    }

    fun setPosts(posts: List<Post>) {
        this.posts.clear()
        this.posts.addAll(posts)
        notifyDataSetChanged()
    }

    inner class PostHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        var post: Post? = null
        set(value) {
            field = value
            itemView.tag = field

            field?.let {
                itemView.labelAuthor.text = it.author
                itemView.labelContent.setText(Html.fromHtml(it.content))
                itemView.labelPublishedAt.text = DateUtils.getRelativeTimeSpanString(
                    it.publishedAt.time,
                    System.currentTimeMillis(),
                    DateUtils.DAY_IN_MILLIS
                )
            }
        }
    }
}