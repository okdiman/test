package com.skillbox.skillbox.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.list_fragment.*

class ListFragment() : Fragment(R.layout.list_fragment) {

    private val resortsList = emptyList<Resorts>()
//    private val resorts = listOf<Resorts>(
//        Resorts.Mountains(
//            name = "Aspen, Colorado",
//            country = "USA",
//            photo = R.drawable.aspen,
//            mountain = "Aspen"
//        ),
//        Resorts.Oceans(
//            name = "Hawaiian Islands",
//            country = "USA",
//            photo = R.drawable.hawaii,
//            ocean = "Pacific ocean"
//        ),
//        Resorts.Mountains(
//            name = "Cortina-d'Ampezzo",
//            country = "Italy",
//            photo = R.drawable.cortina,
//            mountain = "Alps"
//        ),
//        Resorts.Oceans(
//            name = "Seychelles islands",
//            country = "Republic of Seychelles",
//            photo = R.drawable.seychelles,
//            ocean = "Indian ocean"
//        ),
//        Resorts.Mountains(
//            name = "Mont Tremblant",
//            country = "Canada",
//            photo = R.drawable.mont_tremblant,
//            mountain = "Mont Tremblant"
//        ),
//        Resorts.Oceans(
//            name = "Canary islands",
//            country = "Spain",
//            photo = R.drawable.canary,
//            ocean = "Atlantic ocean"
//        ),
//        Resorts.Mountains(
//            name = "Chamonix",
//            country = "France",
//            photo = R.drawable.chamonix,
//            mountain = "Alps"
//        ),
//        Resorts.Seas(
//            name = "Ibiza",
//            country = "Spain",
//            photo = R.drawable.ibiza,
//            sea = "Mediterranean sea"
//        ),
//    )

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        addFab.setOnClickListener {
            addResort()
        }
    }

    private fun addResort (){

    }
}