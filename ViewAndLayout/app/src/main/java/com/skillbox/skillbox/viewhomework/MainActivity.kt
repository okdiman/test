package com.skillbox.skillbox.viewhomework

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var e_mailIsNotEmpty = false
        var passwordIsNotEmpty = false
        agreement.isChecked

        fun activateButton() {
            if (e_mailIsNotEmpty && passwordIsNotEmpty && agreement.isChecked) {
                entrance.isEnabled = true
            }
        }

        agreement.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                entrance.isEnabled = true
                activateButton()
            } else{
                entrance.isEnabled = false
            }
        }


        e_mail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                e_mailIsNotEmpty = e_mail.text.isNotEmpty()?:false
                if (e_mailIsNotEmpty) {
                    activateButton()
                } else{
                    entrance.isEnabled = false
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        password_e_mail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                passwordIsNotEmpty = password_e_mail.text.isNotEmpty()?:false
                if (passwordIsNotEmpty) {
                    activateButton()
                }else{
                        entrance.isEnabled = false
                    }
                }



            override fun afterTextChanged(s: Editable?) {}
        })


        val newProgressBar = layoutInflater.inflate(R.layout.loader, main_layout, false)
        entrance.setOnClickListener {
            main_layout.addView(newProgressBar)
            newProgressBar.apply {
                login()
            }
            android.os.Handler().postDelayed({
                main_layout.removeView(newProgressBar)
            }, 2500)
        }
    }

    private fun login() {
        entrance.isEnabled = false
        e_mail.isEnabled = false
        password_e_mail.isEnabled = false
        agreement.isEnabled = false

        android.os.Handler().postDelayed({
            entrance.isEnabled = true
            e_mail.isEnabled = true
            password_e_mail.isEnabled = true
            agreement.isEnabled = true
            Toast.makeText(this, "Вы успешно вошли!", Toast.LENGTH_SHORT).show()
        }, 2500)
    }
}