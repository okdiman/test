package com.skillbox.skillbox.testonlineshop.adapters

import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.skillbox.skillbox.testonlineshop.classes.Product
import com.skillbox.skillbox.testonlineshop.databinding.HotSalesItemBinding
import com.skillbox.skillbox.testonlineshop.utils.glideLoadImage
import com.skillbox.skillbox.testonlineshop.utils.inflate

class HotSalesAdapterDelegate :
    AbsListItemAdapterDelegate<Product, Product, HotSalesAdapterDelegate.Holder>() {

    //    создаем холдер для recycler view
    class Holder(private val binding: HotSalesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Product) {
            //        баиндим все необхоимые данные продукта во вьюшку
            binding.run {
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