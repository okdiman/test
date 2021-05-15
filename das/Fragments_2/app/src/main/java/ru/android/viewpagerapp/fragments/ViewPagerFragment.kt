package ru.android.viewpagerapp.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import ru.android.viewpagerapp.PagerAdapter
import ru.android.viewpagerapp.R
import ru.android.viewpagerapp.data.PagerScreenData
import ru.android.viewpagerapp.databinding.FragmentViewpagerBinding
import ru.android.viewpagerapp.interfaces.PageActionInterface
import kotlin.math.abs
import kotlin.random.Random
import ru.android.viewpagerapp.data.ArticleTag

class ViewPagerFragment : Fragment(), PageActionInterface {

    private var _binding: FragmentViewpagerBinding? = null
    private val binding get() = _binding!!

    private lateinit var pagerAdapter: PagerAdapter
    private lateinit var viewPager: ViewPager2
    private var alertDialog: AlertDialog? = null


    private val screenData: List<PagerScreenData> = PagerScreenData.getListOfScreenData()
    private var selectedTags: Array<ArticleTag> = ArticleTag.values()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewpagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        // restore filter
        if(savedInstanceState?.getIntArray(TAG_KEY) != null) {
            val restoreTags: IntArray = savedInstanceState.getIntArray(TAG_KEY)!!
            selectedTags = restoreTags.map { ArticleTag.fromInt(it) }.toTypedArray()
            filterArticles(selectedTags.toList())
        } else {
            attachAdapter(screenData)
        }

        initViewPager()

        // add click listener to filter button (toolbar menu)
        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.filterAction -> {
                    showFilterDialog()
                    true
                }
                else -> false
            }
        }

        // restore badges
        if(savedInstanceState?.getIntArray(BADGES_KEY) != null) {
            val restoreBadges: IntArray = savedInstanceState.getIntArray(BADGES_KEY)!!
            setBadges(restoreBadges)
        }
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val savedBadges = getBadges()
        outState.putIntArray(BADGES_KEY, savedBadges)
        outState.putIntArray(TAG_KEY, selectedTags.map { it.ordinal }.toIntArray())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        alertDialog?.dismiss()
    }

    // set view pager settings
    private fun initViewPager() {

        // make animation
        viewPager.setPageTransformer { page, position ->
            val minScale = 0.85f
            val minAlpha = 0.5f
            val pageWidth = page.width
            val pageHeight = page.height
            when {
                position < -1 -> { // [-Infinity,-1)
                    page.alpha = 0f
                }
                position <= 1 -> { // [-1,1]
                    val scaleFactor = minScale.coerceAtLeast(1 - abs(position))
                    val vertMargin = pageHeight * (1 - scaleFactor) / 2
                    val horzMargin = pageWidth * (1 - scaleFactor) / 2
                    page.translationX = if (position < 0) {
                        horzMargin - vertMargin / 2
                    } else {
                        horzMargin + vertMargin / 2
                    }
                    page.scaleX = scaleFactor
                    page.scaleY = scaleFactor
                    page.alpha = (minAlpha +
                            (((scaleFactor - minScale) / (1 - minScale)) * (1 - minAlpha)))
                }
                else -> { // (1,+Infinity]
                    page.alpha = 0f
                }
            }
        }

        // add page selected listener
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.tabLayout.getTabAt(position)?.removeBadge()
            }
        })
    }

    // show Multi Choice dialog
    private fun showFilterDialog() {
        ArticleDialog.newInstance(selectedTags).show(
            childFragmentManager, ArticleDialog.TAG
        )
    }

    // attach screen data to viewpager
    private fun attachAdapter(filterScreen: List<PagerScreenData>) {
        // init variables
        pagerAdapter = PagerAdapter(filterScreen, this)
        viewPager = binding.mainViewPager

        // attach adapter
        viewPager.adapter = pagerAdapter

        // attach tab layout
        TabLayoutMediator(binding.tabLayout, binding.mainViewPager) { tab, position ->
            tab.text = getString(filterScreen[position].topicTitle)
        }.attach()

        // attach dots indicator
        binding.dotsIndicator.setViewPager2(viewPager)
    }

    // get all badges
    private fun getBadges(): IntArray {
        val tabSize = binding.tabLayout.tabCount
        val badges = IntArray(tabSize)
        for (i in 0.until(tabSize)) {
            badges[i] = binding.tabLayout.getTabAt(i)?.badge?.number ?: 0
        }

        return badges
    }

    // set badges from int array
    private fun setBadges(badges: IntArray) {
        val tabSize = binding.tabLayout.tabCount
        for (i in 0.until(tabSize)) {
            if(badges.size <= i)
                break

            if(badges[i] == 0)
                continue

           binding.tabLayout.getTabAt(i)?.orCreateBadge?.apply {
               number = badges[i]
           }
        }
    }

    // generate badge function
    override fun generateBadge() {
        val tabSize = binding.tabLayout.tabCount

        binding.tabLayout.getTabAt(Random.nextInt(tabSize))
            ?.orCreateBadge?.apply {
                number += 1
            }
    }

    // filter screen data by tags
    override fun filterArticles(tags: List<ArticleTag>) {

        selectedTags = tags.toTypedArray()

        val filteredScreen = mutableSetOf<PagerScreenData>()

        screenData.forEach { data ->
            data.tags.forEach { tag ->
                if (tags.contains(tag)) {
                    filteredScreen.add(data)
                }
            }
        }


        if (filteredScreen.isEmpty()) {
            alertDialog = AlertDialog.Builder(requireContext())
                .setTitle("Nothing to show")
                .setMessage("Couldn't find any relevant news")
                .show()


            selectedTags = ArticleTag.values()
            attachAdapter(screenData)

        } else {
            attachAdapter(filteredScreen.toList())
        }
    }

    companion object {
        const val BADGES_KEY = "badges"
        const val TAG_KEY = "tags"
    }
}