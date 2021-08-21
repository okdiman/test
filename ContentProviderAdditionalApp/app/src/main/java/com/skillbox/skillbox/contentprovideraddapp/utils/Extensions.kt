package com.skillbox.skillbox.contentprovider

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import java.util.regex.Pattern

//    создаем исключение для инфлейта вьюшек
fun ViewGroup.inflate (@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

fun <T: Fragment> T.toast(@StringRes message: Int) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}
//    создаем паттерн для телефонного номера
val phonePattern = Pattern.compile("^\\+?[0-9]{3}-?[0-9]{6,12}\$")!!