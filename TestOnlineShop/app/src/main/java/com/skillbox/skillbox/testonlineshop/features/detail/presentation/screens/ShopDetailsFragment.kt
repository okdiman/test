package com.skillbox.skillbox.testonlineshop.features.detail.presentation.screens

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.skillbox.skillbox.testonlineshop.R
import com.skillbox.skillbox.testonlineshop.databinding.ShopDetailsFragmentBinding
import com.skillbox.skillbox.testonlineshop.features.detail.domain.entities.Product
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
        val product = requireArguments().getParcelable<Product>(KEY_PRODUCT)
        binding.run {
            addToCartButton.text =
                "Add to cart     $${DecimalFormat("#,##0.00").format(product?.price)}"
            processorTextView.text = product?.cpu
            cameraTextView.text = product?.camera
            capacityTextView.text = product?.sd
            memoryTextView.text = product?.ssd

//            для кнопок выбора цвета устанавливаем лисенеры и цвета
            colorOneActionButton.run {
                setBackgroundColor(Color.parseColor(product?.color?.get(0)))
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
                setBackgroundColor(Color.parseColor(product?.color?.get(1)))
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
        private const val KEY_PRODUCT = "key_product"

        //        инстанс фрагмента для его открытия из tabLayout
        fun newInstance(
            product: Product
        ): ShopDetailsFragment {
            return ShopDetailsFragment().withArguments {
                putParcelable(KEY_PRODUCT, product)
            }
        }
    }
}