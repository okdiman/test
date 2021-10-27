package com.skillbox.skillbox.testonlineshop.features.detail.presentation.screens

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.skillbox.skillbox.testonlineshop.R
import com.skillbox.skillbox.testonlineshop.databinding.ShopDetailsFragmentBinding
import com.skillbox.skillbox.testonlineshop.utils.withArguments
import java.text.DecimalFormat

class ShopDetailsFragment : Fragment(R.layout.shop_details_fragment) {
    private val binding: ShopDetailsFragmentBinding by viewBinding(ShopDetailsFragmentBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    //    инициализация экрана фрагмента, используя переданные данные
    @SuppressLint("SetTextI18n", "UseCompatLoadingForDrawables")
    private fun init() {
        binding.run {
            addToCartButton.text =
                "Add to cart     $${
                    DecimalFormat("#,##0.00").format(
                        requireArguments().getInt(
                            KEY_PRICE
                        )
                    )
                }"
            processorTextView.text = requireArguments().getString(KEY_CPU)
            cameraTextView.text = requireArguments().getString(KEY_CAMERA)
            capacityTextView.text = requireArguments().getString(KEY_SD)
            memoryTextView.text = requireArguments().getString(KEY_SSD)

//            для кнопок выбора цвета устанавливаем лисенеры и цвета
            colorOneActionButton.run {
                setBackgroundColor(
                    Color.parseColor(
                        requireArguments().getStringArrayList(
                            KEY_COLORS
                        )?.get(0)
                    )
                )
                val icon = context.getDrawable(R.drawable.ic_check)?.apply {
                    setBounds(0, 0, 60, 60)
                    setTint((resources.getColor(R.color.white, requireContext().theme)))
                }
                setCompoundDrawables(null, null, icon, null)
                setOnClickListener {
                    colorOneActionButton.setCompoundDrawables(null, null, icon, null)
                    colorTwoActionButton.setCompoundDrawables(null, null, null, null)
                }
            }
            colorTwoActionButton.run {
                setBackgroundColor(
                    Color.parseColor(
                        requireArguments().getStringArrayList(
                            KEY_COLORS
                        )?.get(1)
                    )
                )
                setOnClickListener {
                    val icon = context.getDrawable(R.drawable.ic_check)?.apply {
                        setBounds(0, 0, 60, 60)
                        setTint((resources.getColor(R.color.white, requireContext().theme)))
                    }
                    colorTwoActionButton.setCompoundDrawables(null, null, icon, null)
                    colorOneActionButton.setCompoundDrawables(null, null, null, null)
                }
            }

            capacityFirstButton.setOnClickListener {
                capacityFirstButton.run {
                    setBackgroundColor(resources.getColor(R.color.primary, requireContext().theme))
                    setTextColor(resources.getColor(R.color.white, requireContext().theme))
                    isAllCaps = true
                }
                capacitySecondButton.run {
                    setBackgroundColor(
                        resources.getColor(
                            R.color.white,
                            requireContext().theme
                        )
                    )
                    setTextColor(resources.getColor(R.color.black, requireContext().theme))
                    isAllCaps = false
                }
            }
            capacitySecondButton.setOnClickListener {
                capacitySecondButton.run {
                    setBackgroundColor(resources.getColor(R.color.primary, requireContext().theme))
                    setTextColor(resources.getColor(R.color.white, requireContext().theme))
                    isAllCaps = true
                }
                capacityFirstButton.run {
                    setBackgroundColor(
                        resources.getColor(
                            R.color.white,
                            requireContext().theme
                        )
                    )
                    setTextColor(resources.getColor(R.color.black, requireContext().theme))
                    isAllCaps = false
                }
            }
        }
    }

    companion object {
        private const val KEY_CPU = "cpu"
        private const val KEY_CAMERA = "camera"
        private const val KEY_SD = "sd"
        private const val KEY_SSD = "ssd"
        private const val KEY_COLORS = "colors"
        private const val KEY_CAPACITY = "capacity"
        private const val KEY_PRICE = "price"

        //        инстанс фрагмента для его открытия из tabLayout
        fun newInstance(
            cpu: String?,
            camera: String?,
            sd: String?,
            ssd: String?,
            colors: ArrayList<String>?,
            capacity: ArrayList<String>?,
            price: Int
        ): ShopDetailsFragment {
            return ShopDetailsFragment().withArguments {
                putString(KEY_CPU, cpu)
                putString(KEY_CAMERA, camera)
                putString(KEY_SD, sd)
                putString(KEY_SSD, ssd)
                putStringArrayList(KEY_COLORS, colors)
                putStringArrayList(KEY_CAPACITY, capacity)
                putInt(KEY_PRICE, price)
            }
        }
    }
}