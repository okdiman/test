package com.skillbox.skillbox.viewhomework

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.third_activity.*

class ThirdActivity : AppCompatActivity() {
    private val tag = "ThirdActivity"
    private var state: CounterState = CounterState(0, "")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.third_activity)

        if (savedInstanceState != null){
            state = savedInstanceState.getParcelable<CounterState>(KEY_COUNTER)?: error("Unexpected state")
            updateCurrentTExt()
        }

        firstButton.setOnClickListener {
            state = state.decrement()
            updateCurrentTExt()
        }

        secondButton.setOnClickListener {
            state = state.increment()
            updateCurrentTExt()
        }
        updateCurrentTExt()

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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(KEY_COUNTER, state)
        DebugLogger.d(tag, "onSaveInstanceState ${hashCode()}, ${outState}")
        updateCurrentTExt()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
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

    private fun updateCurrentTExt() {
        counter.text = state.count.toString()
        DebugLogger.d(tag, "updateCurrentTExt ${hashCode()}")
    }

    companion object {
        private const val KEY_COUNTER = "counter"
    }
}

