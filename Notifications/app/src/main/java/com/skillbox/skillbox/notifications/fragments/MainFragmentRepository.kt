package com.skillbox.skillbox.notifications.fragments

import com.google.firebase.messaging.FirebaseMessaging
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class MainFragmentRepository {
    //    получение токена
    suspend fun getToken(): String? = suspendCoroutine { continuation ->
//    получаем токен с FirebaseMessaging
        FirebaseMessaging.getInstance().token
            .addOnSuccessListener { token ->
                continuation.resume(token)
            }
            .addOnCanceledListener {
                continuation.resume(null)
            }
            .addOnFailureListener {
                continuation.resume(null)
            }
    }
}