package com.skillbox.skillbox.scopedstorage.fragments

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.skillbox.skillbox.scopedstorage.R
import com.skillbox.skillbox.scopedstorage.databinding.AddNewVideoBinding
import com.skillbox.skillbox.scopedstorage.utils.isConnected
import com.skillbox.skillbox.scopedstorage.utils.toast

class AddDialogFragment : BottomSheetDialogFragment() {
    //    баиндим вьюшку BottomSheetDialog
    private var _binding: AddNewVideoBinding? = null
    private val binding get() = _binding!!

    //    флаг для переопредления метода dismiss
    private var doWeNeedToDeleteFile = true

    //    получаем переданные аргументы из предыдущего фрагмента
    private val args: AddDialogFragmentArgs by navArgs()

    //    создаем объект вью модели
    private val addDialogViewModel: AddDialogFragmentViewModel by viewModels()

    //    переописываем метод создания диалога, изменяя ему действия при нажатии
    //    пользователем кнопки назад
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return object : BottomSheetDialog(requireContext()) {
            override fun onBackPressed() {
                Log.i("addDialogUri", "${args.uri?.toUri()}")
                doWeNeedToDeleteFile = false
//                если мы получили uri созданного пикером файла, то нам нужно удалить этот файл,
//                если пользователь передумает что-то скачивать
                if (args.uri != null) {
                    addDialogViewModel.deleteVideo(args.uri!!.toUri())
                } else {
                    super.onBackPressed()
                }
            }

            //            переопределяем метод dismiss, чтобы при выходе из диалога кликом на область,
//            созданный пикером файл удалялся, если он был создан
            override fun dismiss() {
                if (doWeNeedToDeleteFile && args.uri != null) {
                    onBackPressed()
                } else {
                    super.dismiss()
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AddNewVideoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
//        зануляем баиндинг
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (args.uri != null) {
            binding.titleTextField.editText?.setText(R.string.picker)
            binding.titleTextField.editText?.isEnabled = false
        }
//        по клику на floatingActionButton сохраняем видео
        binding.saveButton.setOnClickListener {
            saveNewVideo()
        }
//        подписываемся на обновления вью модели
        bindingViewModel()
    }

    //    сохранение видео
    private fun saveNewVideo() {
//    проверяем наличие интернета на устройстве
        if (requireContext().isConnected) {
//            проверяем заполненность пользователем полей названия видео и url
            if (binding.titleTextField.editText!!.text.toString().isNotEmpty() &&
                binding.urlTextField.editText!!.text.toString().isNotEmpty()
            ) {
//                проверяем соответствие введеного поля url к стандартам url
                val isValidUrl =
                    Patterns.WEB_URL.matcher(binding.urlTextField.editText!!.text.toString())
                        .matches()
                if (isValidUrl) {
//                    проверяем передан во фрагмент uri путь для сохранения файла или нет
                    if (args.uri != null) {
//                        если да, то передаем uri во вью модель
                        addDialogViewModel.downloadVideo(
                            binding.titleTextField.editText!!.text.toString(),
                            binding.urlTextField.editText!!.text.toString(),
                            args.uri!!.toUri()
                        )
                    } else {
//                        если нет, то передаем вместо uri null
                        addDialogViewModel.downloadVideo(
                            binding.titleTextField.editText!!.text.toString(),
                            binding.urlTextField.editText!!.text.toString(),
                            null
                        )
                    }
                } else {
//                    выбрасываем тост о некорректном uri, в случае данной ошибки
                    toast(R.string.incorrect_url)
                }
            } else {
//                выбрасываем тост о некорреткно заполненной форме в случае,
                //                если форма заполнена не полностью
                toast(R.string.incorrect_form)
            }
        } else {
//            выбрасываем тост в случае отсутствия интернета у пользователя
            toast(R.string.internet_is_not_available)
        }
    }

    //    подписка на обновления вью модели
    private fun bindingViewModel() {
//    выбрасываем тост в зависисмости от пришедшего результата
//    и в любом случае закрываем диалог фрагмент
        addDialogViewModel.videoDownloaded.observe(viewLifecycleOwner) { result ->
            when (result) {
                "Success" -> toast(R.string.successful_download)
                "Throwable" -> toast(R.string.unsuccessful_download)
                "Url error" -> toast(R.string.url_error)
                else -> toast(R.string.incorrect_result)
            }
//            выставляем флаг необходимости удаления файла в false
            doWeNeedToDeleteFile = false
            dismiss()
        }

//    следим за статусом загрузки и взависимости от этого меняем статус вьюшек
        addDialogViewModel.isLoading.observe(viewLifecycleOwner) {
            binding.progressBar.isVisible = it
        }

//    выбрасываем тост с ошибкой в случае ошибки
        addDialogViewModel.isError.observe(viewLifecycleOwner) { toast(it) }

//        в случае вынужденного удаления файла после нажатия кнопки назад пользователем,
//        выдаем тост и закрываем диалог фрагмент
        addDialogViewModel.deleted.observe(viewLifecycleOwner) { deleted ->
            if (deleted) {
                toast(R.string.reject_adding)
                this.dismiss()
            }
        }
    }
}