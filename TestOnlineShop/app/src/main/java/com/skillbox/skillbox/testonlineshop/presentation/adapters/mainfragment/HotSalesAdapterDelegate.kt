package com.skillbox.skillbox.testonlineshop.presentation.adapters.mainfragment

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.skillbox.skillbox.testonlineshop.R
import com.skillbox.skillbox.testonlineshop.data.models.Product
import com.skillbox.skillbox.testonlineshop.databinding.HotSalesItemBinding
import com.skillbox.skillbox.testonlineshop.utils.glideLoadImage
import com.skillbox.skillbox.testonlineshop.utils.inflate

class HotSalesAdapterDelegate :
    AbsListItemAdapterDelegate<Product, Product, HotSalesAdapterDelegate.Holder>() {

    //    создаем холдер для recycler view
    class Holder(private val binding: HotSalesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("ResourceAsColor")
        fun bind(item: Product) {
            //        баиндим все необхоимые данные продукта во вьюшку
            binding.run {
//                у модели самсунга устанавливаем цвет текста черным,
//                так как приходит картинка с белым фоном (по id почему-то не ловит его)
                if (item.title == "Samsung Galaxy A 71") {
                    modelTitleOfHotSalesTextView.setTextColor(R.color.black)
                    descriptionHotSalesTextView.setTextColor(R.color.black)
                }
                hotSalesImageView.glideLoadImage(item.picture!!.toUri())
                modelTitleOfHotSalesTextView.text = item.title
                descriptionHotSalesTextView.text = item.subtitle
                buyNowButton.isEnabled = item.isBuy
                newIconHotSalesImageView.isVisible = item.isNew
            }
        }
    }

    override fun isForViewType(
        item: Product,
        items: MutableList<Product>,
        position: Int
    ): Boolean {
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup): Holder {
        return Holder(parent.inflate(HotSalesItemBinding::inflate))
    }

    override fun onBindViewHolder(item: Product, holder: Holder, payloads: MutableList<Any>) {
        return holder.bind(item)
    }
}