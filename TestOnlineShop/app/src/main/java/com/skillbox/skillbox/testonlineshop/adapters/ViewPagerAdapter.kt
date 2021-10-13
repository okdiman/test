package com.skillbox.skillbox.testonlineshop.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.skillbox.skillbox.testonlineshop.fragments.EmptyFragment
import com.skillbox.skillbox.testonlineshop.fragments.MainFragment
import com.skillbox.skillbox.testonlineshop.fragments.PhonesFragment

class ViewPagerAdapter(private val types: List<String>, fragment: MainFragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return types.size
    }

    override fun createFragment(position: Int): Fragment {
        return when(types[position]){
            "Phones" -> PhonesFragment()
            else -> EmptyFragment()
        }
    }
}