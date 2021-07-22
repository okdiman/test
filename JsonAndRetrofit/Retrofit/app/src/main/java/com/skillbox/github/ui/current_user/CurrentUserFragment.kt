package com.skillbox.github.ui.current_user

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.skillbox.github.data.Network
import com.skillbox.github.databinding.CurrentUserFragmentBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CurrentUserFragment : Fragment() {

    private var _binding: CurrentUserFragmentBinding? = null
    private val binding get() = _binding!!
    private val onComplete: (String) -> Unit = { userData ->
        binding.userInfoTextView.text = userData
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CurrentUserFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getUsersInfo(onComplete) { errorMessage ->
            Toast.makeText(requireContext(), "$errorMessage", Toast.LENGTH_SHORT).show()
        }
    }


    private fun getUsersInfo(
        onComplete: (String) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        Network.githubApi.searchUsersInformation().enqueue(
            object : Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.e("server", "$t")
                    onError(t)
                }

                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.isSuccessful) {
                        onComplete(response.body().toString())
                    } else {
                        onError(RuntimeException("incorrect status code"))
                    }
                }
            }
        )
    }
}