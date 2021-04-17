package com.skillbox.skillbox.viewhomework

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_deeplink.*

class DeeplinkActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deeplink)
        handleIntentData()
    }

    private fun handleIntentData() {
        intent.data?.lastPathSegment?.let { link ->
            uriAddress.text = link
        }
    }
}