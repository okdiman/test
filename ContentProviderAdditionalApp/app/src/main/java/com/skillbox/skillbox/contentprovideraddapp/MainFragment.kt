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

    //    создаем объект адаптера
    private var coursesAdapter: CourseListAdapter? = null

    //    создаем объект вью модели
    private val viewModel: MainFragmentViewModel by viewModels()

    //    список действий с курсом для пользователя
    private val listOfActions = arrayListOf("Get data", "Delete", "Update")

    //    переменная для выбронного пользователем действия
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
        Log.i("provider", "started Main Fragment")
        binding.saveNewCourseButton.setOnClickListener {
//            проверяем доступность кастомного контент провайдера на устройстве
            if (isCustomProviderAvailable()) {
//                проверяем заполенность поля названия курса
                if (binding.enterCourseTitleEditText.text.isNotEmpty()) {
//                    сохраняем курс по клику
                    saveCourse(
                        binding.enterCourseTitleEditText.text.toString()
                    )
//                    очищаем поле записи названия курса после сохранения
                    binding.enterCourseTitleEditText.text.clear()
                } else {
//                    если курс не введем, выбрасываем тост
                    Toast.makeText(
                        requireContext(),
                        "Please, enter title of new course",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        binding.getAllCoursesButton.setOnClickListener {
//            проверяем доступность кастомного контент провайдера на устройстве
            if (isCustomProviderAvailable()) {
                getAllCourses()
            }
        }
        binding.deleteAllCoursesButton.setOnClickListener {
//            проверяем доступность кастомного контент провайдера на устройстве
            if (isCustomProviderAvailable()) {
                viewModel.deleteAllCoursesFromMemory()
//                по окончанию обновляем список курсов
                getAllCourses()
            }
        }
//        баиндим вью модель
        bindViewModel()
//        инициализируем стартовый экран
        initList()
    }

    //    инициализация списка курсов
    private fun initList() {
//    инициализация адаптера
        coursesAdapter = CourseListAdapter { course ->
//            добавляем вью для обновления курса
            val view = (view as ViewGroup).inflate(R.layout.update_view)
//            вызываем диалог для выбора действия пользователем
            AlertDialog.Builder(requireContext())
                .setTitle("What do u want to do?")
//                    устанавливаем элементы для выбора действия пользваотелем
                .setSingleChoiceItems(listOfActions.toTypedArray(), -1) { _, i ->
//                    получаем выбор пользователя
                    selectedAction = listOfActions[i]
                }
                .setPositiveButton("Ok") { _, _ ->
//                    в зависимости от выбора пользователя производим определенные действия
                    when (selectedAction) {
//                        получение данных курса по id
                        "Get data" -> {
                            viewModel.getCourseById(course.id)
                        }
//                        удаление выбранного курса
                        "Delete" -> {
                            viewModel.deleteCourseByIDFromMemory(course.id)
                            getAllCourses()
                        }
//                        обновление данных выбранного курса
                        "Update" -> {
//                            вызываем диалог для того, чтобы пользователь ввел данные, которые он хочет обновить
                            AlertDialog.Builder(requireContext())
                                .setTitle("Enter data for updating")
//                                    добавляем в диалог нашу специальную вью
                                .setView(view)
                                .setPositiveButton("Ok") { _, _ ->
//                                    проверяем заполненность поля
                                    if (view.titleUpdateEditText.text.isNotEmpty()) {
//                                        создаем объект content Values
                                        val contentValues = ContentValues().apply {
//                                        кладем в него введенные пользователем данные
                                            put(
                                                MainFragmentRepository.TITLE,
                                                view.titleUpdateEditText.text.toString()
                                            )
                                        }
//                                        обновляем данные курса
                                        viewModel.updateCourse(course.id, contentValues)
//                                        обновляем список курсов
                                        getAllCourses()
                                    } else {
//                                        выводим тост в случае, если пользователь не заполнил поле ввода
                                        Toast.makeText(
                                            requireContext(),
                                            "U didn't enter new title",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                                .setNegativeButton("Back") { _, _ -> }
                                .show()
                        }
                        else -> {
//                                        выводим тост в случае, если пользователь не выбрал действие
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
//        получаем список курсов
        getAllCourses()
    }

    //    сохранение курса
    private fun saveCourse(title: String) {
        viewModel.addNewCourse(title)
//    обнолвяем список
        getAllCourses()
    }

    //    получение всех курсов
    private fun getAllCourses() {
        viewModel.getAllCourses()
    }

    //    баиндим ViewModel
    @SuppressLint("SetTextI18n")
    private fun bindViewModel() {
//        обновляем список курсов в адаптере
        viewModel.courseList.observe(viewLifecycleOwner) { listOfCourses ->
            coursesAdapter?.items = listOfCourses
        }
//        выбрасываем тост с ошибкой в случае ошибки
        viewModel.isError.observe(viewLifecycleOwner) { error ->
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        }
//        оповещаем об окончании какой-либо операции
        viewModel.isFinished.observe(viewLifecycleOwner) { finishString ->
            Toast.makeText(requireContext(), finishString, Toast.LENGTH_SHORT).show()
        }
//        статус вьюшек при загрузке
        viewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            if (loading) {
                isLoading()
            } else {
                idle()
            }
        }
//        получение данных курса
        viewModel.courseById.observe(viewLifecycleOwner) { course ->
            Log.i("course", course.toString())
//            вызываем нашу специльно созданную для этих целей вью
            val view = (view as ViewGroup).inflate(R.layout.course_item)
//            устанавливаем в нее значения параметров курса
            view.titleInfoOfCourseTextView.text = "title: ${course.title}"
            view.idInfoOfCourseTextView.text = "id: ${course.id}"
//            показываем ее через AlertDialog
            AlertDialog.Builder(requireContext())
                .setTitle("Course info")
                .setView(view)
                .setNegativeButton("back") { _, _ -> }
                .show()
        }
    }

//    контроль статуса вью при загрузке
    private fun isLoading() {
        binding.progressBar.isVisible = true
        binding.getAllCoursesButton.isEnabled = false
        binding.coursesListRecyclerView.isEnabled = false
        binding.saveNewCourseButton.isEnabled = false
        binding.enterCourseTitleEditText.isEnabled = false
    }
//    контроль статуса вью при обычном состоянии
    private fun idle() {
        binding.progressBar.isVisible = false
        binding.getAllCoursesButton.isEnabled = true
        binding.coursesListRecyclerView.isEnabled = true
        binding.saveNewCourseButton.isEnabled = true
        binding.enterCourseTitleEditText.isEnabled = true
    }

//    проверка доступности провайдера
    @SuppressLint("Recycle")
    private fun isCustomProviderAvailable(): Boolean {
//    создаем объект необходимого провайдера
        val customCP = context?.contentResolver?.acquireContentProviderClient(USER_URI)
//    проверяем его на null
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