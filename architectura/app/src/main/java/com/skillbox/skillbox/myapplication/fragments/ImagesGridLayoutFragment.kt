package com.skillbox.skillbox.myapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.skillbox.skillbox.myapplication.R
import com.skillbox.skillbox.myapplication.adapters.images.ImagesGridAdapter
import com.skillbox.skillbox.myapplication.classes.ImagesForLists
import com.skillbox.skillbox.myapplication.classes.ItemOffsetDecoration
import com.skillbox.skillbox.myapplication.databinding.ImageGridListFragmentBinding
import kotlin.random.Random

class ImagesGridLayoutFragment : Fragment() {
    private var _binding: ImageGridListFragmentBinding? = null
    private val binding get() = _binding!!

    private var gridImagesAdapter: ImagesGridAdapter? = null
    private var gridImages = arrayListOf(
        ImagesForLists(Random.nextLong(), R.drawable.ibiza),
        ImagesForLists(Random.nextLong(), R.drawable.red_sea),
        ImagesForLists(Random.nextLong(), R.drawable.greek_sea),
        ImagesForLists(Random.nextLong(), R.drawable.seychelles),
        ImagesForLists(Random.nextLong(), R.drawable.hawaii),
        ImagesForLists(Random.nextLong(), R.drawable.canary),
        ImagesForLists(Random.nextLong(), R.drawable.cortina),
        ImagesForLists(Random.nextLong(), R.drawable.mont_tremblant),
        ImagesForLists(Random.nextLong(), R.drawable.aspen),
        ImagesForLists(Random.nextLong(), R.drawable.red_sea),
        ImagesForLists(Random.nextLong(), R.drawable.greek_sea),
        ImagesForLists(Random.nextLong(), R.drawable.seychelles),
        ImagesForLists(Random.nextLong(), R.drawable.hawaii),
        ImagesForLists(Random.nextLong(), R.drawable.canary),
        ImagesForLists(Random.nextLong(), R.drawable.cortina),
        ImagesForLists(Random.nextLong(), R.drawable.mont_tremblant),
        ImagesForLists(Random.nextLong(), R.drawable.aspen),
        ImagesForLists(Random.nextLong(), R.drawable.ibiza)
    )

    //  используем баиндинг
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ImageGridListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    //  очищаем баиндинг и адаптер при удалении вьюшки
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        gridImagesAdapter = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        gridImagesAdapter?.items = gridImages
    }

    //  инициализируем список
    private fun initList() {
        gridImagesAdapter = ImagesGridAdapter { }
        with(binding.imagesGridListRV) {
            adapter = gridImagesAdapter
            layoutManager = GridLayoutManager(requireContext(), 4).apply {
//                используем SpanSizeLookup
                spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return if (position % 3 == 0) 2 else 1
                    }
                }
            }
            setHasFixedSize(true)
//            добавляем отступы
            addItemDecoration(ItemOffsetDecoration(context))
        }
    }
}