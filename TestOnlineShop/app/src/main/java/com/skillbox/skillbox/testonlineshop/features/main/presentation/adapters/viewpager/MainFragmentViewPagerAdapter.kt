package com.skillbox.skillbox.testonlineshop.features.main.presentation.adapters.viewpager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.skillbox.skillbox.testonlineshop.features.main.domain.entities.TypesOfProducts
import com.skillbox.skillbox.testonlineshop.features.main.presentation.screens.MainFragment
import com.skillbox.skillbox.testonlineshop.features.main.presentation.screens.PhonesFragment

class MainFragmentViewPagerAdapter(private val types: List<TypesOfProducts>, fragment: MainFragment)
    : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return types.size
    }

    override fun createFragment(position: Int): Fragment {
//        так как сейчас требуется реализовать только 1 фрагмент,
//        при выборе другого поля в tabLayout возвращаем пустой фрагмент
        return when(types[position]){
            TypesOfProducts.Phones -> PhonesFragment()
            else -> Fragment()
        }
    }
}