package com.skillbox.skillbox.lists1

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UserAdapter(
    private val onItemClicked: (position: Int) -> Unit
) : RecyclerView.Adapter<UserAdapter.Holder>() {
    private var users: List<User> = emptyList()
    override fun getItemCount(): Int = users.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(parent.inflate(R.layout.item_user), onItemClicked)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val user = users[position]
        holder.bind(user)
    }

    class Holder(
        view: View,
        onItemClicked: (position: Int) -> Unit
    ) : RecyclerView.ViewHolder(view) {

        private val nameTextView: TextView = view.findViewById(R.id.nameTextView)
        private val ageTextView: TextView = view.findViewById(R.id.ageTextView)
        private val developerTextView: TextView = view.findViewById(R.id.developerTextView)
        private val avatarImageView: ImageView = view.findViewById(R.id.avatarImageView)

        @SuppressLint("SetTextI18n")
        fun bind(user: User) {
            nameTextView.text = user.name
            ageTextView.text = "Возраст ${user.age}"
            developerTextView.visibility = if (user.isDeveloper) View.VISIBLE else View.GONE
            avatarImageView.setImageResource(user.avatarLink)
        }

        init {
            view.setOnClickListener {
                onItemClicked(adapterPosition)
            }
        }
    }

    fun updateUsers(newUsers: List<User>) {
        users = newUsers

    }
}