package com.skillbox.skillbox.viewhomework

import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class IntereationActiviry: AppCompatActivity(R.layout.interact_activity), ItemSeleckListener {
    override fun onItemSelected(text: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, InfoFragment.newInstance(text))
            .commitNow()
    }


}