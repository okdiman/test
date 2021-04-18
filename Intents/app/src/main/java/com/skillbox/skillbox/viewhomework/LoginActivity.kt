package com.skillbox.skillbox.viewhomework

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private val tag = "LoginActivity"
    private var uncorrectlyState: FormState = FormState(false, "")
    var successLogin = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if (savedInstanceState != null) {
            uncorrectlyState =
                savedInstanceState.getParcelable<FormState>(STATE_KEY) ?: error("Unexpected key")
            successLogin = uncorrectlyState.valid
            Log.d(tag, "successLogin is $successLogin")
            Log.d(tag, "Bundle is $savedInstanceState")
            if (!successLogin) {
                uncorrectly.isVisible = true
            }
        }

        Log.v(tag, "onCreated ${hashCode()}")
        var e_mailIsNotEmpty = false
        var passwordIsNotEmpty = false
        var agreementCompleted = false
        val newProgressBar = layoutInflater.inflate(R.layout.loader, mainConstraint, false)

        fun assessment() {
            successLogin = e_mailIsNotEmpty && passwordIsNotEmpty && agreementCompleted
        }

        fun checkIn() {
            val emailAdressString = e_mail.text
            val isEmailValid = Patterns.EMAIL_ADDRESS.matcher(emailAdressString).matches()
            if (successLogin) {
                if (isEmailValid) {
                    uncorrectly.isVisible = false
                    Log.d(tag, "CheckIn $successLogin")
                    mainConstraint.addView(newProgressBar)
                    newProgressBar.apply {
                        login()
                    }
                    android.os.Handler().postDelayed({
                        val mainActivityClass = ActivityMain::class.java
                        val mainActivityIntent = Intent(
                            this,
                            mainActivityClass
                        )
                        startActivity(mainActivityIntent)
                        mainConstraint.removeView(newProgressBar)
                    }, 2500)

                } else {
                    uncorrectly.isVisible = true
                    uncorrectly.text = "E-mail введен некорректно"
                }
            } else {
                uncorrectly.isVisible = true
                uncorrectly.text = "Ошибка! Некорректно заполнена форма входа"
            }
        }

        agreement.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                agreementCompleted = true
                assessment()
            } else {
                successLogin = false
            }
        }

        e_mail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                e_mailIsNotEmpty = e_mail.text.isNotEmpty() ?: false
                if (e_mailIsNotEmpty) {
                    assessment()
                } else {
                    successLogin = false
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        password_e_mail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                passwordIsNotEmpty = password_e_mail.text.isNotEmpty() ?: false
                if (passwordIsNotEmpty) {
                    assessment()
                } else {
                    successLogin = false
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        entrance.setOnClickListener {
            checkIn()
        }

        ANR.setOnClickListener {
            Thread.sleep(8000)
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(tag, "onStarted ${hashCode()}")
    }

    override fun onResume() {
        super.onResume()
        Log.i(tag, "onResumed ${hashCode()}")
    }

    override fun onTopResumedActivityChanged(isTopResumedActivity: Boolean) {
        super.onTopResumedActivityChanged(isTopResumedActivity)
        Log.e(tag, "onTopResumedActivityChanged ${hashCode()}")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        uncorrectlyState = FormState(successLogin, "")
        outState.putParcelable(STATE_KEY, uncorrectlyState)
    }

    override fun onPause() {
        super.onPause()
        Log.e(tag, "onPaused ${hashCode()}")
    }

    override fun onStop() {
        super.onStop()
        Log.v(tag, "onStopped ${hashCode()}")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(tag, "onDestroyed ${hashCode()}")
    }

    private fun login() {
        entrance.isEnabled = false
        entrance.text = "Введен логин и пароль"
        e_mail.isEnabled = false
        password_e_mail.isEnabled = false
        agreement.isEnabled = false

        android.os.Handler().postDelayed({
            entrance.isEnabled = true
            entrance.text = "Вход"
            e_mail.isEnabled = true
            password_e_mail.isEnabled = true
            agreement.isEnabled = true
            Toast.makeText(this, "Вы успешно вошли!", Toast.LENGTH_SHORT).show()
        }, 2500)

    }

    companion object {
        private var STATE_KEY = "stateKey"
    }
}