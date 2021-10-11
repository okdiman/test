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
import com.skillbox.skillbox.services.workers.DownloadWorker
import kotlinx.coroutines.flow.collect

class MainFragment : Fragment(R.layout.main_fragment) {
    //    создаем переменные баиндинга и вью модели
    private val binding: MainFragmentBinding by viewBinding(MainFragmentBinding::bind)
    private val viewModelMain: MainFragmentViewModel by viewModels()

    //    флаг нового запуска приложения(нужен, чтобы не показывать статус succeeded
//    при подписке на на нашу задачу в workManger при запуске приложения)
    private var newRun = true

    //    создаем нуллабельный url, чтобы его можно было использовать в нескольких местах
    private var url: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        подписка на обновления флоу
        bindStateFlow()
        binding.downloadButton.setOnClickListener {
            download()
        }
//        подписка на обновления WorkManager.state
        bindWorkManagerState()
//        запускаем периодическую задачу
        viewModelMain.startPeriodicWork()
    }

    override fun onDestroyView() {
        super.onDestroyView()
//        зануляме url при закрытии экрана
        url = null
    }

    private fun download() {
//        порверяем заполненность поля ввода
        if (binding.urlEditText.text.toString().isNotEmpty()) {
//            проверяем соответствие введенного значения с URL
            if (Patterns.WEB_URL.matcher(binding.urlEditText.text.toString()).matches()) {
//                присваиваем введенное значение переменной url для дальнейшего переиспользования
                url = binding.urlEditText.text.toString()
//                проверяем доступность внешнего хранилища
                if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
//                    изменяем флаг первого запуска
                    newRun = false
//                    скачиваем файл
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

    //    подписка на изменения статуса флоу ошибки
    private fun bindStateFlow() {
        lifecycleScope.launchWhenResumed {
            if (!newRun) {
                viewModelMain.isErrorStateFlow.collect { toast(it) }
            }
        }
    }

    //    статус экрана при загрузке
    private fun isLoading(loading: Boolean) {
        if (loading) {
            binding.run {
                cancelDownloadButton.isVisible = true
                cancelDownloadButton.setOnClickListener {
                    WorkManager.getInstance(requireContext())
                        .cancelUniqueWork(DownloadWorker.UNIQUE_DOWNLOADING)
                }
                urlEditText.isEnabled = false
                downloadButton.isEnabled = false
            }
        } else {
            binding.run {
                cancelDownloadButton.isVisible = false
                urlEditText.isEnabled = true
                downloadButton.isEnabled = true
            }
        }
    }

    //    подписка на изменения статуса нашей задачи в workManager
    private fun bindWorkManagerState() {
        WorkManager.getInstance(requireContext())
//                получаем workInfo нашей задачи по ID
            .getWorkInfosForUniqueWorkLiveData(DownloadWorker.UNIQUE_DOWNLOADING)
//                подписываемся на изменения workInfo
            .observe(viewLifecycleOwner) { handleWorkInfo(it.first()) }
    }

    //    действия при изменении workInfo нашей задачи
    private fun handleWorkInfo(workInfo: WorkInfo) {
//        при добавлении задачи в очередь выдаем соотсветствующий текст
        binding.waitingTextView.isVisible = workInfo.state == WorkInfo.State.ENQUEUED
//        при загрузке файла показываем прогресс бар
        binding.downloadProgressBar.isVisible = workInfo.state == WorkInfo.State.RUNNING
//        проверяем флаг нового запуска приложения
        if (!newRun) {
//        если файл успешно скачан, то выдаем тост
            if (workInfo.state == WorkInfo.State.SUCCEEDED) {
                toast(R.string.succeeded_downloading)
            }
//        если файл по какой-либо причине скачан не был, то показываем ошибку
//        с возможностью перезапустить задачу
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
        }
        //        передаем функции состояния экрана значение окончания загрузки
        isLoading(!workInfo.state.isFinished)
    }
}