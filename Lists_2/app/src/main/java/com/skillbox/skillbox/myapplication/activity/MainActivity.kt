package com.skillbox.skillbox.myapplication.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.skillbox.skillbox.myapplication.fragments.MainFragment
import com.skillbox.skillbox.myapplication.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//  Добавление фрагмента в контейнер мейн активити
        startListFragment()
    }


//  Проверка на наличие фрагмента
    private fun startListFragment() {
        val alreadyHasFragment = supportFragmentManager.findFragmentById(R.id.mainConteiner) != null
        if (!alreadyHasFragment) {
            supportFragmentManager.beginTransaction()
                .add(R.id.mainConteiner, MainFragment())
                .commit()
        }
    }
}