package com.skillbox.skillbox.notifications

import android.content.Intent
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.skillbox.skillbox.notifications.notifications.MessagingService
import com.skillbox.skillbox.notifications.notifications.NotificationChannels

class MainActivity : AppCompatActivity(R.layout.main_activity) {
    //    описываем действия при получении нового интента
    override fun onNewIntent(intent: Intent?) {
        Log.i("intent", "$intent")
        super.onNewIntent(intent)
//        проверяем пришедший интнет на наш reply интент
        if (MessagingService.REPLY_ACTION == intent?.action) {
//            считываем напечатанный нами текст
            val replyText = intent.getCharSequenceExtra(MessagingService.EXTRA_TEXT_REPLY)
            Log.i("reply_text", "$replyText")
//            считываем userId отправителя
            val userId = intent.getIntExtra(MessagingService.EXTRA_USER_ID, 0)
//            выдаем оповещение, что ответ отправлен
            val repliedNotification =
                NotificationCompat.Builder(this, NotificationChannels.MESSAGE_CHANNEL_ID)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setSmallIcon(R.drawable.ic_new)
                    .setContentText("Replied")
                    .build()
            NotificationManagerCompat.from(this)
                .notify(userId, repliedNotification)
        }
    }
}