package com.skillbox.skillbox.flow.adapter

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.skillbox.skillbox.flow.database.MovieEntity

class MovieAdapter : AsyncListDifferDelegationAdapter<MovieEntity>(MovieDiffUtil()) {

    init {
        delegatesManager.addDelegate(MovieAdapterDelegate())
    }

    //    создаем DiffUtil для нашего адаптера
    class MovieDiffUtil : DiffUtil.ItemCallback<MovieEntity>() {
        override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
//            сравниваем клубы по названию на первичном этапе
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
//            сравниваем остальные поля, если название совпадает
            return oldItem == newItem
        }
    }
}