package com.skillbox.skillbox.files.main

import android.app.DownloadManager
import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.datastore.dataStoreFile
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.skillbox.skillbox.files.R
import com.skillbox.skillbox.files.databinding.MainFragmentBinding
import com.skillbox.skillbox.files.toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainFragment : Fragment() {
    //баиндинг
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    //создание viewModel
    private val viewModel: MainFragmentViewModel by viewModels()

    //viewModel для dataStore
    private val dataStoreViewModel: DataStoreViewModel by viewModels()

    //хэндлер для взаимодействия с потоками
    private val mainHandler = Handler(Looper.getMainLooper())

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
//            если первый, то выполняем специальный блок кода
            firstRunDownload()
        }
//        кнопка загрузки файла
        binding.filesButton.setOnClickListener {
//            проверяем заполненность поля ссылки
            if (binding.fileEditText.text.toString().isNotEmpty()) {
//              инициализируем url для клика
                url = binding.fileEditText.text.toString()
//                проверяем соотвествие введенной ссылки с типом Url
                val isUrlValid = Patterns.WEB_URL.matcher(url).matches()
                if (isUrlValid) {
//                    downloadFileByNetwork(url)
//                    downloadFileByDownloadManager(url)
                    downloadFileByNetworkAndDataStore(url)
                } else {
                    toast(R.string.incorrect_url)
                }
            } else {
                toast(R.string.url_is_empty)
            }
        }
//        кнопка очистки строки ссылки
        binding.clearEditTextButton.setOnClickListener {
            binding.fileEditText.text.clear()
        }
//        подписка на обновления LiveData
        observe()
    }

    //  загрузка файлов через Network и с использованием обычной ViewModel c SharedPrefs
    private fun downloadFileByNetwork(url: String) {
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
                        requireContext().getExternalFilesDir(MainFragmentRepository.FILES_DIR_NAME)
                    val name = url.substring(url.lastIndexOf('/') + 1, url.length)
                    viewModel.downloadFile(url, name, sharedPrefs, filesDir!!)
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

    //  загрузка файлов через Network и с использованием dataStore
    private fun downloadFileByNetworkAndDataStore(url: String) {
//        создаем корутину для работы с внешним хранилищем
        lifecycleScope.launch(Dispatchers.IO) {
//           проверяем доступность внешнего хранилища, если недоступен, то заканчиваем функцию
            if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED) return@launch toast(
                R.string.storage_is_not_available
            )
            try {
//              задаем адрес директории и имя файла для скачивания с расширением
                val filesDir =
                    requireContext().getExternalFilesDir(MainFragmentRepository.FILES_DIR_NAME)
                val name = url.substring(url.lastIndexOf('/') + 1, url.length)
                dataStoreViewModel.downloadFile(url, name, filesDir!!)
            } catch (t: Throwable) {
//                переходим на главный потом для выброса тоста ошибки
                mainHandler.post {
                    toast(R.string.something_wrong)
                }
            }
        }
    }


    //    загрузка файлов из assets при первом запуске
    private fun firstRunDownload() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
//              открытие файла из assets
                resources.assets.open("file_for_first_run_download.txt")
//                   чтение
                    .bufferedReader()
//                   запись
                    .use {
                        it.readText()
                    }
//                  выполнение загрузки для каждой ссылки
                    .split(",").toTypedArray().forEach { firstRunDownloads ->
                        downloadFileByNetwork(firstRunDownloads)
                    }
//                изменяем флаг в случае успешного скачивания
                sharedPrefs.edit()
                    .putBoolean(MainFragmentRepository.FIRST_RUN, false)
                    .apply()
//                переходим на главный поток для выброса тоста
                mainHandler.post {
                    toast(R.string.first_run_download)
                }
            } catch (t: Throwable) {
//              переходим на главный потом для выброса тоста ошибки
                mainHandler.post {
                    toast(R.string.something_wrong)
                }
            }
        }
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
                        requireContext().getExternalFilesDir(MainFragmentRepository.FILES_DIR_NAME)
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

    //    подписка на обновления LiveData для обеих ViewModel
    private fun observe() {
//        подписки на обычную ViewModel с испольованием SharedPrefs
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
        viewModel.downloadByDownloadManager.observe(viewLifecycleOwner) { downloadByDM ->
            if (downloadByDM) {
                isLoadingDM()
            } else {
                finishLoading()
            }
        }
//        подписки на ViewModel DataStore
        dataStoreViewModel.download.observe(viewLifecycleOwner) { download ->
            if (download) {
                isLoading()
            } else {
                finishLoading()
            }
        }
        dataStoreViewModel.isError.observe(viewLifecycleOwner) { error ->
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        }
        dataStoreViewModel.isFinished.observe(viewLifecycleOwner) { finishString ->
            Toast.makeText(requireContext(), finishString, Toast.LENGTH_SHORT).show()
        }
    }

    //    статус Вьюшек при загрузке файлов
    private fun isLoading() {
        binding.fileEditText.isEnabled = false
        binding.filesButton.isEnabled = false
        binding.downloadProgressBar.isVisible = true
    }

    //    статус Вьюшек при загрузке файлов через DownloadManager
    private fun isLoadingDM() {
        binding.fileEditText.isEnabled = false
        binding.filesButton.isEnabled = false
    }

    //    обычный статус Вьюшек
    private fun finishLoading() {
        binding.fileEditText.isEnabled = true
        binding.filesButton.isEnabled = true
        binding.downloadProgressBar.isVisible = false
    }

}