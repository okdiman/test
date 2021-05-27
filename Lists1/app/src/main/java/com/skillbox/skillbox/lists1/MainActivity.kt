package com.skillbox.skillbox.lists1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val user: List<User> = listOf(
        User(
            name = "Дмитрий Окунев",
            avatarLink = "https://www.google.com/url?sa=i&url=https%3A%2F%2Fvjoy.cc%2Favatarki-dlya-malchikov-41-foto%2F&psig=AOvVaw0RxPyRiz8I4G1u6Af9sgIb&ust=1622206672719000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCMi0hfr06fACFQAAAAAdAAAAABAJ",
            age = 26,
            isDeveloper = true
        ),
        User(
            name = "Оксана Окунева",
            avatarLink = "https://www.google.com/url?sa=i&url=https%3A%2F%2Frg.ru%2F2019%2F10%2F31%2Feksperty-rasskazali-gde-rabotaiut-samye-schastlivye-liudi.html&psig=AOvVaw17Hphqwpj1m1Hd8r7YEIin&ust=1622206782827000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCKDB8a716fACFQAAAAAdAAAAABAD",
            age = 25,
            isDeveloper = false
        ),
        User(
            name = "Арсений Попов",
            avatarLink = "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.rbc.ru%2Fnewspaper%2F2017%2F08%2F29%2F59a012e89a794754d03d439a&psig=AOvVaw17Hphqwpj1m1Hd8r7YEIin&ust=1622206782827000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCKDB8a716fACFQAAAAAdAAAAABAK",
            age = 33,
            isDeveloper = false
        ),
        User(
            name = "Кирилл Евгорьев",
            avatarLink = "https://www.google.com/url?sa=i&url=https%3A%2F%2Frg.ru%2F2020%2F06%2F25%2Fserial-normalnye-liudi-poluchit-prodolzhenie.html&psig=AOvVaw17Hphqwpj1m1Hd8r7YEIin&ust=1622206782827000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCKDB8a716fACFQAAAAAdAAAAABAP",
            age = 18,
            isDeveloper = false
        ),
        User(
            name = "Наталья Афанасьева",
            avatarLink = "https://www.google.com/url?sa=i&url=http%3A%2F%2Fkaluga24.tv%2Flife%2Fstilnye-lyudi-goroda%2F&psig=AOvVaw17Hphqwpj1m1Hd8r7YEIin&ust=1622206782827000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCKDB8a716fACFQAAAAAdAAAAABAV",
            age = 24,
            isDeveloper = false
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}