package com.skillbox.skillbox.viewhomework

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.activity.prepareCall
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val tag = "MainActivity"
    private var uncorrectlyState: FormState = FormState(false, "")
    var successLogin = false
    val PHOTO_REQUEST_CODE = 123
    private val cameraLauncher = prepareCall(ActivityResultContracts.TakePicture()){
        it ?: toast("photo capture was cancelled")
        resultPhotoImageView.setImageBitmap(it)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
            if (successLogin) {
                uncorrectly.isVisible = false
                Log.d(tag, "CheckIn $successLogin")
                mainConstraint.addView(newProgressBar)
                newProgressBar.apply {
                    login()
                }
                android.os.Handler().postDelayed({
                    mainConstraint.removeView(newProgressBar)
                }, 2500)
            } else {
                uncorrectly.isVisible = true
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

        startExplicitButton.setOnClickListener {
            val messageText = e_mail.text.toString()
            startActivity(SecondActivity.getIntent(this, messageText))
        }

        sendEmailButton.setOnClickListener {
            val emailAddressString = e_mail.text.toString()
            val emailSubject = subjectEditText.text.toString()

            val isEmailValid: Boolean = Patterns.EMAIL_ADDRESS.matcher(emailAddressString).matches()

            if (!isEmailValid) {
                toast("Enter valid E-mail")
            } else {
                val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("mailto:")
                    putExtra(Intent.EXTRA_EMAIL, arrayOf(emailAddressString))
                    putExtra(Intent.EXTRA_SUBJECT, emailSubject)
                }
                if (emailIntent.resolveActivity(packageManager) != null) {
                    startActivity(emailIntent)
                } else {
                    toast("no component to handle intent")
                }
            }
        }

        takePhotoButton.setOnClickListener {
            dispatchTakePictureIntent()
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

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        if (requestCode == PHOTO_REQUEST_CODE) {
//            if (resultCode == Activity.RESULT_OK) {
//                val previewBitmap = data?.getParcelableExtra("data") as? Bitmap
//                resultPhotoImageView.setImageBitmap(previewBitmap)
//            } else {
//                toast("photo capture was cancelled")
//            }
//        } else {
//            super.onActivityResult(requestCode, resultCode, data)
//        }
//    }

    private fun dispatchTakePictureIntent() {

        val isCameraPermissionGranted = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
        if (!isCameraPermissionGranted) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 4123)
        } else {
            cameraLauncher.launch(null)
//            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//            cameraIntent.resolveActivity(packageManager)?.also {
//                startActivityForResult(cameraIntent, PHOTO_REQUEST_CODE)
            }
        }

    private fun toast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
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