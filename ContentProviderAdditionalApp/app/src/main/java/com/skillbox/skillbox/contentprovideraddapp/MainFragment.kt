package com.skillbox.skillbox.contentprovideraddapp

import android.annotation.SuppressLint
import android.content.ContentValues
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.skillbox.skillbox.contentprovider.inflate
import com.skillbox.skillbox.contentprovideraddapp.course.CourseListAdapter
import com.skillbox.skillbox.contentprovideraddapp.databinding.MainFragmentBinding
import kotlinx.android.synthetic.main.course_item.view.*
import kotlinx.android.synthetic.main.update_view.view.*

class MainFragment : Fragment() {

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    private var coursesAdapter: CourseListAdapter? = null

    private val viewModel: MainFragmentViewModel by viewModels()

    private val listOfActions = arrayListOf("Get data", "Delete", "Update")
    private var selectedAction: String? = null

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
        coursesAdapter = null
        selectedAction = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.saveNewCourseButton.setOnClickListener {
            if (isCustomProviderAvailable()){
                if (binding.enterCourseTitleEditText.text.isNotEmpty()) {
                    saveCourse(
                        binding.enterCourseTitleEditText.text.toString()
                    )
                    binding.enterCourseTitleEditText.text.clear()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Please, enter title of new course",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        binding.getAllCoursesButton.setOnClickListener {
            if (isCustomProviderAvailable()){
                getAllCourses()
            }
        }
        binding.deleteAllCoursesButton.setOnClickListener {
            if (isCustomProviderAvailable()){
                viewModel.deleteAllCoursesFromMemory()
                getAllCourses()
            }
        }
        bindViewModel()
        initList()
//        isCustomProviderAvailable()
    }

    //    инициализация списка курсов
    private fun initList() {
//    инициализация адаптера
        coursesAdapter = CourseListAdapter { course ->
            val view = (view as ViewGroup).inflate(R.layout.update_view)
            AlertDialog.Builder(requireContext())
                .setTitle("What do u want to do?")
                .setSingleChoiceItems(listOfActions.toTypedArray(), -1) { _, i ->
                    selectedAction = listOfActions[i]
                }
                .setPositiveButton("Ok") { _, _ ->
                    when (selectedAction) {
                        "Get data" -> {
                            viewModel.getCourseById(course.id)
                        }
                        "Delete" -> {
                            viewModel.deleteCourseByIDFromMemory(course.id)
                            getAllCourses()
                        }
                        "Update" -> {
                            AlertDialog.Builder(requireContext())
                                .setTitle("Enter data for updating")
                                .setView(view)
                                .setPositiveButton("Ok") { _, _ ->
                                    val contentValues = ContentValues().apply {
                                        put(
                                            MainFragmentRepository.TITLE,
                                            view.titleUpdateEditText.text.toString()
                                        )
                                    }
                                    viewModel.updateCourse(course.id, contentValues)
                                    getAllCourses()
                                }
                                .setNegativeButton("Back") { _, _ -> }
                                .show()
                        }
                        else -> {
                            Toast.makeText(
                                requireContext(),
                                "You didn't choose a type of action",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
                .setNegativeButton("Cancel") { _, _ -> }
                .show()
        }
//    инициализируем список курсов
        with(binding.coursesListRecyclerView) {
            adapter = coursesAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
        }
//        getAllCourses()
    }

    private fun saveCourse(title: String) {
        viewModel.addNewCourse(title)
        getAllCourses()
    }

    private fun getAllCourses() {
        viewModel.getAllCourses()
    }

    //    баиндим ViewModel
    @SuppressLint("SetTextI18n")
    private fun bindViewModel() {
        viewModel.courseList.observe(viewLifecycleOwner) { listOfCourses ->
            coursesAdapter?.items = listOfCourses
        }
//        выбрасываем тост с ошибкой в случае ошибки
        viewModel.isError.observe(viewLifecycleOwner) { error ->
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        }
        viewModel.isFinished.observe(viewLifecycleOwner) { finishString ->
            Toast.makeText(requireContext(), finishString, Toast.LENGTH_SHORT).show()
        }
        viewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            if (loading) {
                isLoading()
            } else {
                idle()
            }
        }
        viewModel.courseById.observe(viewLifecycleOwner) { course ->
            Log.i("course", course.toString())
            val view = (view as ViewGroup).inflate(R.layout.course_item)
            view.titleInfoOfCourseTextView.text = "title: ${course.title}"
            view.idInfoOfCourseTextView.text = "id: ${course.id}"
            AlertDialog.Builder(requireContext())
                .setTitle("Course info")
                .setView(view)
                .setNegativeButton("back") { _, _ -> }
                .show()
        }
    }

    private fun isLoading() {
        binding.progressBar.isVisible = true
        binding.getAllCoursesButton.isEnabled = false
        binding.coursesListRecyclerView.isEnabled = false
        binding.saveNewCourseButton.isEnabled = false
        binding.enterCourseTitleEditText.isEnabled = false
    }

    private fun idle() {
        binding.progressBar.isVisible = false
        binding.getAllCoursesButton.isEnabled = true
        binding.coursesListRecyclerView.isEnabled = true
        binding.saveNewCourseButton.isEnabled = true
        binding.enterCourseTitleEditText.isEnabled = true
    }

    @SuppressLint("Recycle")
    private fun isCustomProviderAvailable(): Boolean {
        val customCP = context?.contentResolver?.acquireContentProviderClient(USER_URI)
        return if (customCP == null) {
            Toast.makeText(
                requireContext(),
                "Course content provider wasn't found",
                Toast.LENGTH_SHORT
            ).show()
            false
        } else {
            true
        }
    }

    companion object {
        private val USER_URI: Uri =
            Uri.parse("content://com.skillbox.skillbox.contentprovider.provider/users")
    }
}