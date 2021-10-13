package com.skillbox.skillbox.testonlineshop.adapters

import android.annotation.SuppressLint
import android.graphics.Paint
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.skillbox.skillbox.testonlineshop.R
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
                oldCostTextView.apply {
//                    перечеркиваем текст старой цены
                    paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                    text = "${item.oldPrice}$"
                }
//                в зависимости от метки is_favorites отображаем ту или иную картинку в кнопке
                if (item.is_favorites) {
                    isFavoriteBestSellerFloatingActionButton.apply {
                        setImageResource(R.drawable.ic_favorite_full)
                    }
                }
//                обрабатываем клик на isFavorites, меняем картинку и флаг у айтема
                isFavoriteBestSellerFloatingActionButton.setOnClickListener {
                    if (item.is_favorites) {
                        item.is_favorites = false
                        isFavoriteBestSellerFloatingActionButton.setImageResource(R.drawable.ic_favorite)
                    } else {
                        item.is_favorites = true
                        isFavoriteBestSellerFloatingActionButton.setImageResource(R.drawable.ic_favorite_full)
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
        return Holder(parent.inflate(BestSellerItemBinding::inflate))
    }

    override fun onBindViewHolder(item: Product, holder: Holder, payloads: MutableList<Any>) {
        return holder.bind(item)
    }
}