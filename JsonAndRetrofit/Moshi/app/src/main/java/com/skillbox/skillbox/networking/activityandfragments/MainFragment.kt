package com.skillbox.skillbox.networking.activityandfragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.skillbox.skillbox.networking.R
import com.skillbox.skillbox.networking.databinding.MainFragmentBinding
import com.skillbox.skillbox.networking.files.inflate
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.android.synthetic.main.new_score.view.*

class MainFragment : Fragment() {
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    private val movieViewModel: ViewModelMainFragment by viewModels()

    private var adapterMovie: AdapterMovies? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initStartScreen()
        observeViewModel()
        binding.searchButton.setOnClickListener {
            initRequest()
        }
        binding.addNewScoreButton.setOnClickListener {
            addScore()
        }
    }

    //инициализируем стартовый экран
    private fun initStartScreen() {
        //инициализируем выпадающее меню
        val adapterMenu = ArrayAdapter(
            requireContext(),
            R.layout.item_menu_layout,
            resources.getStringArray(R.array.movies_types_string_array)
        )
        AutoCompleteTextView.setAdapter(adapterMenu)
        //инициализируем список фильмов
        adapterMovie = AdapterMovies {}
        with(binding.movieRecyclerView) {
            adapter = adapterMovie
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    //подписываемся на обновления ViewModel
    private fun observeViewModel() {
        movieViewModel.movie.observe(viewLifecycleOwner) { newMovies ->
            if (newMovies.isEmpty()) {
                //вызываем AlertDialog в случае ошибки запроса
                AlertDialog.Builder(requireContext())
                    .setMessage("Sorry, an error has occurred, please, check your internet connection and try again")
                    .setNegativeButton("cancel") { _, _ -> }
                    .setPositiveButton("repeat") { _, _ -> request() }
                    .show()
            } else {
                adapterMovie?.items = newMovies
            }
        }
        //подписываемся на обновление статуса загрузки
        movieViewModel.isLoading.observe(viewLifecycleOwner, ::updateLoadingState)
        movieViewModel.isError.observe(viewLifecycleOwner) { isError() }
        movieViewModel.isAdded.observe(viewLifecycleOwner) { isAddedScore() }
    }

    private fun isError() {
        Toast.makeText(requireContext(), movieViewModel.getError, Toast.LENGTH_SHORT).show()
    }

    private fun isAddedScore() {
        Toast.makeText(requireContext(), "Score was added", Toast.LENGTH_SHORT).show()
    }

    //функция, отвечающая за поведение приложения при загрузке данных
    private fun updateLoadingState(isLoading: Boolean) {
        binding.movieRecyclerView.isVisible = isLoading.not()
        binding.progressBar.isVisible = isLoading
        binding.searchButton.isEnabled = isLoading.not()
        binding.TitleMovieEditText.isEnabled = isLoading.not()
        binding.YearMovieEditText.isEnabled = isLoading.not()
    }

    //выполнение запроса поиска фильмов
    private fun request() {
        movieViewModel.requestMovies(
            binding.TitleMovieEditText.text.toString()
        )
    }

    //проверка корректности введенного года
    private fun correctYear(): Boolean {
        return binding.YearMovieEditText.text.toString().toInt() in 1900..2021
    }

    //инициализация запроса поиска
    private fun initRequest() {
        if (binding.TitleMovieEditText.text.isNotEmpty()) {
            if (binding.YearMovieEditText.text.toString().isNotEmpty()) {
                if (correctYear()) {
                    request()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Enter correct year of film, please",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            } else {
                request()
            }

        } else {
            Toast.makeText(requireContext(), "Enter title of film, please", Toast.LENGTH_SHORT)
                .show()
        }
    }

    //добавление новой оценки к фильму
    private fun addScore() {
        if (!movieViewModel.movie.value.isNullOrEmpty()) {
            val viewForAddingScore = (view as ViewGroup).inflate(R.layout.new_score)
            AlertDialog.Builder(requireContext())
                .setMessage("Введите источник оценки и саму оценку фильма")
                .setView(viewForAddingScore)
                .setNegativeButton("Cancel") { _, _ -> }
                .setPositiveButton("enter") { _, _ ->
                    if (viewForAddingScore.sourceEditText.text.isNotEmpty() &&
                        viewForAddingScore.scoreEditText.text.isNotEmpty()
                    ) {
                        movieViewModel.addScore(
                            0,
                            viewForAddingScore.sourceEditText.text.toString(),
                            viewForAddingScore.scoreEditText.text.toString()
                        )
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Please, check filling of the form and try again",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                .show()
        } else {
            Toast.makeText(requireContext(), "first you need choose a film", Toast.LENGTH_SHORT)
                .show()
        }
    }
}