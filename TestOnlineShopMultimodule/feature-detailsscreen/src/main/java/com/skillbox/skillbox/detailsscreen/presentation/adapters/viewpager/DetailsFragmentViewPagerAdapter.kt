package com.skillbox.skillbox.detailsscreen.presentation.adapters.viewpager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.skillbox.skillbox.detailsscreen.domain.entities.Product
import com.skillbox.skillbox.detailsscreen.presentation.screens.DetailsFragment
import com.skillbox.skillbox.testonlineshop.features.detail.presentation.screens.ShopDetailsFragment

class DetailsFragmentViewPagerAdapter(
    private val menuItems: List<String>,
    private val product: Product,
    fragment: DetailsFragment
) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return menuItems.size
    }

    override fun createFragment(position: Int): Fragment {
//        так как сейчас требуется реализовать только 1 фрагмент,
//        при выборе другого поля в tabLayout возвращаем пустой фрагмент
        return when (menuItems[position]) {
//            при открытии вкладки shop создаем инстанс Shop фрагмента
            "Shop" -> ShopDetailsFragment.newInstance(
                product
            )
            else -> Fragment()
        }
    }
}