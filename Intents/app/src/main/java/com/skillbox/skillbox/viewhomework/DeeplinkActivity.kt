package com.skillbox.skillbox.viewhomework

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_deeplink.*

class DeeplinkActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deeplink)
        handleIntentData()

        onNewIntent(intent)
    }

    private fun handleIntentData() {
        intent.data?.lastPathSegment?.let { link ->
            uriAddress.text = link
        }
    }
}