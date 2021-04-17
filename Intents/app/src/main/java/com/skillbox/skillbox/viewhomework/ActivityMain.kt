package com.skillbox.skillbox.viewhomework

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class ActivityMain : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        prepareCall(ActivityResultContracts.Dial()) { dialing ->
            if (dialing) {
                toast("Вызов завершен")
            } else {
              toast("Вызов отменен!")
            }
        }

        phoning.setOnClickListener {
            dialing()
        }
    }

    private fun dialing() {
        val telephoneNumberToDial = telephoneNumber.text.toString()
        val isPhoneValid = Patterns.PHONE.matcher(telephoneNumberToDial).matches()

        if (!isPhoneValid) {
            Toast.makeText(this, "Номер введен некорректно", Toast.LENGTH_SHORT).show()
        } else {
            val dialIntent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:$telephoneNumberToDial")
            }
            if (dialIntent.resolveActivity(packageManager) != null) {
                startActivity(dialIntent)
            } else {
                toast("Номер введен некорректно")
            }
        }
    }
    private fun toast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }
}
