package com.corcida.dmovie.ui.common

import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.corcida.dmovie.util.Constants
import kotlin.properties.Delegates

fun ImageView.loadUrl(path: String) {
    Glide.with(context).load(Constants.imageUrl + path).into(this)
}

fun ImageView.loadUrlWithCircleCrop(path: String) {
    Glide.with(context).load(Constants.imageUrl + path).circleCrop().into(this)
}

fun ImageView.loadFirebaseUrl(path: String) {
    Glide.with(context).load(path).into(this)
}
inline fun <VH : RecyclerView.ViewHolder, T> RecyclerView.Adapter<VH>.basicDiffUtil(
    initialValue: List<T>,
    crossinline areItemsTheSame: (T, T) -> Boolean = { old, new -> old == new },
    crossinline areContentsTheSame: (T, T) -> Boolean = { old, new -> old == new }
) =
    Delegates.observable(initialValue) { _, old, new ->
        DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                areItemsTheSame(old[oldItemPosition], new[newItemPosition])

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                areContentsTheSame(old[oldItemPosition], new[newItemPosition])

            override fun getOldListSize(): Int = old.size

            override fun getNewListSize(): Int = new.size
        }).dispatchUpdatesTo(this@basicDiffUtil)
    }