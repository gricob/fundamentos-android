package io.keepcoding.eh_ho.topics

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import io.keepcoding.eh_ho.R
import io.keepcoding.eh_ho.TopicsRepo
import io.keepcoding.eh_ho.inflate
import kotlinx.android.synthetic.main.fragment_create_topic.*
import java.lang.IllegalArgumentException

class CreateTopicFragment : Fragment() {

    var interactionListener: CreateTopicInteractionListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is CreateTopicInteractionListener)
            this.interactionListener = context
        else
            throw IllegalArgumentException("Context doesn't implement ${CreateTopicInteractionListener::class.java.canonicalName}")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return container?.inflate(R.layout.fragment_create_topic)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_create_topic, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_send -> createTopic()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun createTopic() {
        if (isFormValid()) {
            TopicsRepo.addTopic(
                inputTitle.text.toString(),
                inputContent.text.toString()
            )

            interactionListener?.onTopicCreated()
        } else {
            showErrors()
        }
    }

    private fun showErrors() {
        if (inputTitle.text.isEmpty())
            inputTitle.error = getString(R.string.error_empty)
        if (inputContent.text.isEmpty())
            inputContent.error = getString(R.string.error_empty)
    }

    private fun isFormValid() = inputTitle.text.isNotEmpty() && inputContent.text.isNotEmpty()

    interface CreateTopicInteractionListener {
        fun onTopicCreated()
    }
}