package io.keepcoding.eh_ho.topic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.keepcoding.eh_ho.R
import io.keepcoding.eh_ho.data.PostsRepo
import io.keepcoding.eh_ho.isFirsTimeCreated

const val EXTRA_TOPIC_ID = "TOPIC_ID"

class TopicActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topic)

        if (this.isFirsTimeCreated(savedInstanceState)) {
            val topicId: String = intent.getStringExtra(EXTRA_TOPIC_ID) ?: ""
            this.supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, PostsFragment(topicId))
                .commit()
        }

    }
}