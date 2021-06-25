package com.skillbox.multithreading.threading

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.skillbox.multithreading.networking.Movie
import kotlinx.android.extensions.LayoutContainer

class ThreadingDelegate(onItemClick: (position: Int) -> Unit) :
    AbsListItemAdapterDelegate<Movie, Movie, ThreadingDelegate.MovieViewHolder>() {

    class MovieViewHolder(
        override val containerView: View,
        onItemClick: (position: Int) -> Unit
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        init {
            containerView.setOnClickListener {
                onItemClick(bindingAdapterPosition)
            }
        }
    }

    override fun isForViewType(item: Movie, items: MutableList<Movie>, position: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun onCreateViewHolder(parent: ViewGroup): MovieViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(
        item: Movie,
        holder: MovieViewHolder,
        payloads: MutableList<Any>
    ) {
        TODO("Not yet implemented")
    }
}