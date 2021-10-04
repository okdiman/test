package com.skillbox.skillbox.flow.utils

import android.content.Context
import android.net.ConnectivityManager
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

fun <T : Fragment> T.toast(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}

// расширение для проверки доступа к сети
val Context.isConnected: Boolean
    get() {
        return (getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
            .activeNetworkInfo?.isConnected == true
    }

@ExperimentalCoroutinesApi
fun EditText.textChangesFlow(): kotlinx.coroutines.flow.Flow<String> {
    return callbackFlow<String> {
        val textChangeListener = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                trySendBlocking(s?.toString().orEmpty())
            }

            override fun afterTextChanged(s: Editable?) {}
        }
        this@textChangesFlow.addTextChangedListener(textChangeListener)
        awaitClose {
            this@textChangesFlow.removeTextChangedListener(textChangeListener)
        }
    }
}

@ExperimentalCoroutinesApi
fun RadioGroup.elementChangeFlow(): Flow<Int> {
    return callbackFlow {
        val checkedChangeListener = RadioGroup.OnCheckedChangeListener { _, checkedId ->
            findViewById<RadioButton>(checkedId).apply {
                when(this.text.toString()){
                    "Movie" -> trySendBlocking(0)
                    "Series" -> trySendBlocking(1)
                    "Episode" -> trySendBlocking(2)
                }
            }
        }
        setOnCheckedChangeListener(checkedChangeListener)
        awaitClose {
            setOnCheckedChangeListener(null)
        }
    }

}

