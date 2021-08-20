package com.skillbox.skillbox.contentprovideraddapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.skillbox.skillbox.contentprovideraddapp.databinding.MainFragmentBinding

class MainFragment : Fragment() {

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!


    private val viewModel: MainFragmentViewModel by viewModels()

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
        binding.saveNewCourseButton.setOnClickListener {
            saveCourse(
                binding.enterCourseTitleEditText.text.toString()
            )
        }
        bindViewModel()
    }

    private fun saveCourse(title: String) {
        viewModel.addNewCourse(title)
    }

    private fun getAllCourses() {
        viewModel.getAllCourses()
    }

    //    баиндим ViewModel
    private fun bindViewModel() {
        viewModel.courseList.observe(viewLifecycleOwner) { listOfContacts ->

        }
//        выбрасываем тост с ошибкой в случае ошибки
        viewModel.isError.observe(viewLifecycleOwner) { error ->
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        }
    }
}