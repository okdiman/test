package com.skillbox.multithreading

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.skillbox.multithreading.databinding.DeadlockFragmentBinding

class DeadlockFragment : Fragment() {

    private var _binding: DeadlockFragmentBinding? = null
    private val binding get() = _binding!!

    private var i = 0
    private val lock1 = Any()
    private val lock2 = Any()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DeadlockFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.startDeadlockButton.setOnClickListener {
            startDeadlock()
        }
    }


    private fun startDeadlock() {
//        val thread1 = Thread {
//            Log.d("Deadlock", "Start1")
//
//            (0..1000000).forEach {
//                synchronized(lock1) {
//                    synchronized(lock2) {
//                        i++
//                    }
//                }
//            }
//            Log.d("Deadlock", "End1")
//        }
//
//        val thread2 = Thread {
//            Log.d("Deadlock", "Start2")
//            (0..1000000).forEach {
//                synchronized(lock2) {
//                    synchronized(lock1) {
//                        i++
//                    }
//                }
//            }
//
//            Log.d("Deadlock", "End2")
//        }
//
//        thread1.start()
//        thread2.start()

        val thread1 = Thread {
            Log.d("Deadlock", "Start1")
            (0..1000000).forEach { _ ->
                synchronized(lock1) {
                    i++
                }
            }
            Log.d("Deadlock", "End1")
        }

        val thread2 = Thread {
            Log.d("Deadlock", "Start2")
            (0..1000000).forEach { _ ->
                synchronized(lock2) {
                    i++
                }
            }

            Log.d("Deadlock", "End2")
        }

        thread1.start()
        thread2.start()
        thread1.join()
        thread2.join()
    }
}