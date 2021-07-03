package com.skillbox.multithreading

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.skillbox.multithreading.databinding.LivelockFragmentBinding

class LivelockFragment : Fragment() {
    private var _binding: LivelockFragmentBinding? = null
    private val binding get() = _binding!!

    private var i = 0
    private val lock1 = Any()
    private val lock2 = Any()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LivelockFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.livelockButton.setOnClickListener {
            startDeadlock()
        }
    }


    private fun startDeadlock() {
//        val thread1 = Thread {
//            Log.d("Livelock", "Start1")
//
//            (0..1000000).forEach { _ ->
//                synchronized(lock1) {
//                    synchronized(lock2) {
//                        i++
//                    }
//                }
//            }
//            Log.d("Livelock", "End1")
//        }
//
//        val thread2 = Thread {
//            Log.d("Livelock", "Start2")
//            (0..1000000).forEach { _ ->
//                synchronized(lock2) {
//                    synchronized(lock1) {
//                        i++
//                    }
//                }
//            }
//
//            Log.d("Livelock", "End2")
//        }
//
//        thread1.start()
//        thread2.start()
//    }

        var firstThreadIsFree = true
        var secondThreadIsFree = true


        val thread1 = Thread {
            Log.d("Livelock", "Start 1 thread")
            firstThreadIsFree = false
            (0..10).forEach { _ ->
                synchronized(lock1) {
                    if (secondThreadIsFree) {
                        firstThreadIsFree = true
                        Log.d("Livelock", "1 thread lock 2 thread")
                        synchronized(lock2) {
                            i++
                        }
                    } else {
                        Log.d("Livelock", "2 thread is busy")
                        Thread.sleep(500)
                        firstThreadIsFree = false
                    }
                }
            }
            Log.d("Livelock", "End1")
        }

        val thread2 = Thread {
            secondThreadIsFree = false
            Log.d("Livelock", "Start 2 thread")
            (0..10).forEach { _ ->
                synchronized(lock2) {
                    if (firstThreadIsFree) {
                        secondThreadIsFree = true
                        Log.d("Livelock", "2 thread lock 1 thread")
                        synchronized(lock1) {
                            i++
                        }
                    } else {
                        Log.d("Livelock", "1 thread is busy")
                        Thread.sleep(500)
                        secondThreadIsFree = false
                    }
                }
            }

            Log.d("Livelock", "End2")
        }

        thread1.start()
        thread2.start()
    }
}