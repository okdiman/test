package com.skillbox.github.ui.repository_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.skillbox.github.databinding.UsersRepositoryFragmentBinding
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch


class RepositoryListFragment : Fragment() {
    private var _binding: UsersRepositoryFragmentBinding? = null
    private val binding get() = _binding!!
    private val repoViewModel: RepositoryFragmentViewModel by viewModels()
    private var adapterRepo: RepoListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = UsersRepositoryFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        adapterRepo = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        observer()
        binding.gettingStarredRepoButton.setOnClickListener {
            getStarredRepo()
        }
    }

    //    инициализация списка
    private fun initList() {
        adapterRepo = RepoListAdapter() { position ->
//            передаем данные о юзере и о выбранном репозитории в след фрагмент
            val action =
                RepositoryListFragmentDirections.actionRepositoryListFragmentToInfoRepositoryFragment(
                    repoViewModel.publicRepo.value!![position].name,
                    repoViewModel.publicRepo.value!![position].owner.login
                )
            findNavController().navigate(action)
        }
        with(binding.repoRecyclerView) {
            adapter = adapterRepo
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
        lifecycleScope.launch(SupervisorJob()) {
            repoViewModel.getUsersInfo()
        }
    }

    //   получаем только отмеченные репозитории
    private fun getStarredRepo() {
        lifecycleScope.launch(SupervisorJob()) {
            repoViewModel.getStarredRepo()
        }
    }

    //  подписка на LiveData
    private fun observer() {
        repoViewModel.publicRepo.observe(viewLifecycleOwner) { info ->
            adapterRepo?.items = info
        }
        repoViewModel.isError.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
        repoViewModel.isLoading.observe(viewLifecycleOwner) {
            binding.repoProgressBar.isVisible = it
        }
    }

}