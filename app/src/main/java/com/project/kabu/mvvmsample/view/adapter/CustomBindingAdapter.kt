package com.project.kabu.mvvmsample.view.adapter

import android.databinding.BindingAdapter
import android.view.View

class CustomBindingAdapter {
    // xmlに定義するBindingAdapter
    @BindingAdapter("visibleGone")
    fun showHide(view: View, show: Boolean) {
        view.visibility = if (show) View.VISIBLE else View.GONE
    }
}