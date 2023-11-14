package com.corcida.dmovie.ui.photos

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.corcida.dmovie.databinding.NodePhotoBinding
import com.corcida.dmovie.ui.common.loadFirebaseUrl

class PhotosViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val binding = NodePhotoBinding.bind(view)

    fun render(photo: PhotoModel?) {
        photo?.let {
            binding.image.loadFirebaseUrl(it.path)
            binding.shimmerViewContainer.stopShimmer()
            binding.image.visibility = View.VISIBLE
            binding.shimmerViewContainer.visibility = View.GONE
        }?:run {
            binding.shimmerViewContainer.startShimmer()
        }

    }

}