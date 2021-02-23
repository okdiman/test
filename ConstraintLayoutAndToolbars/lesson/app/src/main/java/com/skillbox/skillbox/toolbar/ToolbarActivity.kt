package com.skillbox.skillbox.toolbar

import android.os.Bundle
import android.view.MenuItem
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_toolbar.*

class ToolbarActivity : AppCompatActivity() {
    private val users = listOf<String>(
        "User1",
        "User2",
        "Unknown"
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_toolbar)
        initToolbar()
    }

    private fun toast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    private fun initToolbar() {
        toolbar.setNavigationOnClickListener {
            toast("Navigation clicked")
        }
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action1 -> {
                    toast("action 1 clicked")
                    true
                }
                R.id.action2 -> {
                    toast("action 2 clicked")
                    true
                }
                else -> false
            }
        }

        val searchItem = toolbar.menu.findItem(R.id.search)
        searchItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                expandTextView.text = "search expanded"
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                expandTextView.text = "search collapsed"
                return true
            }
        })

        (searchItem.actionView as SearchView).setOnQueryTextListener(
            object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                users.filter { it.contains(newText?:"", true) }
                    .joinToString()
                    .let {
                        searchResultTextView.text = it
                    }
                return true
            }

        } )

    }
}