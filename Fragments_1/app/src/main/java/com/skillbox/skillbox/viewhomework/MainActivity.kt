package com.skillbox.skillbox.viewhomework

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.list_fragment.*
import kotlinx.android.synthetic.main.login_fragment.*

class MainActivity : AppCompatActivity(), ItemSelectListener {
    private val tag = "MainActivity"
    private var uncorrectlyState: FormState = FormState(false, "")
    var successLogin = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startLoginFragment()
        setContentView(R.layout.activity_main)
        if (savedInstanceState != null) {
            uncorrectlyState =
                savedInstanceState.getParcelable<FormState>(MainActivity.STATE_KEY)
                    ?: error("Unexpected key")
            successLogin = uncorrectlyState.valid
            if (!successLogin) {
                uncorrectly.isVisible = true
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        uncorrectlyState = FormState(successLogin, "")
        outState.putParcelable(STATE_KEY, uncorrectlyState)
    }

    private fun startLoginFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.container, LoginFragment())
            .commit()
    }

    companion object {
        private var STATE_KEY = "stateKey"
    }

    override fun onItemSelect(text: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, DetailFragment())
            .addToBackStack("backToMainScreen")
            .commit()
    }
}