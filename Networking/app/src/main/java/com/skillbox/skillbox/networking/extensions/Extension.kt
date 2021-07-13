package com.skillbox.skillbox.networking

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import java.util.regex.Pattern

//    создаем исключение для инфлейта вьюшек
fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

val YEAR = Pattern.compile(
    "(\\+[0-9]+[\\- \\.]*)?" // +<digits><sdd>*
            + "(\\([0-9]+\\)[\\- \\.]*)?" // (<digits>)<sdd>*
            + "([0-9][0-9\\- \\.]+[0-9])"
)