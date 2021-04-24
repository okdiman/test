package com.skillbox.skillbox.viewhomework

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.login_fragment.*

class LoginFragment : Fragment(R.layout.login_fragment) {
    private var uncorrectlyState: FormState = FormState(false, "")
    var successLogin = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

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
                    mainConstraint.addView(newProgressBar)
                    newProgressBar.apply {
                        login()
                    }
                    android.os.Handler().postDelayed({
                        mainConstraint.removeView(newProgressBar)
                        (activity as MainActivity).supportFragmentManager.beginTransaction()
                            .replace(R.id.container, MainFragment())
                            .commit()
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
            Toast.makeText(activity, "Вы успешно вошли!", Toast.LENGTH_SHORT).show()
        }, 2500)
    }
}
