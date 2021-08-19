package com.skillbox.skillbox.contentprovider.main

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.skillbox.skillbox.contentprovider.BuildConfig
import com.skillbox.skillbox.contentprovider.R
import com.skillbox.skillbox.contentprovider.adapter.FileListAdapter
import com.skillbox.skillbox.contentprovider.classes.FileForList
import com.skillbox.skillbox.contentprovider.databinding.ShareFileFragmentBinding
import com.skillbox.skillbox.contentprovider.toast
import kotlinx.android.synthetic.main.view_toolbar.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import kotlin.random.Random

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

    private lateinit var changeListener: SharedPreferences.OnSharedPreferenceChangeListener

    private var fileAdapter: FileListAdapter? = null

    private val viewModel: ShareFileFragmentViewModel by viewModels()

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
        fileAdapter = null
        sharedPrefs.unregisterOnSharedPreferenceChangeListener(changeListener)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe()
        startScreen()
        binding.downloadFailButton.setOnClickListener {
//            проверяем заполненность поля ссылки
            if (binding.uriEditText.text.toString().isNotEmpty()) {
//              инициализируем url для клика
                val url = binding.uriEditText.text.toString()
//                проверяем соотвествие введенной ссылки с типом Url
                val isUrlValid = Patterns.WEB_URL.matcher(url).matches()
                if (isUrlValid) {
                    downloadFileByDownloadManager(url)
                } else {
                    toast(R.string.incorrect_url)
                }
            } else {
                toast(R.string.url_is_empty)
            }
        }
    }


    private fun updateList(key: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            val name = sharedPrefs.getString(key, "null")
            Log.i("name", name.toString())
            fileAdapter?.items =
                fileAdapter?.items?.plus(listOf(FileForList(Random.nextLong(), name!!)))
        }
    }

    private fun startScreen() {
        fileAdapter = FileListAdapter {
            shareFile(it)
        }
        with(binding.listOfDownloadedFiles) {
            adapter = fileAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
        val listOfFiles = mutableListOf<FileForList>()
        sharedPrefs.all.mapNotNull {
            listOfFiles.add(FileForList(Random.nextLong(), it.value.toString()))
        }
        fileAdapter?.items = listOfFiles
        toolbar.title = "Sharing of File"
        lifecycleScope.launch(Dispatchers.IO) {
            changeListener =
                SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
                    updateList(key)
                }
            sharedPrefs.registerOnSharedPreferenceChangeListener(changeListener)
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

    //    подписка на обновления LiveData для обеих ViewModel
    private fun observe() {
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
    }

    private fun shareFile(fileForList: FileForList) {
        lifecycleScope.launch {
            if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED) return@launch toast(
                R.string.storage_is_not_available
            )
            val filesDir =
                requireContext().getExternalFilesDir(FILES_DIR_NAME)
            val file = File(filesDir, fileForList.name)
            if (file.exists().not()) return@launch
            val uri = FileProvider.getUriForFile(
                requireContext(),
                "${BuildConfig.APPLICATION_ID}.file_provider",
                file
            )
            val intent = Intent(Intent.ACTION_SEND).apply {
                putExtra(Intent.EXTRA_STREAM, uri)
                type = requireContext().contentResolver.getType(uri)
                flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            }
            val shareIntent = Intent.createChooser(intent, null)
            startActivity(shareIntent)
        }
    }

    //    статус Вьюшек при загрузке файлов через DownloadManager
    private fun isLoadingDM() {
        binding.downloadFailButton.isEnabled = false
        binding.listOfDownloadedFiles.isEnabled = false
        binding.uriEditText.isEnabled = false
        binding.shareTextView.isEnabled = false
    }

    //    обычный статус Вьюшек
    private fun finishLoading() {
        binding.downloadFailButton.isEnabled = true
        binding.listOfDownloadedFiles.isEnabled = true
        binding.uriEditText.isEnabled = true
        binding.shareTextView.isEnabled = false
    }

    companion object {
        const val FILES_DIR_NAME = "Folder for downloads files"
        const val SHARED_PREF = "Shared preferences"
    }

}