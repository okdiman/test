package com.skillbox.skillbox.testonlineshop.adapters

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.skillbox.skillbox.testonlineshop.classes.Product
import com.skillbox.skillbox.testonlineshop.databinding.BestSellerItemBinding
import com.skillbox.skillbox.testonlineshop.utils.glideLoadImage
import com.skillbox.skillbox.testonlineshop.utils.inflate

class BestSellersAdapterDelegate :
    AbsListItemAdapterDelegate<Product, Product, BestSellersAdapterDelegate.Holder>() {

    //    создаем холдер для recycler view
    class Holder(private val binding: BestSellerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        //        баиндим все необхоимые данные продукта во вьюшку
        @SuppressLint("SetTextI18n")
        fun bind(item: Product) {
            binding.run {
                bestSellerItemImageView.glideLoadImage(item.picture!!.toUri())
                modelPhoneTextView.text = item.title
                newCostTextView.text = "${item.newPrice}$"
                oldCostTextView.text = "${item.oldPrice}$"
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
        return Holder(parent.inflate(BestSellerItemBinding::inflate))
    }

    override fun onBindViewHolder(item: Product, holder: Holder, payloads: MutableList<Any>) {
        return holder.bind(item)
    }
}