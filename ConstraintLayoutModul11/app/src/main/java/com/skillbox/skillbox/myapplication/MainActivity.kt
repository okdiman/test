package com.skillbox.skillbox.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toggleLastName.setOnClickListener {
            userInfoGroup.isVisible = !userInfoGroup.isVisible
        }

        userInfoGroup.referencedIds.forEach { id->
            findViewById<View>(id).setOnClickListener {
                Toast.makeText(this, "$id text for click", Toast.LENGTH_SHORT).show()
            }
        }
    }
}