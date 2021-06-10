package com.skillbox.skillbox.myapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.skillbox.skillbox.myapplication.R
import com.skillbox.skillbox.myapplication.adapters.ImagesGridAdapter
import com.skillbox.skillbox.myapplication.classes.Images
import com.skillbox.skillbox.myapplication.classes.ItemOffsetDecoration
import com.skillbox.skillbox.myapplication.databinding.ImageGridListFragmentBinding

class ImagesGridLayoutFragment : Fragment() {
    private var _binding: ImageGridListFragmentBinding? = null
    private val binding get() = _binding!!

    private var gridImagesAdapter: ImagesGridAdapter? = null
    private var gridImages = arrayListOf(
        Images(R.drawable.ibiza),
        Images(R.drawable.red_sea),
        Images(R.drawable.greek_sea),
        Images(R.drawable.seychelles),
        Images(R.drawable.hawaii),
        Images(R.drawable.canary),
        Images(R.drawable.cortina),
        Images(R.drawable.mont_tremblant),
        Images(R.drawable.aspen)
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ImageGridListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        gridImagesAdapter = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        gridImagesAdapter?.updateImages(gridImages)
        gridImagesAdapter?.notifyDataSetChanged()
    }

    private fun initList() {
        gridImagesAdapter = ImagesGridAdapter { }
        with(binding.imagesGridListRV) {
            adapter = gridImagesAdapter
            layoutManager = GridLayoutManager(requireContext(), 4).apply {
                spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return if (position % 3 == 0) 2 else 1
                    }
                }
            }
            setHasFixedSize(true)
            addItemDecoration(ItemOffsetDecoration(context))
        }
    }
}