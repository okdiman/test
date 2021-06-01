package com.skillbox.skillbox.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//Добавление фрагмента в контейнер мейн активити
        supportFragmentManager.beginTransaction()
            .add(R.id.mainConteiner, ListFragment())
            .commit()
    }
}