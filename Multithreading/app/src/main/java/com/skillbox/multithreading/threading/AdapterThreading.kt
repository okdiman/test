package com.skillbox.multithreading.threading

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.skillbox.multithreading.networking.Movie

class AdapterThreading(onItemClick: (position: Int) -> Unit) :
    AsyncListDifferDelegationAdapter<Movie>(MovieDiffUtilCallback()) {

    init {
        delegatesManager.addDelegate(ThreadingDelegate(onItemClick))
    }

    class MovieDiffUtilCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return true
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }
}