package com.skillbox.skillbox.notifications.notifications


import android.app.PendingIntent
import android.content.Intent
import android.graphics.Color
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.bumptech.glide.Glide
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.skillbox.skillbox.notifications.MainActivity
import com.skillbox.skillbox.notifications.R


class MessagingService : FirebaseMessagingService() {

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.i("message", "$message")
//        в зависимости от поля type в сообщении опрделяем тип сообщения
        when (message.data["type"]) {
            "message" -> {
                val userId = message.data["userId"]?.toLong()
                val user = message.data["user"]
                val messageText = message.data["text"]
                if (user != null && messageText != null && userId != null) {
//                    если данные полные, то показываем уведомление о сообщении
                    showMessageNotification(user, messageText, userId)
                }
            }
            "news" -> {
                val title = message.data["title"]
                val description = message.data["description"]
                val imageURL = message.data["imageURL"]
                if (title != null && description != null) {
//                    если данные полные, то показываем уведомление для новостей
                    showNewsNotification(title, description, imageURL)
                }
            }
        }
    }

    //    вывод уведомлений сообщений
    private fun showMessageNotification(user: String, message: String, userId: Long) {
//    создаем интент для обработки клика на уведомление
        val intent = Intent(this, MainActivity::class.java)
//    оборачиваем пред интент в обертку pending, чтобы его мог использовать др процесс
        val pendingIntent = PendingIntent.getActivity(
            this,
            PENDING_INTENT_REQUEST_CODE, intent, 0
        )
//    создаем интент для обработки клика на кнопку reply
        val replyIntent = Intent(this, MainActivity::class.java)
//                кладем userIdи тип действия
            .putExtra(EXTRA_USER_ID, userId.toInt())
            .setAction(REPLY_ACTION)
//    оборачиваем пред интент в обертку pending, чтобы его мог использовать др процесс
        val replyPendingIntent =
            PendingIntent.getActivity(
                this, REPLY_PENDING_INTENT_REQUEST_CODE,
                replyIntent, 0
            )
//        создаем remoteInput для того, чтобы пользователь мог ввести текст в reply
        val remoteInput = androidx.core.app.RemoteInput.Builder(EXTRA_TEXT_REPLY)
            .setLabel("Type message")
            .build()
//        создаем кнопку действия для reply
        val action = NotificationCompat.Action.Builder(
            android.R.drawable.ic_menu_send,
            "Reply",
            replyPendingIntent
        )
            .addRemoteInput(remoteInput)
            .build()
//        создаем уведомление для сообщений
        val notification = NotificationCompat.Builder(
            this,
            NotificationChannels.MESSAGE_CHANNEL_ID
        )
            .setContentTitle("You have a new message from $user!")
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setSmallIcon(R.drawable.ic_message)
//                параметр для автоматического закрытия по клику
            .setAutoCancel(true)
//                установим отображение красного светодиода при сообщении
            .setLights(Color.RED, 1, 0)
//                обработаем клик на сообщение
            .setContentIntent(pendingIntent)
//                добавим действие в reply
            .addAction(action)
            .build()
//        отображаем уведомление
        NotificationManagerCompat.from(this)
            .notify(userId.toInt(), notification)
    }

//    вывод уведомления о новостях
    private fun showNewsNotification(title: String, description: String, imageUrl: String?) {
//    создаем битмап из пришедшего URL
        val bitmap = Glide.with(this)
            .asBitmap()
            .load(imageUrl)
            .placeholder(R.drawable.ic_cloud_download)
            .error(R.drawable.ic_sync_problem)
            .submit()
            .get()
//    создаем интент для обработки клика по уведомлению
        val intent = Intent(this, MainActivity::class.java)
//    оборачиваем пред интент в обертку pending, чтобы его мог использовать др процесс
        val pendingIntent = PendingIntent.getActivity(
            this,
            PENDING_INTENT_REQUEST_CODE, intent, 0
        )
//    создаем уведомление
        val notification = NotificationCompat.Builder(
            this,
            NotificationChannels.NEWS_CHANNEL_ID
        )
            .setContentTitle(title)
            .setContentText(description)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setSmallIcon(R.drawable.ic_new)
            .setLargeIcon(bitmap)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()
    //        отображаем уведомление
        NotificationManagerCompat.from(this)
            .notify(NEWS_NOTIFICATION_ID, notification)
    }


    companion object {
        const val PENDING_INTENT_REQUEST_CODE = 7777
        const val REPLY_PENDING_INTENT_REQUEST_CODE = 7779
        const val NEWS_NOTIFICATION_ID = 7778
        const val EXTRA_TEXT_REPLY = "reply_text"
        const val EXTRA_USER_ID = "user_id"
        const val REPLY_ACTION = "Reply_action"
    }
}