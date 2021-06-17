package com.skillbox.skillbox.myapplication

import android.content.Context
import android.view.View
import androidx.lifecycle.ViewModel
import com.skillbox.skillbox.myapplication.adapters.resorts.ResortsAdapter
import com.skillbox.skillbox.myapplication.classes.Resorts
import kotlinx.android.synthetic.main.add_new_resort.view.*

class ResortListViewModel : ViewModel() {

    private var resortsList = arrayListOf<Resorts>()
    private val repository = ResortRepository()
    private var resortsAdapter: ResortsAdapter? = null

    //  добавление нового элемента
    fun addResort(view: View, context: Context) {
        val newResort = repository.addResort(view, context)
        resortsList = (arrayListOf(newResort) + resortsList) as ArrayList<Resorts>
    }

    //  удаление элемента
    fun deleteResort(position: Int) {
        resortsList =
            repository.deleteResort(resortsList, position) as ArrayList<Resorts>
    }

    fun getResortList() {
        resortsAdapter?.items = resortsList
    }

    override fun onCleared() {
        super.onCleared()
        resortsAdapter = null
    }
}