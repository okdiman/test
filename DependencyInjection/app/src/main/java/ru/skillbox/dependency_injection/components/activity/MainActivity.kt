package ru.skillbox.dependency_injection.components.activity

import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import ru.skillbox.dependency_injection.R

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main)