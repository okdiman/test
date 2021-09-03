package com.skillbox.skillbox.roomdao.adapters

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.skillbox.skillbox.roomdao.R
import com.skillbox.skillbox.roomdao.database.entities.Tournaments
import com.skillbox.skillbox.roomdao.utils.glideLoadImage
import com.skillbox.skillbox.roomdao.utils.inflate
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
            cupPictureImageView.glideLoadImage(tournaments.cupPicture.toUri())
            titleOfTournamentTextView.text = "Title: ${tournaments.title}"
            typeOfTournamentTextView.text = "Type: ${tournaments.type}"
            if (tournaments.prizeMoney != null) {
                prizeMoneyOfTournamentTextView.text =
                    "Prize money: ${tournaments.prizeMoney.toString()} euro"
            } else {
                prizeMoneyOfTournamentTextView.text =
                    "Prize money unknown"
            }
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