package com.skillbox.skillbox.viewhomework

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_course.*

class CourseActivity: AppCompatActivity(R.layout.activity_course) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    private fun handleIntentData(){
        intent.data?.lastPathSegment?.let { courseLink ->
            courseNameTextView.text = courseLink
        }
    }
}