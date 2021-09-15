package com.skillbox.skillbox.scopedstorage.fragments

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.RemoteAction
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.skillbox.skillbox.scopedstorage.R
import com.skillbox.skillbox.scopedstorage.adapters.VideoAdapter
import com.skillbox.skillbox.scopedstorage.databinding.MainFragmentBinding
import com.skillbox.skillbox.scopedstorage.utils.ViewBindingFragment
import com.skillbox.skillbox.scopedstorage.utils.haveQ
import com.skillbox.skillbox.scopedstorage.utils.toast
import permissions.dispatcher.ktx.constructPermissionsRequest

class MainFragment : ViewBindingFragment<MainFragmentBinding>(MainFragmentBinding::inflate) {
    //    создаем объект адаптера
    private var videoAdapter: VideoAdapter? = null

    //    создаем объект вью модели
    private val mainViewModel: MainFragmentViewModel by viewModels()

    //    создаем lateinit объекты лаунчеров для recoverable action
    //    и создания файла по выбранному пользваотелем адресу
    private lateinit var recoverableActionLauncher: ActivityResultLauncher<IntentSenderRequest>
    private lateinit var createDocumentLauncher: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        инициализируем оба лаунчера
        initRecoverableActionLauncher()
        initCreateVideoLauncher()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        инициализируем список видео
        initVideoList()
//        подписываемся на обновления вью модели
        bindingViewModel()
//        лисенер на снопку добавления видео
        binding.addNewVideoButton.setOnClickListener {
//            если у пользователя Android 10 и выше, то запрос на WRITE_EXTERNAL_STORAGE не нужен
            if (haveQ()) {
                addNewVideo(null)
            } else {
//               если ниже,то нужен запрос
                requestPermissionForWriting(null)
            }
        }
//        лисенер на кнопку создания файла с указанной пользователем директорией
        binding.downloadToSelectedFolderButton.setOnClickListener {
            createVideo()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
//        зануляем адаптер
        videoAdapter = null
    }

    //    инициализация списка видео
    private fun initVideoList() {
        videoAdapter = VideoAdapter(::onDeleteVideo)
        with(binding.videoRV) {
            adapter = videoAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
//    выполняем запрос для чтения списка по необходимости
        requestPermissionForReading()
    }

    //    удаление видео
    private fun onDeleteVideo(videoId: Long) {
//    вызываем диалог чтобы пользователь подтвердил удаление
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.delete_confirmation)
            .setMessage(R.string.delete_video)
            .setPositiveButton("Yes") { _, _ -> mainViewModel.deleteVideo(videoId) }
            .setNegativeButton("No") { _, _ -> }
            .show()
    }

    //    добавление нового видео
    private fun addNewVideo(uri: Uri?) {
//    в зависимости от значения uri передаем его или нулл
        if (uri != null) {
            findNavController().navigate(
                MainFragmentDirections.actionMainFragmentToAddDialogFragment(
                    uri.toString()
                )
            )
        } else {
            findNavController().navigate(
                MainFragmentDirections.actionMainFragmentToAddDialogFragment(
                    null
                )
            )
        }
    }

    //    запрос разрешения для чтение файлов
    private fun requestPermissionForReading() {
        Handler(Looper.getMainLooper()).post {
//            с помощью добавленной библиотеки программа автоматически определяет какой тип действия
//            ей нужно совершить, мы лишь указываем действия на каждое событие
            constructPermissionsRequest(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                onShowRationale = ::onShowRationale,
                onNeverAskAgain = ::onNeverAskAgain,
                onPermissionDenied = ::onPermissionDenied,
                requiresPermission = { mainViewModel.isObserving() }
            )
//                    запускаем constructPermissionsRequest
                .launch()
        }
    }

    private fun onShowRationale(request: permissions.dispatcher.PermissionRequest) {
        request.proceed()
    }

    private fun onNeverAskAgain() {
        toast(R.string.on_never_ask_again)
    }

    private fun onPermissionDenied() {
        toast(R.string.permission_denied)
    }

    //    запрос разрешения на запись файлов(актуально для версии Android < 10)
    private fun requestPermissionForWriting(uri: Uri?) {
        Handler(Looper.getMainLooper()).post {
            constructPermissionsRequest(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                onShowRationale = ::onShowRationale,
                onNeverAskAgain = ::onNeverAskAgainWriting,
                onPermissionDenied = ::onPermissionDeniedWriting,
                requiresPermission = { (::addNewVideo)(uri) }
            )
                .launch()
        }
    }

    private fun onNeverAskAgainWriting() {
        toast(R.string.on_never_ask_again_writing)
    }

    private fun onPermissionDeniedWriting() {
        toast(R.string.permission_denied_writing)
    }

    //    инициализируем лаунчер для recoverable action (выдается диалог пользователю
    //    с выбором принятия или непринятия действия с видео другого приложения)
    private fun initRecoverableActionLauncher() {
//    указываем контракт для лаунчера
        recoverableActionLauncher = registerForActivityResult(
            ActivityResultContracts.StartIntentSenderForResult()
        ) { activityResult ->
//            проверяем ответ пользователя
            val isConfirmed = activityResult.resultCode == Activity.RESULT_OK
//            в зависимости от ответа выполняем определенные дейтсвия
            if (isConfirmed) {
                mainViewModel.confirmDelete()
            } else {
                mainViewModel.declineDelete()
            }
        }
    }

    //    инициализируем лаунчер для создания пикера, чтобы пользователь мог выбрать
    //    директорию для сохранения файла
    private fun initCreateVideoLauncher() {
//    указываем контракт
        createDocumentLauncher = registerForActivityResult(
            ActivityResultContracts.CreateDocument()
        ) { uri ->
//            передаем uri в handler
            handleCreateVideo(uri)
        }
    }

    //    сохранение видео
    private fun createVideo() {
//        активируем лаунчер
        createDocumentLauncher.launch("new video")
    }

    //    хэндлер для сохранения видео после выбора пользователем директории
    private fun handleCreateVideo(uri: Uri?) {
//        если пользователь не выбрал директорию, то выдаем тост
        if (uri == null) {
            toast("file not created")
            return
        }
//        в зависимости от того имеет ли пользователь android 10 и выше или нет
//        либо напрямую вызываем ф-ую загрузки, либо через конструкотр запросов
        if (haveQ()) {
            addNewVideo(uri)
        } else {
            requestPermissionForWriting(uri)
        }
    }

    //    хэндлер для RecoverableAction
    @RequiresApi(Build.VERSION_CODES.O)
    private fun handleRecoverableAction(action: RemoteAction) {
//    создаем интент SenderRequest
        val request = IntentSenderRequest.Builder(action.actionIntent.intentSender)
            .build()
//        запускаем лаунчер, передавая ему интент
        recoverableActionLauncher.launch(request)
    }

    //    подписка на обноления вью модели
    private fun bindingViewModel() {
//    при получении списка видео, устанавливаем его в адаптер
        mainViewModel.videoForList.observe(viewLifecycleOwner) { videoAdapter?.items = it }

//    следим за статусом загрузки и взависимости от этого меняем статус вьюшек
        mainViewModel.isLoading.observe(viewLifecycleOwner) { binding.progressBar.isVisible = it }

//    выбрасываем тост с ошибкой в случае ошибки
        mainViewModel.isError.observe(viewLifecycleOwner) { toast(it) }
//        при получении ответа пользователя на диалог подствержения взаимодействия
//        с файлом другого приложения вызываем хэндлер
        mainViewModel.recoverableActionLiveData.observe(
            viewLifecycleOwner,
            ::handleRecoverableAction
        )
    }
}