package com.skillbox.skillbox.testonlineshop.components.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.skillbox.skillbox.testonlineshop.R
import dagger.hilt.android.AndroidEntryPoint
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main){
    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }
}