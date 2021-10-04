package com.skillbox.skillbox.flow.adapter

import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.skillbox.skillbox.flow.database.MovieEntity
import com.skillbox.skillbox.flow.databinding.MovieItemBinding
import com.skillbox.skillbox.flow.utils.glideLoadImage
import com.skillbox.skillbox.flow.utils.inflate

class MovieAdapterDelegate :
    AbsListItemAdapterDelegate<MovieEntity, MovieEntity, MovieAdapterDelegate.Holder>() {
    //    создаем класс Холдера для наших итемов
    class Holder(
        private val binding: MovieItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        //    баиндим пришедший объект в нашу вьюшку
        fun bind(movie: MovieEntity) {
            binding.titleOfMovieTextView.text = movie.title
            binding.yearOfMovieTextView.text = movie.year
            binding.typeOfMovieTextView.text = movie.type.toString()
            binding.runtimeOfMovieTextView.text = movie.runtime
            binding.genreOfMovieTextView.text = movie.genre
            if (movie.poster != null) {
                binding.posterImageView.glideLoadImage(movie.poster.toUri())
            } else {
                val uriString = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS8yYvWo1jgVMpAqCuJN0bnHClazYXiYfjMlCvDnWh9wy3MTxna-54aVKCDO1Tqhr346m8&usqp=CAU"
                binding.posterImageView.glideLoadImage(uriString.toUri())
            }
        }
    }

    override fun isForViewType(
        item: MovieEntity,
        items: MutableList<MovieEntity>,
        position: Int
    ): Boolean {
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup): Holder {
        return Holder(parent.inflate(MovieItemBinding::inflate))
    }

    override fun onBindViewHolder(item: MovieEntity, holder: Holder, payloads: MutableList<Any>) {
        return holder.bind(item)
    }

}