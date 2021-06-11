package com.skillbox.skillbox.myapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.skillbox.skillbox.myapplication.R
import com.skillbox.skillbox.myapplication.adapters.images.ImagesHorizontalAdapter
import com.skillbox.skillbox.myapplication.classes.Images
import com.skillbox.skillbox.myapplication.databinding.ImageListFragmentBinding

class ImageHorizontalListFragment : Fragment() {
    private var _binding: ImageListFragmentBinding? = null
    private val binding get() = _binding!!

    private var imagesAdapter: ImagesHorizontalAdapter? = null
    private var imagesList = arrayListOf(
        Images(1, R.drawable.ibiza),
        Images(2, R.drawable.red_sea),
        Images(3, R.drawable.greek_sea),
        Images(4, R.drawable.seychelles),
        Images(5, R.drawable.hawaii),
        Images(6, R.drawable.canary),
        Images(7, R.drawable.cortina),
        Images(8, R.drawable.mont_tremblant),
        Images(9, R.drawable.aspen),
        Images(10, R.drawable.chamonix)
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

    //  очищаем баиндинг и адаптер при удалении вьюшки
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        imagesAdapter = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initImageList()
        imagesAdapter?.items = imagesList
    }

    //  инициализируем список
    private fun initImageList() {
        imagesAdapter = ImagesHorizontalAdapter { }
        with(binding.imagesListRV) {
            adapter = imagesAdapter
            layoutManager = LinearLayoutManager(requireContext()).apply {
                orientation = LinearLayoutManager.HORIZONTAL
            }
            setHasFixedSize(true)
        }
    }
}