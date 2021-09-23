package com.skillbox.skillbox.notifications

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.bumptech.glide.Glide
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.skillbox.skillbox.notifications.ui.main.MainFragment

class MessagingService : FirebaseMessagingService() {

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        val userId = message.data["userId"]?.toLong()
        val user = message.data["user"]
        val messageText = message.data["message"]
        if (user != null && messageText != null && userId != null) {
            showMessageNotification(user, messageText, userId)
        }
        val title = message.data["title"]
        val description = message.data["description"]
        val imageURL = message.data["imageURL"]
        if (title != null && description != null) {
            showNewsNotification(title, description, imageURL)
        }
    }

    private fun showMessageNotification(user: String, message: String, userId: Long) {
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, PENDING_INTENT_REQUEST_CODE, intent, 0)
        val notification = NotificationCompat.Builder(this, NotificationChannels.MESSAGE_CHANNEL_ID)
            .setContentTitle("You have a new message from $user!")
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setSmallIcon(R.drawable.ic_message)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()
        NotificationManagerCompat.from(this)
            .notify(userId.toInt(), notification)
    }

    private fun showNewsNotification(title: String, description: String, imageUrl: String?) {
        val bitmap = Glide.with(this)
            .asBitmap()
            .load(imageUrl)
            .placeholder(R.drawable.ic_cloud_download)
            .error(R.drawable.ic_sync_problem)
            .submit()
            .get()
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, PENDING_INTENT_REQUEST_CODE, intent, 0)
        val notification = NotificationCompat.Builder(this, NotificationChannels.NEWS_CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(description)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setSmallIcon(R.drawable.ic_new)
            .setLargeIcon(bitmap)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()
        NotificationManagerCompat.from(this)
            .notify(NEWS_NOTIFICATION_ID, notification)
    }


    companion object {
        const val PENDING_INTENT_REQUEST_CODE = 7777
        const val NEWS_NOTIFICATION_ID = 7778
    }
}