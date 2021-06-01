package com.skillbox.skillbox.lists1

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_user_list.*

class PersonListFragment : Fragment(R.layout.fragment_user_list) {
    private var personsAdapter: PersonAdapter? = null
    private var persons = listOf(
        Person.Developer(
            name = "Дмитрий Окунев",
            avatarLink = R.drawable.dm,
            age = 26,
            programmingLanguage = "Kotlin"
        ),
        Person.User(
            name = "Оксана Окунева",
            avatarLink = R.drawable.oo,
            age = 25
        ),
        Person.Developer(
            name = "Арсений Попов",
            avatarLink = R.drawable.asd,
            age = 33,
            programmingLanguage = "Java"
        ),
        Person.User(
            name = "Кирилл Евгорьев",
            avatarLink = R.drawable.fgb,
            age = 18
        ),
        Person.User(
            name = "Наталья Афанасьева",
            avatarLink = R.drawable.vcvzx,
            age = 24
        )
    )


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initList()
        addFab.setOnClickListener {
            addUser()
        }
        personsAdapter?.updatePersons(persons)
        personsAdapter?.notifyDataSetChanged()
//        userAdapter.notifyItemRangeInserted(0, users.size)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        personsAdapter = null
    }
//создание списка
    private fun initList() {
        personsAdapter = PersonAdapter { position -> deleteUser(position) }
        with(userList) {
            adapter = personsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }
//удаление элемента
    private fun deleteUser(position: Int) {
        persons = persons.filterIndexed { index, user -> index != position }
        personsAdapter?.updatePersons(persons)
        personsAdapter?.notifyItemRemoved(position)
    }
//добавление элемента
    private fun addUser() {
        if (persons.isNotEmpty()) {
            val newUser = persons.random()
            persons = listOf(newUser) + persons
            personsAdapter?.updatePersons(persons)
            personsAdapter?.notifyItemInserted(0)
//            автоскролл наверх
            userList.scrollToPosition(0)
        } else {
            Toast.makeText(requireContext(), "List is empty", Toast.LENGTH_SHORT).show()
        }

    }
}