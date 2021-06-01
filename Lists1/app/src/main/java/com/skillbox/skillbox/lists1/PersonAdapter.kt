package com.skillbox.skillbox.lists1

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView


class PersonAdapter(
    private val onItemClick: (position: Int) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var persons: List<Person> = emptyList()
    override fun getItemCount(): Int {
        return persons.size
    }
//переписываем метод для получения разных ViewType
    override fun getItemViewType(position: Int): Int {
        return when (persons[position]) {
            is Person.Developer -> TYPE_DEVELOPER
            is Person.User -> TYPE_USER
        }
    }
//выбираем исходя из значения ViewType нужный Холдер
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType){
            TYPE_USER -> UserHolder(parent.inflate(R.layout.item_user), onItemClick)
            TYPE_DEVELOPER -> DeveloperHolder(parent.inflate(R.layout.item_developer), onItemClick)
            else -> error("Incorrect ViewType = $viewType")
        }
    }
//просто выбираем исходя из типа холдера нужный bind
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is UserHolder -> {
                val person = persons[position].let { it as? Person.User }
                    ?: error("Person at position $position is not a user")
                holder.bind(person)
            }
            is DeveloperHolder -> {
                val person = persons[position].let { it as? Person.Developer }
                    ?: error("Person at position $position is not a developer")
                holder.bind(person)
            }
            else -> error("Incorrect ViewHolder = $holder")
        }
    }

//функция обновления списка
    fun updatePersons(newPersons: List<Person>) {
        persons = newPersons
    }
//Базовый VH для наследования от него основных
    abstract class BasePersonHolder(
        view: View,
        onItemClick: (position: Int) -> Unit
    ) : RecyclerView.ViewHolder(view) {
        private val nameTextView: TextView = view.findViewById(R.id.nameTextView)
        private val ageTextView: TextView = view.findViewById(R.id.ageTextView)
        private val avatarImageView: ImageView = view.findViewById(R.id.avatarImageView)
//обработка клика
        init {
            view.setOnClickListener {
                onItemClick(adapterPosition)
            }
        }
// общий баиндинг данных для обоих VH
        protected fun bindMainInfo(
            name: String,
            age: Int,
            avatarLink: Int
        ) {
            nameTextView.text = name
            ageTextView.text = "Возраст ${age}"
            avatarImageView.setImageResource(avatarLink)
        }
    }
// Вью Холдер для юзеров
    class UserHolder(
        view: View,
        onItemClick: (position: Int) -> Unit
    ) : BasePersonHolder(view, onItemClick) {
        init {
            view.findViewById<TextView>(R.id.developerTextView).isVisible = false
        }

        fun bind(person: Person.User) {
            bindMainInfo(person.name, person.age, person.avatarLink)
        }
    }
//Вью Холдер для Разрабов
    class DeveloperHolder(
        view: View,
        onItemClick: (position: Int) -> Unit
    ) : BasePersonHolder(view, onItemClick) {
        private val programmingLanguage = view.findViewById<TextView>(R.id.programmingLanguageTextView)

        fun bind (person: Person.Developer) {
            bindMainInfo(person.name, person.age, person.avatarLink)
            programmingLanguage.text = "Язык программирования ${person.programmingLanguage}"
//            получение строки через ресурсы VH
//            itemView.context.resources.getString(R.string.app_name)
        }
    }
// Константы для получения разных ViewType для разных классов
    companion object {
        private const val TYPE_USER = 1
        private const val TYPE_DEVELOPER = 2
    }

}