package com.skillbox.skillbox.roomdao.adapters

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.skillbox.skillbox.roomdao.utils.inflate
import com.skillbox.skillbox.roomdao.R
import com.skillbox.skillbox.roomdao.database.entities.Tournaments
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.tournament_item.*

class TournamentAdapterDelegate(private val onTournamentClick: (Tournaments) -> Unit) :
    AbsListItemAdapterDelegate<Tournaments, Tournaments, TournamentAdapterDelegate.Holder>() {

    //    создаем класс Холдера для наших итемов
    class Holder(
        override val containerView: View,
        onTournamentClick: (Tournaments) -> Unit
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        //    создаем нуллабельный currentTournament так как хотим передать турнир в клик лисенер
        private var currentTournament: Tournaments? = null

//    инициализируем клик Лисенер

        init {
            containerView.setOnClickListener { currentTournament?.let(onTournamentClick) }
        }

        //    баиндим пришедший объект в нашу вьюшку
        @SuppressLint("SetTextI18n")
        fun bind(tournaments: Tournaments) {
            currentTournament = tournaments
//            Glide.with(itemView)
//                .load(tournaments.cupPicture)
//                .error(R.drawable.ic_sync_problem)
//                .placeholder(R.drawable.ic_cloud_download)
//                .into(cupPictureImageView)
            titleOfTournamentTextView.text = "Title: ${tournaments.title}"
            typeOfTournamentTextView.text = "Type: ${tournaments.type}"
            clubsCountInTournamentTextView.text =
                "Clubs count: ${tournaments.clubsCount.toString()}"
            prizeMoneyOfTournamentTextView.text =
                "Prize money: ${tournaments.prizeMoney.toString()} euro"
        }
    }

    //    имплементируем методы класса
    override fun isForViewType(
        item: Tournaments,
        items: MutableList<Tournaments>,
        position: Int
    ): Boolean {
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup): Holder {
        return Holder(parent.inflate(R.layout.tournament_item), onTournamentClick)
    }

    override fun onBindViewHolder(item: Tournaments, holder: Holder, payloads: MutableList<Any>) {
        holder.bind(item)
    }
}