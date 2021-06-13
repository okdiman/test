package com.skillbox.skillbox.location

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startMainFragment()
    }

    private fun startMainFragment() {
        val alreadyHasFragment = supportFragmentManager.findFragmentById(R.id.mainContainer) != null
        if (!alreadyHasFragment) {
            this.supportFragmentManager.beginTransaction()
                .replace(R.id.mainContainer, MainFragment())
                .commit()
        }
    }
}