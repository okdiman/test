package com.skillbox.skillbox.contentprovideraddapp.course

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class CourseListAdapter (onCourseClick: (Course) -> Unit) :
    AsyncListDifferDelegationAdapter<Course>(FileDiffUtilCallback()) {
    //    создаем делегат для нашего адаптера
    init {
        delegatesManager.addDelegate(CourseListAdapterDelegate(onCourseClick))
    }
    //    создаем класс DiffUtilCallback для нашего адаптера
    class FileDiffUtilCallback : DiffUtil.ItemCallback<Course>() {
        override fun areItemsTheSame(oldItem: Course, newItem: Course): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Course, newItem: Course): Boolean {
            return oldItem == newItem
        }
    }
}