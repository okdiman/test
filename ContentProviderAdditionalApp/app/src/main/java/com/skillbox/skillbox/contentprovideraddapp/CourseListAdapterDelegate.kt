package com.skillbox.skillbox.contentprovideraddapp

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.skillbox.skillbox.contentprovider.inflate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.course_item.*

class CourseListAdapterDelegate(private val onCourseClick: (Course) -> Unit) :
    AbsListItemAdapterDelegate<Course, Course, CourseListAdapterDelegate.Holder>() {
    //    создаем ViewHolder для работы с нашими элементами списка (заполнение и клик лисенер)
    class Holder(
        override val containerView: View,
        onCourseClick: (Course) -> Unit
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        private var currentCourseForList: Course? = null

        //    инициализруем клик лисенер
        init {
            containerView.setOnClickListener { currentCourseForList?.let(onCourseClick) }
        }

        //    баиндим данные в элемент списка
        fun bind(courseForList: Course) {
            currentCourseForList = courseForList
            idOfCourseTextView.text = courseForList.id.toString()
            titleOfCourseTextView.text = courseForList.title
        }
    }

    //    имплементируем методы AbsListItemAdapterDelegate
    override fun isForViewType(
        item: Course,
        items: MutableList<Course>,
        position: Int
    ): Boolean {
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup): Holder {
        return Holder(parent.inflate(R.layout.course_item), onCourseClick)
    }

    override fun onBindViewHolder(
        item: Course,
        holder: Holder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }
}