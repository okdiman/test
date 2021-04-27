package com.skillbox.skillbox.viewhomework

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.list_fragment.*

class ListFragment : Fragment(R.layout.list_fragment) {

    private val itemSelectListener: ItemSelectListener?
        get() = parentFragment.let { it as? ItemSelectListener }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        view.let { it as ViewGroup }
//            .children
//            .mapNotNull { it as? TextView }
//            .forEach { textView ->
//                textView.setOnClickListener {
//                    onTextClick(textView.text.toString(), R.drawable.dinamo_moskva)
//                }
//            }
        Dinamo.setOnClickListener {
            onTextClick(Dinamo.text.toString(), R.drawable.dinamo_moskva)
        }

        LiverpooTextView.setOnClickListener {
            onTextClick(LiverpooTextView.text.toString(), R.drawable.liver)
        }

        RealTextView.setOnClickListener {
            onTextClick(RealTextView.text.toString(), R.drawable.real)
        }

        BayernTextView.setOnClickListener {
            onTextClick(BayernTextView.text.toString(), R.drawable.bavaria)
        }

        BDTextView.setOnClickListener {
            onTextClick(BDTextView.text.toString(), R.drawable.dortmund)
        }

        MilanTextView.setOnClickListener {
            onTextClick(MilanTextView.text.toString(), R.drawable.milan)
        }

        MUTextView.setOnClickListener {
            onTextClick(MUTextView.text.toString(), R.drawable.mu)
        }

        AtleticoTextView.setOnClickListener {
            onTextClick(AtleticoTextView.text.toString(), R.drawable.atletico)
        }
    }

    private fun onTextClick(text: String, image: Int) {
        itemSelectListener?.onItemSelect(text, image)
    }
}
