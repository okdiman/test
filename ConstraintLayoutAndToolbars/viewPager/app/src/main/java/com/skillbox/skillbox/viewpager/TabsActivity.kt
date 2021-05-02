package com.skillbox.skillbox.viewpager

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_tabs.*

class TabsActivity: AppCompatActivity() {
    private val screens: List <OnboardingScreen> = listOf(
        OnboardingScreen(
            textRes = R.string.onBoard1,
            bgColorRes = R.color.purple_200,
            drawableRes = R.drawable.dinamo_moskva
        ),
        OnboardingScreen(
            textRes = R.string.onBoard2,
            bgColorRes = R.color.purple_500,
            drawableRes = R.drawable.dortmund
        ),
        OnboardingScreen(
            textRes = R.string.onBoard3,
            bgColorRes = R.color.design_default_color_secondary,
            drawableRes = R.drawable.real
        ),
        OnboardingScreen(
            textRes = R.string.onBoard4,
            bgColorRes = R.color.design_default_color_error,
            drawableRes = R.drawable.atletico
        ),
        OnboardingScreen(
            textRes = R.string.onBoard5,
            bgColorRes = R.color.black,
            drawableRes = R.drawable.liver
        )
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tabs)

        val adapter = OnboardingAdapter(screens + screens, this)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) {
            tab, position -> tab.text = "Tab ${position + 1}"
            if (position == 0) {
                tab.setIcon(R.drawable.dinamo_moskva)
            }
        }.attach()

        tabLayout.getTabAt(1)?.orCreateBadge?.apply {
            number = 2
            badgeGravity = BadgeDrawable.TOP_END
        }

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tabLayout.getTabAt(position)?.removeBadge()
            }
        }
        )
    }
}