package com.skillbox.skillbox.contentprovider.detailfragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.skillbox.skillbox.contentprovider.databinding.DetailFragmentBinding
import kotlinx.android.synthetic.main.view_toolbar.*


class DetailFragment : Fragment() {
    private var _binding: DetailFragmentBinding? = null
    private val binding get() = _binding!!

    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        initContact()
    }

    private fun initToolbar() {
        toolbar.title = "Информация о контакте"
    }

    private fun initContact() {
        binding.nameOfContactDetailTextView.text = args.contact.name
        binding.phonesOfContactDetailTextView.text = args.contact.phones.joinToString ( "\n" )
        binding.emailsOfContactDetailTextView.text = args.contact.eMails?.joinToString ( "\n" )
    }
}