package com.skillbox.skillbox.myhomeworkmodul9

import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        clearButton.setOnClickListener {
            nameInput.setText("")
            Toast.makeText(this, R.string.cleared_text, Toast.LENGTH_SHORT).show()
        }

        nameInput.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                nameText.text = s?.takeIf { it.isNotBlank() }
                        ?.let { name -> resources.getString(R.string.hello_string, name) }
                clearButton.isEnabled = s?.isNotEmpty() ?: false

            }
        })
        makeLongOperationButton.setOnClickListener {
            makeLongOperation()
        }
//        val textView = findViewById<TextView>(R.id.textView)
//        textView.text = """
//            Flavor = ${BuildConfig.FLAVOR}
//            Build Type = ${BuildConfig.BUILD_TYPE}
//            Version Code = ${BuildConfig.VERSION_CODE}
//            Version Name = ${BuildConfig.VERSION_NAME}
//            Application Id = ${BuildConfig.APPLICATION_ID}
//        """.trimIndent()
    }
    private fun makeLongOperation () {
        longOperationProgress.visibility = View.VISIBLE
        makeLongOperationButton.isEnabled = false

        Handler().postDelayed({
            longOperationProgress.visibility = View.GONE
            makeLongOperationButton.isEnabled = true
            Toast.makeText(this,R.string.long_operation_coplete, Toast.LENGTH_SHORT).show()
        }, 2000)
    }
}