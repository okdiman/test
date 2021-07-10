package com.skillbox.skillbox.networking.activityandfragments

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.skillbox.skillbox.networking.R
import com.skillbox.skillbox.networking.classes.Movie
import com.skillbox.skillbox.networking.inflate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_movie.*

class AdapterMoviesDelegate(private val onItemClick: (position: Int) -> Unit) :
    AbsListItemAdapterDelegate<Movie, Movie, AdapterMoviesDelegate.MovieViewHolder>() {
    class MovieViewHolder(onItemClick: (position: Int) -> Unit, override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {
        init {
            containerView.setOnClickListener { onItemClick(adapterPosition) }
        }

        fun bind(movie: Movie) {
            movieTextView.text = movie.title
            yearTextView.text = movie.year
        }
    }

    override fun isForViewType(item: Movie, items: MutableList<Movie>, position: Int): Boolean {
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup): MovieViewHolder {
        return MovieViewHolder(onItemClick, parent.inflate(R.layout.item_movie))
    }

    override fun onBindViewHolder(
        item: Movie,
        holder: MovieViewHolder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }
}