package com.skillbox.skillbox.roomdao.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.skillbox.skillbox.contentprovider.inflate
import com.skillbox.skillbox.roomdao.R
import com.skillbox.skillbox.roomdao.database.entities.Tournaments
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.tournament_item.*

class TournamentAdapterDelegate(private val onTournamentClick: (Tournaments) -> Unit) :
    AbsListItemAdapterDelegate<Tournaments, Tournaments, TournamentAdapterDelegate.Holder>() {
    class Holder(
        override val containerView: View,
        onTournamentClick: (Tournaments) -> Unit
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        private var currentTournament: Tournaments? = null

        init {
            containerView.setOnClickListener { currentTournament?.let(onTournamentClick) }
        }

        fun bind(tournaments: Tournaments) {
            Glide.with(itemView)
                .load(tournaments.cupPicture)
                .error(R.drawable.ic_sync_problem)
                .placeholder(R.drawable.ic_cloud_download)
                .into(cupPictureImageView)
            titleOfTournamentTextView.text = tournaments.title
            typeOfTournamentTextView.text = tournaments.type.toString()
            clubsCountInTournamentTextView.text = tournaments.clubsCount.toString()
            prizeMoneyOfTournamentTextView.text = tournaments.prizeMoney.toString()
        }
    }

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