package com.skillbox.skillbox.contentprovider.detailfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.skillbox.skillbox.contentprovider.databinding.DetailFragmentBinding
import kotlinx.android.synthetic.main.view_toolbar.*


class DetailFragment : Fragment() {
    private var _binding: DetailFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DetailInfoViewModel by viewModels()

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
        initContactStartScreen()
        bindViewModel()
        binding.deleteContactActionButton.setOnClickListener {
            deleteContact()
        }
    }


    private fun initContactStartScreen() {
        toolbar.title = "Информация о контакте"
        viewModel.getContactData(args.contact.id, args.contact.name)
    }

    private fun bindViewModel() {
        viewModel.contact.observe(viewLifecycleOwner) { contact ->
            binding.nameOfContactDetailTextView.text = contact.name
            binding.phonesOfContactDetailTextView.text = contact.phones.joinToString("\n")
            binding.emailsOfContactDetailTextView.text = contact.eMails.joinToString("\n")
        }
    }

    private fun deleteContact(){

    }
}