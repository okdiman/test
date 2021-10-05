package com.skillbox.skillbox.flow.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.skillbox.skillbox.flow.R
import com.skillbox.skillbox.flow.utils.isConnected

class InternetConnectionBroadcastReceiver : BroadcastReceiver() {
    var wasConnected = true
    override fun onReceive(context: Context, intent: Intent?) {
        if (!context.isConnected) {
            wasConnected = false
            Toast.makeText(context, R.string.internet_is_not_available, Toast.LENGTH_SHORT).show()
        } else {
            if (!wasConnected) {
                Toast.makeText(context, R.string.internet_is_available, Toast.LENGTH_SHORT).show()
            }
        }
    }
}