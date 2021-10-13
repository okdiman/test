package com.skillbox.skillbox.testonlineshop.adapters

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.skillbox.skillbox.testonlineshop.classes.BestSellers
import com.skillbox.skillbox.testonlineshop.databinding.BestSellerItemBinding
import com.skillbox.skillbox.testonlineshop.utils.glideLoadImage
import com.skillbox.skillbox.testonlineshop.utils.inflate

class BestSellersAdapterDelegate :
    AbsListItemAdapterDelegate<BestSellers, BestSellers, BestSellersAdapterDelegate.Holder>() {
    class Holder(private val binding: BestSellerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: BestSellers) {
            binding.run {
                bestSellerItemImageView.glideLoadImage(item.picture.toUri())
                modelPhoneTextView.text = item.title
                newCostTextView.text = "${item.newPrice}$"
                oldCostTextView.text = "${item.oldPrice}$"
            }
        }
    }

    override fun isForViewType(
        item: BestSellers,
        items: MutableList<BestSellers>,
        position: Int
    ): Boolean {
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup): Holder {
        return Holder(parent.inflate(BestSellerItemBinding::inflate))
    }

    override fun onBindViewHolder(item: BestSellers, holder: Holder, payloads: MutableList<Any>) {
        return holder.bind(item)
    }
}