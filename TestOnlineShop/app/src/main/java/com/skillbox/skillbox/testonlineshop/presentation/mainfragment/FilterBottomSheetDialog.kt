package com.skillbox.skillbox.testonlineshop.presentation.mainfragment


import android.graphics.Outline
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.widget.ArrayAdapter
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.skillbox.skillbox.testonlineshop.R
import com.skillbox.skillbox.testonlineshop.databinding.BottomSheetDialogBinding


class FilterBottomSheetDialog : BottomSheetDialogFragment() {
    private val binding: BottomSheetDialogBinding by viewBinding(BottomSheetDialogBinding::bind)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.bottom_sheet_dialog, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initStartScreen()
    }

    //    инициализация стартового экрана
    private fun initStartScreen() {
//    создаем адаптеры для раскрывающихся меню выбора модели, цены и размера
        val adapterModels = ArrayAdapter(
            requireContext(),
            R.layout.item_menu_layout,
            resources.getStringArray(R.array.phones_models_string_array)
        )
        val adapterSizes = ArrayAdapter(
            requireContext(),
            R.layout.item_menu_layout,
            resources.getStringArray(R.array.phones_sizes_string_array)
        )
        val adapterPrices = ArrayAdapter(
            requireContext(),
            R.layout.item_menu_layout,
            resources.getStringArray(R.array.phones_prices_string_array)
        )
        binding.run {
            brandAutoCompleteTextView.setAdapter(adapterModels)
            priceAutoCompleteTextView.setAdapter(adapterPrices)
            sizeAutoCompleteTextView.setAdapter(adapterSizes)
            backFloatingActionButton.setOnClickListener {
                dismiss()
            }
            doneBottomDialogButton.setOnClickListener {
//                так как реализация фильтра сейчас не требуется, просто закрываем диалог фрагмент
                dismiss()
            }
        }
    }
}