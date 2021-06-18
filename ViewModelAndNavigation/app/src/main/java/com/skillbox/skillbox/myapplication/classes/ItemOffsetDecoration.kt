package com.skillbox.skillbox.myapplication.classes

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.skillbox.skillbox.myapplication.fromDptoPixels

class ItemOffsetDecoration(private val context: Context) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val offset = 10.fromDptoPixels(context)
        with(outRect) {
            left = offset
            right = offset
            top = offset
            bottom = offset
        }
    }
}