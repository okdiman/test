package com.skillbox.skillbox.myapplication

import androidx.fragment.app.Fragment

class ListFragment() : Fragment(R.layout.list_fragment) {
    private val resorts = listOf<Resorts>(
        Resorts.mountains(
            name = "Aspen, Colorado",
            country = "USA",
            photo = R.drawable.aspen,
            mountain = "Aspen"
        ),
        Resorts.oceans(
            name = "Hawaiian Islands",
            country = "USA",
            photo = R.drawable.hawaii,
            ocean = "Pacific ocean"
        ),
        Resorts.mountains(
            name = "Cortina-d'Ampezzo",
            country = "Italy",
            photo = R.drawable.cortina,
            mountain = "Alps"
        ),
        Resorts.oceans(
            name = "Seychelles islands",
            country = "Republic of Seychelles",
            photo = R.drawable.seychelles,
            ocean = "Indian ocean"
        ),
        Resorts.mountains(
            name = "Mont Tremblant",
            country = "Canada",
            photo = R.drawable.mont_tremblant,
            mountain = "Mont Tremblant"
        ),
        Resorts.oceans(
            name = "Canary islands",
            country = "Spain",
            photo = R.drawable.canary,
            ocean = "Atlantic ocean"
        ),
        Resorts.mountains(
            name = "Chamonix",
            country = "France",
            photo = R.drawable.chamonix,
            mountain = "Alps"
        ),
        Resorts.seas(
            name = "Ibiza",
            country = "Spain",
            photo = R.drawable.ibiza,
            sea = "Mediterranean sea"
        ),
    )
}