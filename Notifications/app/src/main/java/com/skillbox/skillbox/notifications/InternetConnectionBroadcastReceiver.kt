package com.skillbox.skillbox.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class InternetConnectionBroadcastReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        if (context.isConnected){
            Toast.makeText(context, "Internet connected", Toast.LENGTH_SHORT).show()
        } else{
            Toast.makeText(context, "Internet disconnected", Toast.LENGTH_SHORT).show()
        }
    }
}