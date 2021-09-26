package com.skillbox.skillbox.notifications

import android.content.Intent
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.skillbox.skillbox.notifications.notifications.MessagingService
import com.skillbox.skillbox.notifications.notifications.NotificationChannels

class MainActivity : AppCompatActivity(R.layout.main_activity) {
    override fun onNewIntent(intent: Intent?) {
        Log.i("intent", "$intent")
        super.onNewIntent(intent)
        if ("Reply_action" == intent?.action) {
            val replyText = intent.getCharSequenceExtra(MessagingService.EXTRA_TEXT_REPLY)
            val userId = intent.getIntExtra(MessagingService.EXTRA_USER_ID, 0)
            val repliedNotification =
                NotificationCompat.Builder(this, NotificationChannels.MESSAGE_CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_new)
                    .setContentText("Replied")
                    .build()
            NotificationManagerCompat.from(this)
                .notify(userId, repliedNotification)
        }
    }
}