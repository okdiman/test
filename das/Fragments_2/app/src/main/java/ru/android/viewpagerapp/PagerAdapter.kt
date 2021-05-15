package ru.android.viewpagerapp

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.android.viewpagerapp.data.PagerScreenData
import ru.android.viewpagerapp.fragments.PagerScreen

class PagerAdapter(
    private val screenData: List<PagerScreenData>,
    fragment: Fragment
) : FragmentStateAdapter(fragment) {


    override fun getItemCount(): Int {
        return screenData.size
    }

    override fun createFragment(position: Int): Fragment {
        val screenData: PagerScreenData = screenData[position]

        return PagerScreen.newInstance(
            stringRes = screenData.topicText,
            drawableRes = screenData.topicImage,
        )
    }
}