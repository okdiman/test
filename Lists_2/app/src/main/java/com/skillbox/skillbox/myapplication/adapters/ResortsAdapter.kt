package com.skillbox.skillbox.myapplication.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.skillbox.skillbox.myapplication.classes.Resorts
import kotlinx.android.synthetic.main.item_mountain.*
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.skillbox.skillbox.myapplication.R
import kotlinx.android.synthetic.main.item_oceans.*
import kotlinx.android.synthetic.main.item_sea.*

class ResortsAdapter(
    private val onItemClick: (position: Int) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var differ = AsyncListDiffer<Resorts>(this, ResortDiffUtilCallback())

    private val delegatesManager =

    //  устанавливаем количество элементов, равное размеру списка
    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    //  получаем разный ViewType в зависимости от класса элемента
    override fun getItemViewType(position: Int): Int {
        return
    }

    //  выбираем создаваемый ViewHolder в зависимости от полученного ViewType
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return
    }

    //  баиндим Holder'ы в зависимости от их типов
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    }

    class ResortDiffUtilCallback() : DiffUtil.ItemCallback<Resorts>() {
        override fun areItemsTheSame(oldItem: Resorts, newItem: Resorts): Boolean {
            return when {
                oldItem is Resorts.Sea && newItem is Resorts.Sea -> newItem.id == oldItem.id
                oldItem is Resorts.Ocean && newItem is Resorts.Ocean -> newItem.id == oldItem.id
                oldItem is Resorts.Mountain && newItem is Resorts.Mountain -> newItem.id == oldItem.id
                else -> false
            }
        }

        override fun areContentsTheSame(oldItem: Resorts, newItem: Resorts): Boolean {
            return oldItem == newItem
        }

    }

    //  обновление списка
    fun updateResorts(newResorts: List<Resorts>) {
        differ.submitList(newResorts)
    }


    //  константы для получения различных типов viewType
    companion object {
        private const val TYPE_OCEANS = 1
        private const val TYPE_MOUNTAINS = 2
        private const val TYPE_SEAS = 3
    }
}