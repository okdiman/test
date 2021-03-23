package com.skillbox.skillbox.viewhomework

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity: AppCompatActivity(R.layout.activity_second) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        val message = intent.getStringExtra(KEY_MESSAGE)
        messageTextView.text = message
    }
    companion object{
        private const val KEY_MESSAGE = "message key"
        fun getIntent(context: Context, message:String?): Intent{
            return Intent(context, SecondActivity::class.java)
                .putExtra(KEY_MESSAGE, message)
        }
    }
}