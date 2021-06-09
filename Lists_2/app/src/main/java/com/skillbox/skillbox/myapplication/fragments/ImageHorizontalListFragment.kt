package com.skillbox.skillbox.myapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.skillbox.skillbox.myapplication.R
import com.skillbox.skillbox.myapplication.adapters.ImagesAdapter
import com.skillbox.skillbox.myapplication.classes.Images
import com.skillbox.skillbox.myapplication.databinding.ImageListFragmentBinding

class ImageHorizontalListFragment : Fragment() {
    private var _binding: ImageListFragmentBinding? = null
    private val binding get() = _binding!!

    private var imagesAdapter: ImagesAdapter? = null
    private var imagesList = arrayListOf(
        Images(R.drawable.ibiza),
        Images(R.drawable.red_sea),
        Images(R.drawable.greek_sea),
        Images(R.drawable.seychelles),
        Images(R.drawable.hawaii),
        Images(R.drawable.canary),
        Images(R.drawable.cortina),
        Images(R.drawable.mont_tremblant),
        Images(R.drawable.aspen),
        Images(R.drawable.chamonix)
    )

    //  используем баиндинг
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ImageListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        imagesAdapter = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initImageList()
        imagesAdapter?.updateImages(imagesList)
        imagesAdapter?.notifyDataSetChanged()
    }

    private fun initImageList() {
        imagesAdapter = ImagesAdapter { }
        with(binding.imagesListRV) {
            adapter = imagesAdapter
            layoutManager = LinearLayoutManager(requireContext()).apply {
                orientation = LinearLayoutManager.HORIZONTAL
            }
            setHasFixedSize(true)
        }
    }
}