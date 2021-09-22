package com.skillbox.skillbox.notifications.ui.main

import com.google.firebase.messaging.FirebaseMessaging
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class MainFragmentRepository {

    suspend fun getToken(): String? = suspendCoroutine { continuation ->
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