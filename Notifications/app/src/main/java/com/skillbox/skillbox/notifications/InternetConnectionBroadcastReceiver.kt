package com.skillbox.skillbox.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class InternetConnectionBroadcastReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        if (!context.isConnected){
            Toast.makeText(context, "Internet isn't working, please check your internet connection and try again", Toast.LENGTH_SHORT).show()
        }
    }
}