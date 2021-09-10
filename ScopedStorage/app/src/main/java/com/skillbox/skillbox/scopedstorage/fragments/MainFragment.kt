package com.skillbox.skillbox.scopedstorage.fragments

import android.Manifest
import android.app.Activity
import android.app.RemoteAction
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
    private var videoAdapter: VideoAdapter? = null
    private val mainViewModel: MainFragmentViewModel by viewModels()
    private lateinit var recoverableActionLauncher: ActivityResultLauncher<IntentSenderRequest>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initRecoverableActionListener()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initVideoList()
        bindingViewModel()
        binding.addNewVideoButton.setOnClickListener {
            if (haveQ()) {
                addNewVideo()
            } else {
                requestPermissionForWriting()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        videoAdapter = null
    }

    private fun initVideoList() {
        videoAdapter = VideoAdapter(mainViewModel::deleteVideo)
        with(binding.videoRV) {
            adapter = videoAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
        requestPermissionForReading()
    }

    private fun addNewVideo() {
        findNavController().navigate(MainFragmentDirections.actionMainFragmentToAddDialogFragment())
    }

    private fun requestPermissionForReading() {
        Handler(Looper.getMainLooper()).post {
            constructPermissionsRequest(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                onShowRationale = ::onShowRationale,
                onNeverAskAgain = ::onNeverAskAgain,
                onPermissionDenied = ::onPermissionDenied,
                requiresPermission = { mainViewModel.isObserving() }
            )
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

    private fun requestPermissionForWriting() {
        Handler(Looper.getMainLooper()).post {
            constructPermissionsRequest(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                onShowRationale = ::onShowRationale,
                onNeverAskAgain = ::onNeverAskAgainWriting,
                onPermissionDenied = ::onPermissionDeniedWriting,
                requiresPermission = ::addNewVideo
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

    private fun initRecoverableActionListener() {
        recoverableActionLauncher = registerForActivityResult(
            ActivityResultContracts.StartIntentSenderForResult()
        ) { activityResult ->
            val isConfirmed = activityResult.resultCode == Activity.RESULT_OK
            if (isConfirmed) {
                mainViewModel.confirmDelete()
            } else {
                mainViewModel.declineDelete()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun handleRecoverableAction(action: RemoteAction) {
        val request = IntentSenderRequest.Builder(action.actionIntent.intentSender)
            .build()
        recoverableActionLauncher.launch(request)
    }


    private fun bindingViewModel() {
        mainViewModel.videoForList.observe(viewLifecycleOwner) { videoAdapter?.items = it }

//    следим за статусом загрузки и взависимости от этого меняем статус вьюшек
        mainViewModel.isLoading.observe(viewLifecycleOwner) { binding.progressBar.isVisible = it }

//    выбрасываем тост с ошибкой в случае ошибки
        mainViewModel.isError.observe(viewLifecycleOwner) { toast(it) }

        mainViewModel.recoverableActionLiveData.observe(
            viewLifecycleOwner,
            ::handleRecoverableAction
        )
    }
}