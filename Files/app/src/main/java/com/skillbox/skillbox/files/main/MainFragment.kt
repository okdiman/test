package com.skillbox.skillbox.files.main

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.skillbox.skillbox.files.databinding.MainFragmentBinding
import java.io.File

class MainFragment : Fragment() {
    //баиндинг
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    //создание viewModel
    private val viewModel: MainFragmentViewModel by viewModels()

    //лэйтинит url для дальнейшего использования в нескольких местах
    private lateinit var url: String

    //lazy shared prefs для более удобной инициализации
    private val sharedPrefs by lazy {
        requireContext().getSharedPreferences(
            MainFragmentRepository.SHARED_PREF,
            Context.MODE_PRIVATE
        )
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//      проверяем первый ли запуск приложения
        if (sharedPrefs.getBoolean(MainFragmentRepository.FIRST_RUN, true)) {
//            если первый, то выполняем специальный блок кода для этого и изменяем флаг
            firstRunDownload()
            sharedPrefs.edit()
                .putBoolean(MainFragmentRepository.FIRST_RUN, false)
                .apply()
        }
        binding.filesButton.setOnClickListener {
//            инициализируем url для клика
            url = binding.fileEditText.text.toString()
            downloadFileByNetwork(url)
//            downloadFileByDownloadManager()
        }
//        подписка на обновления LiveData
        observe()
    }

    //    загрузка файлов через Network
    private fun downloadFileByNetwork(url: String) {
//    проверяем доступность внешнего хранилища, если недоступен, то заканчиваем функцию
        if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED) return Toast.makeText(
            requireContext(),
            "Storage isn't available, sorry, request was interrupted",
            Toast.LENGTH_SHORT
        ).show()
//        задаем адрес директории и имя файла для скачивания с расширением
        val filesDir = requireContext().getExternalFilesDir(MainFragmentRepository.FILES_DIR_NAME)
        val name = url.substring(url.lastIndexOf('/') + 1, url.length)
//        проверяем sharedPrefs на содержание в нем файла, который пользователь хочет скачать
        if (!sharedPrefs.contains(url)) {
            viewModel.downloadFile(url, name, sharedPrefs, filesDir!!)
        } else {
            Toast.makeText(requireContext(), "File was downloaded earlier", Toast.LENGTH_SHORT)
                .show()
        }
    }

    //    загрузка файлов из assets при первом запуске
    private fun firstRunDownload() {
//    открытие файла из assets
        resources.assets.open("file_for_first_run_download.txt")
//                чтение
            .bufferedReader()
//                запись
            .use {
                it.readText()
            }
//                выполнение загрузки для каждой ссылки
            .split(",").toTypedArray().forEach { firstRunDownloads ->
                downloadFileByNetwork(firstRunDownloads)
            }
    }

    private fun downloadFileByDownloadManager() {
        if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED) return
        val filesDir = requireContext().getExternalFilesDir(MainFragmentRepository.FILES_DIR_NAME)
        val url = binding.fileEditText.text.toString()
        val name = url.substring(
            url.lastIndexOf('/') + 1,
            url.length
        )
        val fileName = "${System.currentTimeMillis()}_$name"
        val file = File(filesDir, fileName)
        val request = DownloadManager.Request(Uri.parse(url))
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
            .setDestinationUri(Uri.fromFile(file))
            .setTitle(fileName)
            .setDescription("Downloading")
            .setRequiresCharging(false)
            .setAllowedOverMetered(true)
        val downloadManager =
            requireActivity().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val downloadID = downloadManager.enqueue(request)

        //проверка завершения
        var finishLoad = false
        var progress: Int
        val loading = binding.downloadProgressBar
        while (!finishLoad) {
            val cursor =
                downloadManager.query(DownloadManager.Query().setFilterById(downloadID))
            sharedPrefs.edit()
                .putString(url, fileName)
                .apply()
            if (cursor.moveToFirst()) {
                Log.i("cursor", "${cursor.moveToFirst()}")
                Log.i("cursor", "${cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)}")
                Log.i(
                    "cursor",
                    "${cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR)}"
                )
                Log.i("cursor", "${cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES)}")
                when (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))) {
                    DownloadManager.STATUS_FAILED -> {
                        finishLoad = true
                    }
                    DownloadManager.STATUS_PAUSED -> break
                    DownloadManager.STATUS_PENDING -> break
                    DownloadManager.STATUS_RUNNING -> {
                        val total =
                            cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES))
                        if (total >= 0) {
                            val downloaded =
                                cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR))
                            progress = ((downloaded * 100L) / total).toInt()
                            loading.isVisible = true
                            loading.progress = progress
                        }
                    }
                    DownloadManager.STATUS_SUCCESSFUL -> {
                        loading.isVisible = false
                        progress = 100
                        Toast.makeText(
                            requireContext(),
                            "Download completed",
                            Toast.LENGTH_SHORT
                        ).show()
                        finishLoad = true
                    }
                }
            }
        }
    }

//    подписка на обновления LiveData
    private fun observe() {
        viewModel.download.observe(viewLifecycleOwner) { download ->
            if (download) {
                isLoading()
            } else {
                finishLoading()
            }
        }
        viewModel.isError.observe(viewLifecycleOwner) { error ->
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        }
        viewModel.isFinished.observe(viewLifecycleOwner) { finishString ->
            Toast.makeText(requireContext(), finishString, Toast.LENGTH_SHORT).show()
        }
    }

//    статус Вьюшек при загрузке файлов
    private fun isLoading() {
        binding.fileEditText.isEnabled = false
        binding.filesButton.isEnabled = false
        binding.downloadProgressBar.isVisible = true
    }

//    обычный статус Вьюшек
    private fun finishLoading() {
        binding.fileEditText.isEnabled = true
        binding.filesButton.isEnabled = true
        binding.downloadProgressBar.isVisible = false
    }

}