package com.skillbox.skillbox.contentprovider.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.skillbox.skillbox.contentprovider.databinding.MainFragmentBinding


class MainFragment : Fragment() {
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    //    обнуляем баиндинг и адаптер при закрытии фрагмента
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.contactsButton.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToContactsListFragment())
        }
        binding.shareFileButton.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToShareFileFragment())
        }
    }
}