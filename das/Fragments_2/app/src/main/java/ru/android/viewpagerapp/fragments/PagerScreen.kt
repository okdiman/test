package ru.android.viewpagerapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import ru.android.viewpagerapp.databinding.FragmentPagerBinding
import ru.android.viewpagerapp.interfaces.PageActionInterface
import ru.android.viewpagerapp.withArguments

class PagerScreen : Fragment() {
    private var _binding: FragmentPagerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPagerBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.pageImageView.setImageResource(requireArguments().getInt(KEY_DRAWABLE))
        binding.pageTextView.setText(requireArguments().getInt(KEY_STRING))
        binding.badgeGeneratorButton.setOnClickListener {
            (parentFragment as? PageActionInterface)?.generateBadge()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    companion object {

        private const val KEY_STRING = "pager.string.res"
        private const val KEY_DRAWABLE = "pager.drawable.res"

        fun newInstance(
            @StringRes stringRes: Int,
            @DrawableRes drawableRes: Int
        ): PagerScreen {
            return PagerScreen().withArguments {
                putInt(KEY_STRING, stringRes)
                putInt(KEY_DRAWABLE, drawableRes)
            }

        }

    }


}