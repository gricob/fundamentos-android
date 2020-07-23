package io.keepcoding.eh_ho

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun showTopics(view: View) {
        val intent: Intent = Intent(this, TopicsActivity::class.java)
        startActivity(intent)
    }
}
