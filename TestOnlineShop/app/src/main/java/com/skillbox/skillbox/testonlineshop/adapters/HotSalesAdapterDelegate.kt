package com.skillbox.skillbox.testonlineshop.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.skillbox.skillbox.testonlineshop.R
import com.skillbox.skillbox.testonlineshop.classes.Product
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
                if (item.title == "Samsung Galaxy A 71"){
                    modelTitleOfHotSalesTextView.setTextColor(R.color.black)
                    descriptionHotSalesTextView.setTextColor(R.color.black)
                }
                hotSalesImageView.glideLoadImage(item.picture!!.toUri())
                modelTitleOfHotSalesTextView.text = item.title
                descriptionHotSalesTextView.text = item.subtitle
                buyNowButton.isEnabled = item.isBuy
                newIconHotSalesImageView.isVisible = item.isNew
//                в зависимости от метки is_favorites отображаем ту или иную картинку в кнопке
                if (item.is_favorites) {
                    isFavoriteHotSellerFloatingActionButton.apply {
                        setImageResource(R.drawable.ic_favorite_full)
                    }
                }
                isFavoriteHotSellerFloatingActionButton.setOnClickListener {
                    if (item.is_favorites) {
                        item.is_favorites = false
                        isFavoriteHotSellerFloatingActionButton.setImageResource(R.drawable.ic_favorite)
                    } else {
                        item.is_favorites = true
                        isFavoriteHotSellerFloatingActionButton.setImageResource(R.drawable.ic_favorite_full)
                    }
                }
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