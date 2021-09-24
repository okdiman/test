package com.skillbox.skillbox.notifications.ui.main

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
import com.skillbox.skillbox.notifications.NotificationChannels
import com.skillbox.skillbox.notifications.R
import com.skillbox.skillbox.notifications.databinding.MainFragmentBinding
import com.skillbox.skillbox.notifications.isConnected
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainFragment : Fragment(R.layout.main_fragment) {
    private val binding: MainFragmentBinding by viewBinding(MainFragmentBinding::bind)
    private val viewModel: MainViewModel by viewModels()
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

    private fun bindViewModel() {
        viewModel.token.observe(viewLifecycleOwner) { gotToken ->
            Log.i("token", "$gotToken")
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
            .setSmallIcon(R.drawable.ic_notification_important)
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