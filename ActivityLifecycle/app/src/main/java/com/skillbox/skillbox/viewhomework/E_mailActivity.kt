package com.skillbox.skillbox.viewhomework

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_e_mail.*
import kotlinx.android.synthetic.main.activity_main.*

class E_mailActivity: AppCompatActivity (R.layout.activity_e_mail) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setEmailParamsFromIntent()
    }
    private fun setEmailParamsFromIntent(){
        val addresses = intent.getStringArrayExtra(Intent.EXTRA_EMAIL)
        val subject = intent.getStringExtra(Intent.EXTRA_SUBJECT)

        addressTextView.text = addresses?.joinToString()?: "Email address not set"

        subjectTextView.text = subject?: "subject is not set"
    }
}