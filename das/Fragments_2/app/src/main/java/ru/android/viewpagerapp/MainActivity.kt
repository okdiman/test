package ru.android.viewpagerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import ru.android.viewpagerapp.databinding.ActivityMainBinding
import ru.android.viewpagerapp.fragments.ViewPagerFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        if (savedInstanceState == null) {
            Log.d("LOG", "Add fragment to activity")
            supportFragmentManager.beginTransaction()
                .add(R.id.container, ViewPagerFragment())
                .commit()
        }
    }
}