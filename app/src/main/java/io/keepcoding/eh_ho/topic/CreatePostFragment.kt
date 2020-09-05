package io.keepcoding.eh_ho.topic

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import io.keepcoding.eh_ho.LoadingDialogFragment
import io.keepcoding.eh_ho.R
import io.keepcoding.eh_ho.data.CreatePostModel
import io.keepcoding.eh_ho.data.PostsRepo
import io.keepcoding.eh_ho.data.RequestError
import io.keepcoding.eh_ho.data.Topic
import io.keepcoding.eh_ho.inflate
import io.keepcoding.eh_ho.topics.TAG_LOADING_DIALOG
import kotlinx.android.synthetic.main.fragment_create_post.*
import kotlinx.android.synthetic.main.fragment_create_topic.*
import java.lang.IllegalArgumentException

class CreatePostFragment(val topic: Topic): Fragment() {
    var interactionListener: CreatePostInteractionListener? = null
    val loadingDialogFragment: LoadingDialogFragment by lazy {
        val message = getString(R.string.label_creating_post)
        LoadingDialogFragment.newInstance(message)
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is CreatePostInteractionListener)
            this.interactionListener = context
        else
            throw IllegalArgumentException("Context doesn't implement ${CreatePostInteractionListener::class.java.canonicalName}")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return container?.inflate(R.layout.fragment_create_post)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        labelTopic.text = this.topic.title
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_create_post, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_send_post -> createPost()
        }

        return super.onOptionsItemSelected(item)
    }

    fun createPost() {
        if (this.isFormValid()) {
            this.postPost()
        } else {
            this.showErrors()
        }
    }

    private fun postPost() {
        enableLoadingDialog()

        val model = CreatePostModel(this.topic.id, inputBody.text.toString())

        context?.let {
            PostsRepo.addPost(
                it,
                model,
                {
                    enableLoadingDialog(false)
                    this.interactionListener?.onPostCreated()
                },
                {
                    enableLoadingDialog(false)
                    this.handleError(it)
                }
            )
        }
    }

    private fun enableLoadingDialog(enabled: Boolean = true) {
        if (enabled)
            loadingDialogFragment.show(childFragmentManager, TAG_LOADING_DIALOG)
        else
            loadingDialogFragment.dismiss()
    }

    private fun isFormValid(): Boolean {
        return inputBody.text.isNotBlank()
    }

    private fun showErrors() {
        if (inputBody.text.isBlank()) {
            inputBody.error = "This field cannot be empty"
        }
    }

    private fun handleError(error: RequestError) {
        val message =
            if (error.messageResId != null)
                getString(error.messageResId)
            else error.message ?: getString(R.string.error_default)

        Snackbar.make(containerPostForm, message, Snackbar.LENGTH_LONG).show()
    }

    interface CreatePostInteractionListener {
        fun onPostCreated()
    }
}