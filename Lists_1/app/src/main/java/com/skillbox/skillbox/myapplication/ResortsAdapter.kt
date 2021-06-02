package com.skillbox.skillbox.myapplication

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ResortsAdapter(
    private val onItemClick: (position: Int) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var resorts = emptyList<Resorts>()

    // устанавливаем количество элементов, равное размеру списка
    override fun getItemCount(): Int {
        return resorts.size
    }

    // получаем разный ViewType в зависимости от класса элемента
    override fun getItemViewType(position: Int): Int {
        return when (resorts[position]) {
            is Resorts.Mountains -> TYPE_MOUNTAINS
            is Resorts.Seas -> TYPE_SEAS
            is Resorts.Oceans -> TYPE_OCEANS
        }
    }

    // выбираем создаваемый ViewHolder в зависимости от полученного ViewType
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
                val resort = resorts[position].let { it as? Resorts.Oceans }
                    ?: error("resort at position $position isn't a ocean")
                holder.bind(resort)
            }
            is MountainHolder -> {
                val resort = resorts[position].let { it as? Resorts.Mountains }
                    ?: error("resort at position $position isn't a mountain")
                holder.bind(resort)
            }
            is SeaHolder -> {
                val resort = resorts[position].let { it as? Resorts.Seas }
                    ?: error("resort at position $position isn't a sea")
                holder.bind(resort)
            }
        }
    }

    //  обновление списка
    fun updateResorts(newResorts: List<Resorts>) {
        resorts = newResorts
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

        // общий набор данных для обоих View Holder
        protected fun baseBindInfo(
            name: String,
            country: String,
            photo: Int
        ) {
            nameTextView.text = name
            countryTextView.text = country
            photoImageView.setImageResource(photo)
        }
    }

    // создаем 3 разных холдера для разных классов элементов
    class OceanHolder(
        view: View,
        onItemClick: (position: Int) -> Unit
    ) : BaseViewHolder(view, onItemClick) {
        private val oceanTextView = view.findViewById<TextView>(R.id.oceanTextView)
        fun bind(ocean: Resorts.Oceans) {
            oceanTextView.text = ocean.ocean
            baseBindInfo(ocean.name, ocean.country, ocean.photo)
        }
    }

    class SeaHolder(
        view: View,
        onItemClick: (position: Int) -> Unit
    ) : BaseViewHolder(view, onItemClick) {
        private val seaTextView = view.findViewById<TextView>(R.id.seaTextView)
        fun bind(sea: Resorts.Seas) {
            seaTextView.text = sea.sea
            baseBindInfo(sea.name, sea.country, sea.photo)
        }
    }

    class MountainHolder(
        view: View,
        onItemClick: (position: Int) -> Unit
    ) : BaseViewHolder(view, onItemClick) {
        private val mountainTextView = view.findViewById<TextView>(R.id.mountainTextView)
        fun bind(mountain: Resorts.Mountains) {
            mountainTextView.text = mountain.mountain
            baseBindInfo(mountain.name, mountain.country, mountain.photo)
        }
    }

    //    константы для получения различных типов viewType
    companion object {
        private const val TYPE_OCEANS = 1
        private const val TYPE_MOUNTAINS = 2
        private const val TYPE_SEAS = 3
    }
}