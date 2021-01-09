package com.skillbox.skillbox.helloworld

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    //вывод различных строк и инфы на главный экран
//        val textView = findViewById<TextView>(R.id.textView)
//        val count = 2
//        val pluralString = resources.getQuantityString(R.plurals.main_quantity_string, count, count)
//        textView.text = pluralString

// вывод инфы о приложении на главный экран
//        textView.text = """
//           Build Type = ${BuildConfig.BUILD_TYPE}
//           Flavor = ${BuildConfig.FLAVOR}
//           Version Code = ${BuildConfig.VERSION_CODE}
//           Version Name = ${BuildConfig.VERSION_NAME}
//        """
    }
}