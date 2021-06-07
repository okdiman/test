package com.skillbox.skillbox.myapplication.adapters

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.skillbox.skillbox.myapplication.R
import com.skillbox.skillbox.myapplication.Resorts
import com.skillbox.skillbox.myapplication.inflate

class ResortsAdapter(
    private val onItemClick: (position: Int) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val differ = AsyncListDiffer<Resorts>(this, ResortDiffUtilCallBack())

    //  устанавливаем количество элементов, равное размеру списка
    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    //  получаем разный ViewType в зависимости от класса элемента
    override fun getItemViewType(position: Int): Int {
        return when (differ.currentList[position]) {
            is Resorts.Mountain -> TYPE_MOUNTAINS
            is Resorts.Sea -> TYPE_SEAS
            is Resorts.Ocean -> TYPE_OCEANS
        }
    }

    //  выбираем создаваемый ViewHolder в зависимости от полученного ViewType
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_OCEANS -> OceanHolder(parent.inflate(R.layout.item_oceans), onItemClick)
            TYPE_SEAS -> SeaHolder(parent.inflate(R.layout.item_sea), onItemClick)
            TYPE_MOUNTAINS -> MountainHolder(parent.inflate(R.layout.item_mountain), onItemClick)
            else -> error("Incorrect ViewType = $viewType")
        }
    }
//  баиндим Holder'ы в зависимости от их типов
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is OceanHolder -> {
                val resort = differ.currentList[position].let { it as? Resorts.Ocean }
                    ?: error("resort at position $position isn't a ocean")
                holder.bind(resort)
            }
            is MountainHolder -> {
                val resort = differ.currentList[position].let { it as? Resorts.Mountain }
                    ?: error("resort at position $position isn't a mountain")
                holder.bind(resort)
            }
            is SeaHolder -> {
                val resort = differ.currentList[position].let { it as? Resorts.Sea }
                    ?: error("resort at position $position isn't a sea")
                holder.bind(resort)
            }
        }
    }

    //  обновление списка
    fun updateResorts(newResorts: List<Resorts>) {
        differ.submitList(newResorts)
    }

    class ResortDiffUtilCallBack(): DiffUtil.ItemCallback<Resorts>(){
        override fun areItemsTheSame(oldItem: Resorts, newItem: Resorts): Boolean {
            return when{
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

    //  создаем общий Холдер
    abstract class BaseViewHolder(
        view: View,
        onItemClick: (position: Int) -> Unit
    ) : RecyclerView.ViewHolder(view) {
        private val nameTextView = view.findViewById<TextView>(R.id.nameTextView)
        private val countryTextView = view.findViewById<TextView>(R.id.countryTextView)
        private val photoImageView = view.findViewById<ImageView>(R.id.photoImageView)

        //  обработка клика по элементу
        init {
            view.setOnClickListener {
                onItemClick(adapterPosition)
            }
        }

        //  общий набор данных для обоих View Holder
        protected fun baseBindInfo(
            name: String?,
            country: String?,
            photo: Int
        ) {
            nameTextView.text = name
            countryTextView.text = country
            photoImageView.setImageResource(photo)
        }
    }

    //  создаем 3 разных холдера для разных классов элементов
    class OceanHolder(
        view: View,
        onItemClick: (position: Int) -> Unit
    ) : BaseViewHolder(view, onItemClick) {
        private val oceanTextView = view.findViewById<TextView>(R.id.oceanTextView)
        fun bind(ocean: Resorts.Ocean) {
            oceanTextView.text = ocean.ocean
            baseBindInfo(ocean.name, ocean.country, ocean.photo)
        }
    }

    class SeaHolder(
        view: View,
        onItemClick: (position: Int) -> Unit
    ) : BaseViewHolder(view, onItemClick) {
        private val seaTextView = view.findViewById<TextView>(R.id.seaTextView)
        fun bind(sea: Resorts.Sea) {
            seaTextView.text = sea.sea
            baseBindInfo(sea.name, sea.country, sea.photo)
        }
    }

    class MountainHolder(
        view: View,
        onItemClick: (position: Int) -> Unit
    ) : BaseViewHolder(view, onItemClick) {
        private val mountainTextView = view.findViewById<TextView>(R.id.mountainTextView)
        fun bind(mountain: Resorts.Mountain) {
            mountainTextView.text = mountain.mountain
            baseBindInfo(mountain.name, mountain.country, mountain.photo)
        }
    }

    //  константы для получения различных типов viewType
    companion object {
        private const val TYPE_OCEANS = 1
        private const val TYPE_MOUNTAINS = 2
        private const val TYPE_SEAS = 3
    }
}