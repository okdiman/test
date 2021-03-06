package com.skillbox.skillbox.testonlineshop.features.cart.presentation.adapters

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.skillbox.skillbox.testonlineshop.features.cart.domain.entities.Basket
import com.skillbox.skillbox.testonlineshop.databinding.CartDetailsItemBinding
import com.skillbox.skillbox.testonlineshop.utils.glideLoadImage
import com.skillbox.skillbox.testonlineshop.utils.inflate
import java.text.DecimalFormat

class CartInfoAdapterDelegate :
    AbsListItemAdapterDelegate<Basket, Basket, CartInfoAdapterDelegate.Holder>() {
    class Holder(private val binding: CartDetailsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: Basket) {
            binding.run {
                productImageView.run {
                    glideLoadImage(item.image.toUri())
                    clipToOutline = true
                }
                productTitle.text = item.title
                productPrice.text = "$${DecimalFormat("#0.00").format(item.price)}"
                itemsCountTextView.text = "2"
            }
        }
    }

    override fun isForViewType(item: Basket, items: MutableList<Basket>, position: Int): Boolean {
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup): Holder {
        return Holder(parent.inflate(CartDetailsItemBinding::inflate))
    }

    override fun onBindViewHolder(item: Basket, holder: Holder, payloads: MutableList<Any>) {
        return holder.bind(item)
    }
}