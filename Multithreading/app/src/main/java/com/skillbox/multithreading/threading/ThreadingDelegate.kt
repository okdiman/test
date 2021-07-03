package com.skillbox.multithreading.threading

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.skillbox.multithreading.R
import com.skillbox.multithreading.inflate
import com.skillbox.multithreading.networking.Movie
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_movie.*

class ThreadingDelegate(private val onItemClick: (position: Int) -> Unit) :
    AbsListItemAdapterDelegate<Movie, Movie, ThreadingDelegate.MovieViewHolder>() {

    override fun isForViewType(item: Movie, items: MutableList<Movie>, position: Int): Boolean {
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup): MovieViewHolder {
        return MovieViewHolder(parent.inflate(R.layout.item_movie), onItemClick)
    }

    override fun onBindViewHolder(
        item: Movie,
        holder: MovieViewHolder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }

    class MovieViewHolder(
        override val containerView: View,
        onItemClick: (position: Int) -> Unit
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        init {
            containerView.setOnClickListener {
                onItemClick(adapterPosition)
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(movie: Movie) {
            movieTextView.text = movie.title
            yearTextView.text = "${movie.year} year"
        }
    }
}