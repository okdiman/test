package com.skillbox.skillbox.contentprovider.main

import android.app.DownloadManager
import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.skillbox.skillbox.contentprovider.R
import com.skillbox.skillbox.contentprovider.databinding.ShareFileFragmentBinding
import com.skillbox.skillbox.contentprovider.toast
import kotlinx.android.synthetic.main.view_toolbar.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ShareFileFragment : Fragment() {
    private var _binding: ShareFileFragmentBinding? = null
    private val binding get() = _binding!!

    //lazy shared prefs для более удобной инициализации
    private val sharedPrefs by lazy {
        requireContext().getSharedPreferences(
            SHARED_PREF,
            Context.MODE_PRIVATE
        )
    }

    //хэндлер для взаимодействия с потоками
    private val mainHandler = Handler(Looper.getMainLooper())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ShareFileFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    //    обнуляем баиндинг и адаптер при закрытии фрагмента
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
    }

    //    инициализаиия тулбара
    private fun initToolbar() {
        toolbar.title = "Sharing of File"
    }

    private fun downloadFileByDownloadManager(url: String) {
//        создаем корутину для работы с внешним хранилищем
        lifecycleScope.launch(Dispatchers.IO) {
//           проверяем доступность внешнего хранилища, если недоступен, то заканчиваем функцию
            if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED) return@launch toast(
                R.string.storage_is_not_available
            )
            try {
//              проверяем sharedPrefs на содержание в нем файла, который пользователь хочет скачать
                if (!sharedPrefs.contains(url)) {
//              задаем адрес директории и имя файла для скачивания с расширением
                    val filesDir =
                        requireContext().getExternalFilesDir(FILES_DIR_NAME)
                    val name = url.substring(
                        url.lastIndexOf('/') + 1,
                        url.length
                    )
//                  создаем объект downloadManager
                    val downloadManager =
                        requireActivity().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
//                  загрузка файла
                    viewModel.downloadFromDownloadManager(
                        url,
                        name,
                        sharedPrefs,
                        filesDir!!,
                        downloadManager,
                        binding.downloadLinearProgressBar
                    )
                } else {
//                переходим на главный потом для выброса тоста ошибки
                    mainHandler.post {
                        toast(R.string.fail_was_download_earlier)
                    }
                }
            } catch (t: Throwable) {
//                переходим на главный потом для выброса тоста ошибки
                mainHandler.post {
                    toast(R.string.something_wrong)
                }
            }

        }
    }

    companion object {
        const val FILES_DIR_NAME = "Folder for downloads files"
        const val SHARED_PREF = "Shared preferences"
    }

}