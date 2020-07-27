package io.keepcoding.eh_ho.topics

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.keepcoding.eh_ho.R
import io.keepcoding.eh_ho.Topic
import io.keepcoding.eh_ho.inflate
import kotlinx.android.synthetic.main.item_topic.view.*
import java.lang.IllegalArgumentException

class TopicsAdapter(val topicClickListener: ((Topic) -> Unit)? = null) : RecyclerView.Adapter<TopicsAdapter.TopicHolder>() {

    private val topics = mutableListOf<Topic>()

    private val listener : ((View) -> Unit) = {
        if (it.tag is Topic) {
            topicClickListener?.invoke(it.tag as Topic)
        } else {
            throw IllegalArgumentException("Topic item view has not a Topic Data as a tag")
        }

    }

    override fun getItemCount(): Int {
        return topics.size
    }

    override fun onCreateViewHolder(list: ViewGroup, viewType: Int): TopicHolder {
        val view = list.inflate(R.layout.item_topic)
        return TopicHolder(view)
    }

    override fun onBindViewHolder(holder: TopicHolder, position: Int) {
        val topic = topics[position]
        holder.topic = topic
        holder.itemView.setOnClickListener(listener)
    }

    fun setTopics(topics: List<Topic>) {
        this.topics.clear()
        this.topics.addAll(topics)
        notifyDataSetChanged()
    }

    inner class TopicHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var topic: Topic? = null
        set(value) {
            field = value
            itemView.tag = field
            itemView.labelTopic.text = field?.title
        }
    }

}
