package com.skillbox.skillbox.viewhomework

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Button
import androidx.core.view.children
import androidx.fragment.app.Fragment

class ListFragment : Fragment(R.layout.fragment_list) {

    private val itemSelectListener: ItemSeleckListener?
            get() = activity.let { it as? ItemSeleckListener }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        view.let { it as ViewGroup }
            .children
            .mapNotNull { it as? Button }
            .forEach { button ->
                button.setOnClickListener {
                    onButtonClick(button.text.toString())
                }

            }
    }

    private fun onButtonClick(buttonText: String) {
        Log.d("listFragment", "onButtonClick $buttonText")
        itemSelectListener?.onItemSelected(buttonText)
    }
}