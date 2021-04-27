package com.skillbox.skillbox.viewhomework

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import kotlinx.android.synthetic.main.list_fragment.*
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment(R.layout.main_fragment), ItemSelectListener {
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val tabletSize = resources.getBoolean(R.bool.isLandScape)
        if (!tabletSize) {
            childFragmentManager.beginTransaction()
                .add(R.id.mainContainer, ListFragment())
                .commit()
        }
    }

    override fun onItemSelect(text: String, image: Int) {
        choosePageTextView.text = text
        requireFragmentManager().beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .replace(
                R.id.mainContainer,
                DetailFragment.newInstance(choosePageTextView.text.toString(), image)
            )
            .addToBackStack("backToMainScreen")
            .commit()
    }
}