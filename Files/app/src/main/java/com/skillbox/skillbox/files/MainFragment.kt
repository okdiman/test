package com.skillbox.skillbox.files

import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.skillbox.skillbox.files.databinding.MainFragmentBinding
import com.skillbox.skillbox.files.network.Network
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

class MainFragment : Fragment() {
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    private val sharedPrefs by lazy {
        requireContext().getSharedPreferences("SharedPref", Context.MODE_PRIVATE)
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
        binding.filesButton.setOnClickListener {
            downloadFile()
        }
    }

    private fun downloadFile() {
        lifecycleScope.launch(Dispatchers.IO) {
            if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED) return@launch
            isLoading()
            val url = binding.fileEditText.text.toString()
            val filesDir = requireContext().getExternalFilesDir("Folder for downloads files")
            val fileName = "${System.currentTimeMillis()}_name"
            val file = File(filesDir, fileName)


            try {
                file.outputStream().use { fileOutputSteram ->
                    Network.api
                        .getFile(url)
                        .byteStream()
                        .use {
                            it.copyTo(fileOutputSteram)
                        }
                    sharedPrefs.edit()
                        .putString(url, fileName)
                        .apply()
                }
            } catch (t: Throwable) {
                file.delete()
            } finally {
                finishLoading()
            }


            //            val request = DownloadManager.Request(Uri.parse(url))
//                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
//                .setDestinationUri(Uri.fromFile(file))
//                .setTitle(fileName)
//                .setDescription("Downloading")
//                .setRequiresCharging(false)
//                .setAllowedOverMetered(true)
//            val downloadManager =
//                requireActivity().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
//            val downloadID = downloadManager.enqueue(request)
//
//            //проверка завершения
//            var finishLoad = false
//            val progress: Int
//            val loading = binding.downloadProgressBar
//            while (!finishLoad) {
//                val cursor =
//                    downloadManager.query(DownloadManager.Query().setFilterById(downloadID))
//                if (cursor.moveToFirst()) {
//                    when (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))) {
//                        DownloadManager.STATUS_FAILED -> {
//                            finishLoad = true
//                            break
//                        }
//                        DownloadManager.STATUS_PAUSED -> break
//                        DownloadManager.STATUS_PENDING -> break
//                        DownloadManager.STATUS_RUNNING -> {
//                            val total =
//                                cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES))
//                            if (total >= 0) {
//                                val downloaded =
//                                    cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR))
//                                progress = ((downloaded * 100L) / total).toInt()
//                                loading.isVisible = true
//                                loading.progress = progress
//                            }
//                            break
//                        }
//                        DownloadManager.STATUS_SUCCESSFUL -> {
//                            loading.isVisible = false
//                            progress = 100
//                            finishLoad = true
//                            Toast.makeText(
//                                requireContext(),
//                                "Download completed",
//                                Toast.LENGTH_SHORT
//                            ).show()
//                            break
//                        }
//                    }
//                }
//            }
        }
    }

    private fun isLoading(){
        binding.fileEditText.isEnabled = false
        binding.filesButton.isEnabled = false
        binding.downloadProgressBar.isVisible = true
    }

    private fun finishLoading(){
        binding.fileEditText.isEnabled = true
        binding.filesButton.isEnabled = true
        binding.downloadProgressBar.isVisible = false
    }

}