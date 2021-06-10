package com.skillbox.skillbox.myapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.skillbox.skillbox.myapplication.R
import com.skillbox.skillbox.myapplication.adapters.ImagesAdapter
import com.skillbox.skillbox.myapplication.classes.Images
import com.skillbox.skillbox.myapplication.databinding.ImageListFragmentBinding

class ImagesStaggeredGridLayoutFragment : Fragment() {
    private var _binding: ImageListFragmentBinding? = null
    private val binding get() = _binding!!

    private var staggeredGridAdapter: ImagesAdapter? = null
    private val staggeredGridImagesList = arrayListOf(
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
        _binding = ImageListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        staggeredGridAdapter?.updateImages(staggeredGridImagesList)
        staggeredGridAdapter?.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        staggeredGridAdapter = null
    }

    private fun initList() {
        staggeredGridAdapter = ImagesAdapter { }
        with(binding.imagesListRV) {
            adapter = staggeredGridAdapter
            layoutManager = StaggeredGridLayoutManager(
                3,
                StaggeredGridLayoutManager.HORIZONTAL
            )
            setHasFixedSize(true)
        }
    }
}