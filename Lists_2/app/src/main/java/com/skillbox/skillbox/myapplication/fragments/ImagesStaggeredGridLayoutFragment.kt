package com.skillbox.skillbox.myapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.skillbox.skillbox.myapplication.R
import com.skillbox.skillbox.myapplication.adapters.images.ImagesStaggeredGridAdapter
import com.skillbox.skillbox.myapplication.classes.Images
import com.skillbox.skillbox.myapplication.databinding.ImageStaggeredGridListFragmentBinding
import kotlin.random.Random

class ImagesStaggeredGridLayoutFragment : Fragment() {
    private var _binding: ImageStaggeredGridListFragmentBinding? = null
    private val binding get() = _binding!!

    private var staggeredGridAdapter: ImagesStaggeredGridAdapter? = null
    private val staggeredGridImagesList = arrayListOf(
        Images(Random.nextLong(), R.drawable.ibiza),
        Images(Random.nextLong(), R.drawable.red_sea),
        Images(Random.nextLong(), R.drawable.greek_sea),
        Images(Random.nextLong(), R.drawable.seychelles),
        Images(Random.nextLong(), R.drawable.hawaii),
        Images(Random.nextLong(), R.drawable.canary),
        Images(Random.nextLong(), R.drawable.cortina),
        Images(Random.nextLong(), R.drawable.mont_tremblant),
        Images(Random.nextLong(), R.drawable.aspen),
        Images(Random.nextLong(), R.drawable.ibiza),
        Images(Random.nextLong(), R.drawable.red_sea),
        Images(Random.nextLong(), R.drawable.greek_sea),
        Images(Random.nextLong(), R.drawable.seychelles),
        Images(Random.nextLong(), R.drawable.hawaii),
        Images(Random.nextLong(), R.drawable.canary),
        Images(Random.nextLong(), R.drawable.cortina),
        Images(Random.nextLong(), R.drawable.mont_tremblant),
        Images(Random.nextLong(), R.drawable.aspen),
        Images(Random.nextLong(), R.drawable.ibiza),
        Images(Random.nextLong(), R.drawable.red_sea),
        Images(Random.nextLong(), R.drawable.greek_sea),
        Images(Random.nextLong(), R.drawable.seychelles),
        Images(Random.nextLong(), R.drawable.hawaii),
        Images(Random.nextLong(), R.drawable.canary),
        Images(Random.nextLong(), R.drawable.cortina),
        Images(Random.nextLong(), R.drawable.mont_tremblant),
        Images(Random.nextLong(), R.drawable.aspen)
    )

    //  используем баиндинг
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ImageStaggeredGridListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        staggeredGridAdapter?.items = staggeredGridImagesList
    }

    //  очищаем баиндинг и адаптер при удалении вьюшки
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        staggeredGridAdapter = null
    }

    //  инициализируем список
    private fun initList() {
        staggeredGridAdapter = ImagesStaggeredGridAdapter { }
        with(binding.imagesStaggeredGridListRV) {
            adapter = staggeredGridAdapter
            layoutManager = StaggeredGridLayoutManager(
                3,
                StaggeredGridLayoutManager.VERTICAL
            )
            setHasFixedSize(true)
//            добавляем разделители
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.HORIZONTAL
                )
            )
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
        }

    }
}