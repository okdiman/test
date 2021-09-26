package com.skillbox.skillbox.notifications.fragments

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.skillbox.skillbox.notifications.R
import com.skillbox.skillbox.notifications.databinding.MainFragmentBinding
import com.skillbox.skillbox.notifications.utils.isConnected
import com.skillbox.skillbox.notifications.notifications.NotificationChannels
import com.skillbox.skillbox.notifications.receiver.InternetConnectionBroadcastReceiver
import com.skillbox.skillbox.notifications.utils.toast
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainFragment : Fragment(R.layout.main_fragment) {
    //    создаем объекты баиндинга, вью модели и бродкаст ресивера
    private val binding: MainFragmentBinding by viewBinding(MainFragmentBinding::bind)
    private val viewModel: MainViewModel by viewModels()
    private val receiver = InternetConnectionBroadcastReceiver()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        закрываем все уведомления при открытии фрагмента
        NotificationManagerCompat.from(requireContext())
            .cancelAll()
//        получаем токен при запуске фрагмента
        viewModel.getToken()
//        подписка на вью модель
        bindViewModel()
//        обработка кнопки синхронизации
        binding.synchronizeButton.setOnClickListener {
//            если интернет есть, то симулируем синхронизацию
            if (requireContext().isConnected) {
                showProgressNotification()
            } else {
//                если интернета нет, то показываем тост
                Toast.makeText(
                    context,
                    "Internet isn't working, please check your internet connection and try again",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
//        регистрируем бродкаст ресивер
        requireContext().registerReceiver(
            receiver,
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )
    }

    override fun onPause() {
        super.onPause()
//        дерегистрируем бродкаст ресивер
        requireContext().unregisterReceiver(receiver)
    }

    private fun bindViewModel() {
//        выводим полученный токен в лог
        viewModel.token.observe(viewLifecycleOwner) { gotToken ->
            Log.i("token", "$gotToken")
        }
//        выводим тост в случае ошибки
        viewModel.isError.observe(viewLifecycleOwner) { error ->
            toast(error)
        }
    }

    //    симуляция синхронизации
    private fun showProgressNotification() {
//    делаем кнопку неактивной
        binding.synchronizeButton.isEnabled = false
//    создаем билдер для уведомлений
        val notificationBuilder = NotificationCompat.Builder(
            requireContext(),
            NotificationChannels.NEWS_CHANNEL_ID
        )
            .setContentTitle("Update downloading")
            .setContentText("Download in progress")
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setSmallIcon(android.R.drawable.stat_sys_download)
//    указываем число стадий прогресса
        val maxProgress = 20
//    используем корутину здесь только из-за delay
        lifecycleScope.launch {
//            для каждого состояния прогресса обновляем уведомление
            (0 until maxProgress).forEach { progress ->
                val notification = notificationBuilder
                    .setProgress(maxProgress, progress, false)
                    .build()
//                показываем уведомление
                NotificationManagerCompat.from(requireContext())
                    .notify(PROGRESS_NOTIFICATION_ID, notification)
                delay(500)
            }
//            создаем финальное уведомление о завершении загрузки
            val finalNotification = notificationBuilder
                .setSmallIcon(R.drawable.ic_notification_important)
                .setContentText("Download is completed")
                .setProgress(0, 0, false)
                .build()
//            выводим уведомление
            NotificationManagerCompat.from(requireContext())
                .notify(PROGRESS_NOTIFICATION_ID, finalNotification)
//            закрываем через секунду
            delay(1000)
//            закрываем уведомление
            NotificationManagerCompat.from(requireContext())
                .cancel(PROGRESS_NOTIFICATION_ID)
//            делаем кнопку активной
            binding.synchronizeButton.isEnabled = true
        }
    }

    companion object {
        private const val PROGRESS_NOTIFICATION_ID = 8888
    }
}