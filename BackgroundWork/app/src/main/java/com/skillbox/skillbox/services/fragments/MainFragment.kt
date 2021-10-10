package com.skillbox.skillbox.services.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.os.Environment
import android.util.Patterns
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.work.WorkInfo
import androidx.work.WorkManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.skillbox.skillbox.services.R
import com.skillbox.skillbox.services.databinding.MainFragmentBinding
import com.skillbox.skillbox.services.utils.toast
import com.skillbox.skillbox.services.worker.DownloadWorker
import kotlinx.coroutines.flow.collect

class MainFragment : Fragment(R.layout.main_fragment) {
    //    создаем объекты баиндинга и вью модели
    private val binding: MainFragmentBinding by viewBinding(MainFragmentBinding::bind)
    private val viewModelMain: MainFragmentViewModel by viewModels()

    private var firstRun = true

    //    создаем нуллабельный url, чтобы его можно было использовать в нескольких местах
    private var url: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindStateFlow()
        binding.downloadButton.setOnClickListener {
            download()
        }
        bindWorkManagerState()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        url = null
    }

    private fun download() {
        if (binding.urlEditText.text.toString().isNotEmpty()) {
            if (Patterns.WEB_URL.matcher(binding.urlEditText.text.toString()).matches()) {
                url = binding.urlEditText.text.toString()
                if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
                    firstRun = false
                    viewModelMain.downloadFile(url!!)
                } else {
                    toast(R.string.storage_is_not_available)
                }
            } else {
                toast(R.string.incorrect_url)
            }
        } else {
            toast(R.string.u_did_not_enter_url)
        }

    }

    private fun bindStateFlow() {
        lifecycleScope.launchWhenResumed {
            if (!firstRun){
                viewModelMain.isErrorStateFlow.collect { toast(it) }
            }
        }
    }

    private fun isLoading(loading: Boolean) {
        if (loading) {
            binding.run {
                urlEditText.isEnabled = false
                downloadButton.isEnabled = false
            }
        } else {
            binding.run {
                urlEditText.isEnabled = true
                downloadButton.isEnabled = true
            }
        }
    }

    private fun bindWorkManagerState() {
        WorkManager.getInstance(requireContext())
            .getWorkInfosForUniqueWorkLiveData(DownloadWorker.UNIQUE_DOWNLOADING)
            .observe(viewLifecycleOwner) { handleWorkInfo(it.first()) }
    }

    private fun handleWorkInfo(workInfo: WorkInfo) {
        binding.waitingTextView.isVisible = workInfo.state == WorkInfo.State.ENQUEUED
        binding.downloadProgressBar.isVisible = workInfo.state == WorkInfo.State.RUNNING
        if (workInfo.state == WorkInfo.State.SUCCEEDED && !firstRun) {
            toast(R.string.succeeded_downloading)
        }
        if (workInfo.state == WorkInfo.State.FAILED || workInfo.state == WorkInfo.State.CANCELLED
            || workInfo.state == WorkInfo.State.BLOCKED
        ) {
            AlertDialog.Builder(requireContext())
                .setTitle(R.string.error)
                .setMessage(R.string.download_error)
                .setNegativeButton("Cancel") { _, _ -> }
                .setPositiveButton("Retry") { _, _ -> viewModelMain.downloadFile(url!!) }
                .show()
        }
        isLoading(!workInfo.state.isFinished)
    }
}