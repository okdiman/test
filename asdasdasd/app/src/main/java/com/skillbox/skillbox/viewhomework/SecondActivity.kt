package com.skillbox.skillbox.viewhomework

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.second_activity.*

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.second_activity)

        showFragmentButton.setOnClickListener {
            showInfoFragment()
        }

        replaceInfoButton.setOnClickListener {
            replaceInfoFragment()
        }
    }

    private fun showInfoFragment() {
        val alreadyHasFragment = supportFragmentManager.findFragmentById(R.id.container) != null

        if (!alreadyHasFragment) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, InfoFragment.newInstance(dataInfoText.text.toString()))
                .commit()
        } else {
            toast("Fragment is shown")
        }
    }

    private fun replaceInfoFragment () {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, InfoFragment.newInstance(dataInfoText.text.toString()))
            .commit()
    }

    private fun toast (text:String){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }
}