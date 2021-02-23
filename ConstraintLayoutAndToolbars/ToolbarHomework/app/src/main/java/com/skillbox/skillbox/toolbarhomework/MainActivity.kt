package com.skillbox.skillbox.toolbarhomework

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val users = listOf(
        "Blagorodova",
        "Trishin",
        "Kopitov",
        "Okunev",
        "Sergeenko",
        "Lobanova",
        "Verde",
        "Bazilevskii",
        "Basiyk",
        "Lopatin",
        "Chaschin"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initToolbar()
    }


    private fun initToolbar() {
        Toolbar.setNavigationOnClickListener {
            toast("back")
        }

        Toolbar.setOnMenuItemClickListener { MenuItem ->
            when (MenuItem.itemId) {
                R.id.friends -> {
                    toast("Friends list")
                    true
                }
                R.id.music -> {
                    toast("Music library")
                    true
                }
                R.id.messenger -> {
                    toast("Your messages")
                    true
                }
                else -> {
                    false

                }
            }

        }
        val searchItem = Toolbar.menu.findItem(R.id.search)
        searchItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                return false
            }
        })


        (searchItem.actionView as SearchView).setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    users.filter { it.contains(newText?: "", ignoreCase = true) }
                        .joinToString()
                        .let {
                            searchResult.text = it
                        }
                    return true
                }
            })
    }


    private fun toast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }
}