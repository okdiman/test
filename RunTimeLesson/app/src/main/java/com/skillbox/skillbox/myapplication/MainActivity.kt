package com.skillbox.skillbox.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startFragment()
    }

    private fun startFragment(){
        this.supportFragmentManager.beginTransaction()
                .add(R.id.mainConteiner, DangerousPermissionFragment())
                .commit()
    }
}