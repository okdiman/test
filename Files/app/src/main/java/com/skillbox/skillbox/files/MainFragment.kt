package com.skillbox.skillbox.files

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.skillbox.skillbox.files.databinding.MainFragmentBinding

class MainFragment : Fragment() {
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainFragmentViewModel by viewModels()

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
        observe()
    }

    private fun downloadFile() {
        viewModel.downloadFile(binding.fileEditText.toString())


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

    private fun isLoading() {
        binding.fileEditText.isEnabled = false
        binding.filesButton.isEnabled = false
        binding.downloadProgressBar.isVisible = true
    }

    private fun finishLoading() {
        binding.fileEditText.isEnabled = true
        binding.filesButton.isEnabled = true
        binding.downloadProgressBar.isVisible = false
    }

}