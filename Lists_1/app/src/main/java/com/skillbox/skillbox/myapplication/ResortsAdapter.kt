package com.skillbox.skillbox.myapplication

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ResortsAdapter(
    private val onItemClick: (position: Int) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var resorts = emptyList<Resorts>()

    //  устанавливаем количество элементов, равное размеру списка
    override fun getItemCount(): Int {
        return resorts.size
    }

    //  получаем разный ViewType в зависимости от класса элемента
    override fun getItemViewType(position: Int): Int {
        return when (resorts[position]) {
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
                val resort = resorts[position].let { it as? Resorts.Ocean }
                    ?: error("resort at position $position isn't a ocean")
                holder.bind(resort)
            }
            is MountainHolder -> {
                val resort = resorts[position].let { it as? Resorts.Mountain }
                    ?: error("resort at position $position isn't a mountain")
                holder.bind(resort)
            }
            is SeaHolder -> {
                val resort = resorts[position].let { it as? Resorts.Sea }
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

        //  обработка клика по элементу
        init {
            view.setOnClickListener {
                onItemClick(adapterPosition)
            }
        }

        //  общий набор данных для обоих View Holder
        protected fun baseBindInfo(
            name: String?,
            country: String?
        ) {
            nameTextView.text = name
            countryTextView.text = country
        }
    }

    //  создаем 3 разных холдера для разных классов элементов
    class OceanHolder(
        view: View,
        onItemClick: (position: Int) -> Unit
    ) : BaseViewHolder(view, onItemClick) {
        private val oceanTextView = view.findViewById<TextView>(R.id.oceanTextView)
        private val photoImageView = view.findViewById<ImageView>(R.id.photoImageView)
        fun bind(ocean: Resorts.Ocean) {
            Glide.with(itemView)
                .load(ocean.photo)
                .error(R.drawable.ic_sync_problem)
                .placeholder(R.drawable.ic_cloud_download)
                .into(photoImageView)
            oceanTextView.text = ocean.ocean
            baseBindInfo(ocean.name, ocean.country)
        }
    }

    class SeaHolder(
        view: View,
        onItemClick: (position: Int) -> Unit
    ) : BaseViewHolder(view, onItemClick) {
        private val seaTextView = view.findViewById<TextView>(R.id.seaTextView)
        private val photoImageView = view.findViewById<ImageView>(R.id.photoImageView)
        fun bind(sea: Resorts.Sea) {
            Glide.with(itemView)
                .load(sea.photo)
                .error(R.drawable.ic_sync_problem)
                .placeholder(R.drawable.ic_cloud_download)
                .into(photoImageView)
            seaTextView.text = sea.sea
            baseBindInfo(sea.name, sea.country)
        }
    }

    class MountainHolder(
        view: View,
        onItemClick: (position: Int) -> Unit
    ) : BaseViewHolder(view, onItemClick) {
        private val mountainTextView = view.findViewById<TextView>(R.id.mountainTextView)
        private val photoImageView = view.findViewById<ImageView>(R.id.photoImageView)
        fun bind(mountain: Resorts.Mountain) {
            Glide.with(itemView)
                .load(mountain.photo)
                .error(R.drawable.ic_sync_problem)
                .placeholder(R.drawable.ic_cloud_download)
                .into(photoImageView)
            mountainTextView.text = mountain.mountain
            baseBindInfo(mountain.name, mountain.country)
        }
    }

    //  константы для получения различных типов viewType
    companion object {
        private const val TYPE_OCEANS = 1
        private const val TYPE_MOUNTAINS = 2
        private const val TYPE_SEAS = 3
    }
}