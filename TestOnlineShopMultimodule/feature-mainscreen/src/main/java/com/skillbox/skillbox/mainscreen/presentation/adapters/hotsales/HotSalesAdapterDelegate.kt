package com.skillbox.skillbox.mainscreen.presentation.adapters.hotsales

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.skillbox.skillbox.mainscreen.R
import com.skillbox.skillbox.mainscreen.databinding.HotSalesItemBinding
import com.skillbox.skillbox.mainscreen.domain.entities.HotSales
import com.skillbox.skillbox.core.utils.glideLoadImage
import com.skillbox.skillbox.core.utils.inflate

class HotSalesAdapterDelegate(private val onBuyButtonClick: () -> Unit) :
    AbsListItemAdapterDelegate<HotSales, HotSales, HotSalesAdapterDelegate.Holder>() {

    //    создаем холдер для recycler view
    class Holder(
        private val binding: HotSalesItemBinding,
        onBuyButtonClick: () -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        //        инициализируем клик лисенер для кнопки BuyNow
        init {
            binding.buyNowButton.setOnClickListener {
//                могли бы передать в лямбду все, что нам было бы нужно,
//                но по заданию этого не требуется
                onBuyButtonClick()
            }
        }

        @SuppressLint("ResourceAsColor")
        fun bind(item: HotSales) {
            //        баиндим все необхоимые данные продукта во вьюшку
            binding.run {
//                у модели самсунга устанавливаем цвет текста черным,
//                так как приходит картинка с белым фоном (по id почему-то не ловит его)
                if (item.title == "Samsung Galaxy A 71") {
                    modelTitleOfHotSalesTextView.setTextColor(R.color.black)
                    descriptionHotSalesTextView.setTextColor(R.color.black)
                }
                hotSalesImageView.run {
                    glideLoadImage(item.picture!!.toUri())
                    clipToOutline = true
                }
                modelTitleOfHotSalesTextView.text = item.title
                descriptionHotSalesTextView.text = item.subtitle
                buyNowButton.isEnabled = item.isBuy
                newIconHotSalesImageView.run {
                    isVisible = item.isNew
                    clipToOutline = true
                }
            }
        }
    }

    override fun isForViewType(
        item: HotSales,
        items: MutableList<HotSales>,
        position: Int
    ): Boolean {
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup): Holder {
        return Holder(parent.inflate(HotSalesItemBinding::inflate), onBuyButtonClick)
    }

    override fun onBindViewHolder(item: HotSales, holder: Holder, payloads: MutableList<Any>) {
        return holder.bind(item)
    }
}