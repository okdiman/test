package com.skillbox.skillbox.viewhomework

import android.os.Bundle
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.children
import androidx.fragment.app.Fragment

class ListFragment : Fragment(R.layout.list_fragment) {

    private val itemSelectListener: ItemSelectListener?
        get() = activity.let { it as? ItemSelectListener }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        view.let { it as ViewGroup }
            .children
            .mapNotNull { it as? TextView }
            .forEach { textView ->
                textView.setOnClickListener {
                    onTextClick(textView.text.toString())
                }
            }
    }
    private fun onTextClick (text: String) {
        itemSelectListener?.onItemSelect(text)
    }
}
