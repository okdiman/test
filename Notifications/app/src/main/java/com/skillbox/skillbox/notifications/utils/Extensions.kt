package com.skillbox.skillbox.notifications.utils

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import android.widget.Toast
import androidx.fragment.app.Fragment

fun <T : Fragment> T.toast(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}

//   расширение для проверки версии android у пользователя
fun haveO(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
}

// расширение для проверки доступа к сети
val Context.isConnected: Boolean
    get() {
        return (getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
            .activeNetworkInfo?.isConnected == true
    }

