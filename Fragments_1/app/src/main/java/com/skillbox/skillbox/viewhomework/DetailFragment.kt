package com.skillbox.skillbox.viewhomework

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.detail_fragment.*

class DetailFragment : Fragment(R.layout.detail_fragment) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        welcomeTextView.text = requireArguments().getString(KEY_CLUB)
        iconImageView.setImageResource(requireArguments().getInt(KEY_ICON))
    }

    companion object {
        private const val KEY_CLUB = "key_club"
        private const val KEY_ICON = "key_icon"
        fun newInstance(text: String, image: Int): DetailFragment {
            return DetailFragment().withArguments {
                putString(KEY_CLUB, text)
                putInt(KEY_ICON, image)
            }
        }
    }
}