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
import com.skillbox.skillbox.notifications.isConnected
import com.skillbox.skillbox.notifications.notifications.NotificationChannels
import com.skillbox.skillbox.notifications.receiver.InternetConnectionBroadcastReceiver
import com.skillbox.skillbox.notifications.toast
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainFragment : Fragment(R.layout.main_fragment) {
    private val binding: MainFragmentBinding by viewBinding(MainFragmentBinding::bind)
    private val viewModel: MainViewModel by viewModels()
    private val receiver = InternetConnectionBroadcastReceiver()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        NotificationManagerCompat.from(requireContext())
            .cancelAll()
        viewModel.getToken()
        bindViewModel()
        binding.synchronizeButton.setOnClickListener {
            if (requireContext().isConnected) {
                showProgressNotification()
            } else {
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
        requireContext().registerReceiver(
            receiver,
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )
    }

    override fun onPause() {
        super.onPause()
        requireContext().unregisterReceiver(receiver)
    }

    private fun bindViewModel() {
        viewModel.token.observe(viewLifecycleOwner) { gotToken ->
            Log.i("token", "$gotToken")
        }
        viewModel.isError.observe(viewLifecycleOwner) { error ->
            toast(error)
        }
    }

    private fun showProgressNotification() {
        binding.synchronizeButton.isEnabled = false
        val notificationBuilder = NotificationCompat.Builder(
            requireContext(),
            NotificationChannels.NEWS_CHANNEL_ID
        )
            .setContentTitle("Update downloading")
            .setContentText("Download in progress")
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setSmallIcon(android.R.drawable.stat_sys_download)
        val maxProgress = 20
        lifecycleScope.launch {
            (0 until maxProgress).forEach { progress ->
                val notification = notificationBuilder
                    .setProgress(maxProgress, progress, false)
                    .build()

                NotificationManagerCompat.from(requireContext())
                    .notify(PROGRESS_NOTIFICATION_ID, notification)
                delay(500)
            }

            val finalNotification = notificationBuilder
                .setSmallIcon(R.drawable.ic_notification_important)
                .setContentText("Download is completed")
                .setProgress(0, 0, false)
                .build()

            NotificationManagerCompat.from(requireContext())
                .notify(PROGRESS_NOTIFICATION_ID, finalNotification)
            delay(1000)

            NotificationManagerCompat.from(requireContext())
                .cancel(PROGRESS_NOTIFICATION_ID)
            binding.synchronizeButton.isEnabled = true
        }
    }

    companion object {
        private const val PROGRESS_NOTIFICATION_ID = 8888
    }
}