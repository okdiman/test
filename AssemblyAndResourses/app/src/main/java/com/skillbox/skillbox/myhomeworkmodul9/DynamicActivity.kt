package com.skillbox.skillbox.myhomeworkmodul9

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_dinamic.*
import kotlinx.android.synthetic.main.activity_dinamic.view.*
import kotlinx.android.synthetic.main.item_text.view.*

class DynamicActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dinamic)

//        addButton.setOnClickListener {
//            val textToAdd = textInput.text.toString()
//            val textViewToAdd = TextView(this).apply {
//                text = textToAdd
//                layoutParams = LinearLayout.LayoutParams(
//                    LinearLayout.LayoutParams.WRAP_CONTENT,
//                    LinearLayout.LayoutParams.WRAP_CONTENT
//                ).apply {
//                    gravity = when (Random.nextInt(3)) {
//                        0 -> Gravity.CENTER
//                        1 -> Gravity.END
//                        else -> Gravity.START
//                    }
//                }
//            }
//            container.addView(textViewToAdd)


        addButton.setOnClickListener{
            val textToAdd = textInput.text.toString()
            val view = layoutInflater.inflate(R.layout.item_text, container, false)
            view.apply {
                textView.text = textToAdd
                deleteButton.setOnClickListener{
                    container.removeView(this)
                }
            }
            container.addView(view)
        }

    }

}