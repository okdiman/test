package com.skillbox.skillbox.testonlineshop.presentation.adapters.detailsfragment

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.skillbox.skillbox.testonlineshop.domain.models.Product
import com.skillbox.skillbox.testonlineshop.presentation.detailsfragment.DetailsFragment
import com.skillbox.skillbox.testonlineshop.presentation.detailsfragment.ShopDetailsFragment

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
                product.cpu,
                product.camera,
                product.sd,
                product.ssd,
                product.color,
                product.capacity,
                product.price
            )
            else -> Fragment()
        }
    }
}