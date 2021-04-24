package com.skillbox.skillbox.viewhomework

import android.os.Bundle
import androidx.fragment.app.Fragment

class MainFragment : Fragment(R.layout.main_fragment) {
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        childFragmentManager.beginTransaction()
            .add(R.id.mainContainer, ListFragment())
            .commitNow()
    }
}