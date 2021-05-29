package com.skillbox.skillbox.lists1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val users: List<User> = listOf(
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initList()
    }

    private fun initList() {
        with(userList) {
            adapter = UserAdapter (users + users + users + users)
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
        }
    }
}