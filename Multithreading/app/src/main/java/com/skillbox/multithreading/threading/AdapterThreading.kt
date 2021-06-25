package com.skillbox.multithreading.threading

import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.skillbox.multithreading.networking.Movie

class AdapterThreading(onItemClick: (position: Int) -> Unit) : AsyncListDifferDelegationAdapter<Movie>() {

}