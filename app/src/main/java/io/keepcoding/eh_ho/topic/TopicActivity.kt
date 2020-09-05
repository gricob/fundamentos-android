package io.keepcoding.eh_ho.topic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.keepcoding.eh_ho.R
import io.keepcoding.eh_ho.data.PostsRepo
import io.keepcoding.eh_ho.data.Topic
import io.keepcoding.eh_ho.data.TopicsRepo
import io.keepcoding.eh_ho.isFirsTimeCreated
import io.keepcoding.eh_ho.topics.CreateTopicFragment
import io.keepcoding.eh_ho.topics.TRANSACTION_CREATE_TOPIC

const val EXTRA_TOPIC_ID = "TOPIC_ID"
const val TRANSACTION_CREATE_POST = "create_post"

class TopicActivity : AppCompatActivity(),
    PostsFragment.PostsInteractionListener,
    CreatePostFragment.CreatePostInteractionListener
{
    private var topic: Topic? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topic)

        val topicId: String = intent.getStringExtra(EXTRA_TOPIC_ID) ?: ""
        this.topic = TopicsRepo.getTopic(topicId)

        this.topic?.let {
            if (this.isFirsTimeCreated(savedInstanceState)) {
                this.supportFragmentManager.beginTransaction()
                    .add(R.id.fragmentContainer, PostsFragment(it.id))
                    .commit()
            }
        }
    }

    override fun onCreatePost() {
        this.topic?.let {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, CreatePostFragment(it))
                .addToBackStack(TRANSACTION_CREATE_POST)
                .commit()
        }
    }

    override fun onPostCreated() {
        supportFragmentManager.popBackStack()
    }
}