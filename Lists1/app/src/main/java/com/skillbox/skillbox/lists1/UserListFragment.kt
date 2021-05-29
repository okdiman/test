package com.skillbox.skillbox.lists1

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_user_list.*

class UserListFragment : Fragment(R.layout.fragment_user_list) {
    private var userAdapter: UserAdapter? = null
    private var users: List<User> = listOf(
        User(
            name = "Дмитрий Окунев",
            avatarLink = R.drawable.dm,
            age = 26,
            isDeveloper = true
        ),
        User(
            name = "Оксана Окунева",
            avatarLink = R.drawable.oo,
            age = 25,
            isDeveloper = false
        ),
        User(
            name = "Арсений Попов",
            avatarLink = R.drawable.asd,
            age = 33,
            isDeveloper = false
        ),
        User(
            name = "Кирилл Евгорьев",
            avatarLink = R.drawable.fgb,
            age = 18,
            isDeveloper = false
        ),
        User(
            name = "Наталья Афанасьева",
            avatarLink = R.drawable.vcvzx,
            age = 24,
            isDeveloper = false
        )
    )


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initList()
        addFab.setOnClickListener {
            addUser()
        }
        userAdapter?.updateUsers(users)
        userAdapter?.notifyDataSetChanged()
//        userAdapter.notifyItemRangeInserted(0, users.size)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        userAdapter = null
    }
    private fun initList() {
        userAdapter = UserAdapter{position -> deleteUser(position) }
        with(userList) {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun deleteUser (position: Int){
        users = users.filterIndexed{index, user -> index != position }
        userAdapter?.updateUsers(users)
        userAdapter?.notifyItemRemoved(position)
    }

    private fun addUser() {
        if (users.isNotEmpty()){
            val newUser = users.random()
            users = listOf(newUser) + users
            userAdapter?.updateUsers(users)
            userAdapter?.notifyItemInserted(0)
            userList.scrollToPosition(0)
        } else {
            Toast.makeText(requireContext(), "List is empty", Toast.LENGTH_SHORT).show()
        }

    }
}