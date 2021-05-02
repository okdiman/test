package com.skillbox.skillbox.viewpager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
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
        setContentView(R.layout.activity_main)

        val adapter = OnboardingAdapter(screens, this)
        viewPager.adapter = adapter
    }
}