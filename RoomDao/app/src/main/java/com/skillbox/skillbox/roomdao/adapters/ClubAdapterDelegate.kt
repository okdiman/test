package com.skillbox.skillbox.roomdao.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.skillbox.skillbox.contentprovider.inflate
import com.skillbox.skillbox.roomdao.R
import com.skillbox.skillbox.roomdao.database.entities.Clubs
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.clubs_item.*

class ClubAdapterDelegate(private val onClubClick: (Clubs) -> Unit) :
    AbsListItemAdapterDelegate<Clubs, Clubs, ClubAdapterDelegate.Holder>() {

    //    создаем класс Холдера для наших итемов
    class Holder(
        override val containerView: View,
        onClubClick: (Clubs) -> Unit
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        //    создаем нуллабельный currentClub так как хотим передать клуб в клик лисенер
        private var currentClubs: Clubs? = null

        //    инициализируем клик Лисенер
        init {
            containerView.setOnClickListener { currentClubs?.let(onClubClick) }
        }

        //    баиндим пришедший объект в нашу вьюшку
        fun bind(clubs: Clubs) {
            currentClubs = clubs
            Glide.with(itemView)
                .load(clubs.emblem)
                .error(R.drawable.ic_sync_problem)
                .placeholder(R.drawable.ic_cloud_download)
                .into(clubImageView)
            titleOfClubTextView.text = clubs.title
            cityOfClubTextView.text = clubs.city
            countryOfClubTextView.text = clubs.country
        }
    }

    //    имплементируем методы класса
    override fun isForViewType(
        item: Clubs,
        items: MutableList<Clubs>,
        position: Int
    ): Boolean {
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup): Holder {
        return Holder(parent.inflate(R.layout.clubs_item), onClubClick)
    }

    override fun onBindViewHolder(item: Clubs, holder: Holder, payloads: MutableList<Any>) {
        holder.bind(item)
    }
}