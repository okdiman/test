package com.skillbox.skillbox.networking.activityandfragments

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.skillbox.skillbox.networking.classes.Movie

class AdapterMovies(onItemClick: (position: Int) -> Unit) :
    AsyncListDifferDelegationAdapter<Movie>(MovieDiffUtilCallback()) {
    //инициализируем делегата
    init {
        delegatesManager.addDelegate(AdapterMoviesDelegate(onItemClick))
    }

    //создаем DiffUtil для обновления списка
    class MovieDiffUtilCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return true
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }
}