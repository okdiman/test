package com.skillbox.skillbox.files

import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.skillbox.skillbox.files.databinding.MainFragmentBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

class MainFragment : Fragment() {
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

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
            val filesDir = requireContext().getExternalFilesDir("Folder for downloads files")
            val file = File(filesDir, "Internet File")
            try {
                file.outputStream().use { fileOutputSteram ->
                    Network.api
                        .getFile(binding.fileEditText.text.toString())
                        .byteStream()
                        .use {
                            it.copyTo(fileOutputSteram)
                        }
                }
            } catch (t: Throwable) {
            }
        }
    }
}