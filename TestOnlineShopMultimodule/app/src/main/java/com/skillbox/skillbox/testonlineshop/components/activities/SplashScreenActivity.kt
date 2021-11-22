package com.skillbox.skillbox.testonlineshop.components.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.skillbox.skillbox.testonlineshop.R

class SplashScreenActivity : AppCompatActivity(R.layout.splash_activity) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}