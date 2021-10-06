package com.skillbox.skillbox.flow.fragments

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.skillbox.skillbox.flow.R
import com.skillbox.skillbox.flow.adapter.MovieAdapter
import com.skillbox.skillbox.flow.broadcastreceiver.InternetConnectionBroadcastReceiver
import com.skillbox.skillbox.flow.classes.MovieType
import com.skillbox.skillbox.flow.databinding.MainFragmentBinding
import com.skillbox.skillbox.flow.utils.elementChangeFlow
import com.skillbox.skillbox.flow.utils.textChangesFlow
import com.skillbox.skillbox.flow.utils.toast
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class MainFragment : Fragment(R.layout.main_fragment) {
    //    создаем объекты баиндинга, вью модель, адаптера и бродкаст ресивера
    private val binding: MainFragmentBinding by viewBinding(MainFragmentBinding::bind)
    private val viewModel: MainFragmentViewModel by viewModels()
    private var moviesAdapter: MovieAdapter? = null
    private val internetReceiver = InternetConnectionBroadcastReceiver()

    @FlowPreview
    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        flowSearching()
        initList()
        bindViewModel()
        binding.goToDatabaseButton.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToDatabaseFragment())
        }
    }

    override fun onResume() {
        super.onResume()
//        регистрируем бродкаст ресивер
        requireContext().registerReceiver(
            internetReceiver,
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )
    }

    override fun onPause() {
        super.onPause()
//        дерегистрируем бродкаст ресивер
        requireContext().unregisterReceiver(internetReceiver)
    }

    override fun onDestroyView() {
        super.onDestroyView()
//        зануляем адаптер во избежании утечек памяти
        moviesAdapter = null
    }

    //    инициализация адаптера
    private fun initList() {
        moviesAdapter = MovieAdapter()
        with(binding.listOfMoviesRecyclerView) {
            adapter = moviesAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    //    flow поиск по введенным данным
    @FlowPreview
    @ExperimentalCoroutinesApi
    private fun flowSearching() {
//    работа с flow только в корутине
        viewLifecycleOwner.lifecycleScope.launch {
            val movieType = binding.typeOfFilmRadioGroup.elementChangeFlow()
//                    устанавливаем по дефолту 0 индекс
                .onStart { emit(0) }
//                    преобразуем индекс в MovieType
                .map {
                    MovieType.values()[it]
                }
            val movieTitle = binding.filmTitleEditText.textChangesFlow()
            viewModel.bind(movieTitle, movieType)
        }
    }

    //    подписка на обновления stateFlow
    private fun bindViewModel() {
        lifecycleScope.launch {
            viewModel.searchStateFlow.collect { moviesList ->
                moviesAdapter?.items = moviesList
            }
            viewModel.isLoadingStateFlow.collect { loading ->
                isLoading(loading)
            }
            viewModel.isErrorStateFlow.collect { error ->
                toast(error)
            }
        }
        viewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            isLoading(loading)
        }
        viewModel.isError.observe(viewLifecycleOwner) { error ->
            toast(error)
        }
    }

    //    статусы вьюшек в зависимости от того идет загрузка или нет
    private fun isLoading(loading: Boolean) {
        if (loading) {
            binding.run {
                progressBar.isVisible = true
                filmTitleEditText.isEnabled = false
                typeOfFilmRadioGroup.isEnabled = false
            }
        } else {
            binding.run {
                progressBar.isVisible = false
                filmTitleEditText.isEnabled = true
                typeOfFilmRadioGroup.isEnabled = true
            }
        }
    }
}