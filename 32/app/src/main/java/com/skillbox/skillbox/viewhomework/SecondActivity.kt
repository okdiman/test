package com.skillbox.skillbox.viewhomework

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class SecondActivity : AppCompatActivity() {
    private val tag = "SecondActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        welcome.setOnClickListener {
//            finish()
//        }
        DebugLogger.d(tag, "onCreate ${hashCode()}")
    }

    override fun onStart() {
        super.onStart()
        DebugLogger.d(tag, "onStart ${hashCode()}")
    }

    override fun onResume() {
        super.onResume()
        DebugLogger.d(tag, "onResume ${hashCode()}")
    }

    override fun onTopResumedActivityChanged(isTopResumedActivity: Boolean) {
        super.onTopResumedActivityChanged(isTopResumedActivity)
        DebugLogger.d(tag, "onTopResumedActivityChanged ${hashCode()} $isTopResumedActivity")
    }
    override fun onPause() {
        super.onPause()
        DebugLogger.d(tag, "onPause ${hashCode()}")
    }

    override fun onStop() {
        super.onStop()
        DebugLogger.d(tag, "onStop ${hashCode()}")
    }

    override fun onDestroy() {
        super.onDestroy()
        DebugLogger.d(tag, "onDestroy ${hashCode()}")
    }
}

object DebugLogger {
    fun d(tag: String, message: String) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, message)
        }
    }
}