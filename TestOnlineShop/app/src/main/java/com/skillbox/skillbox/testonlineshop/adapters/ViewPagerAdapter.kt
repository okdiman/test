package com.skillbox.skillbox.testonlineshop.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.skillbox.skillbox.testonlineshop.classes.TypesOfProducts
import com.skillbox.skillbox.testonlineshop.fragments.mainscreen.MainFragment
import com.skillbox.skillbox.testonlineshop.fragments.mainscreen.PhonesFragment

class ViewPagerAdapter(private val types: List<TypesOfProducts>, fragment: MainFragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return types.size
    }

    override fun createFragment(position: Int): Fragment {
//        так как сейчас требуется реализовать только 1 фрагмент,
//        при выборе другого поля в tabLayout возвращаем пустой фрагмент
        return when(types[position]){
            TypesOfProducts.PHONES -> PhonesFragment()
            else -> Fragment()
        }
    }
}