package com.corcida.dmovie.ui.photos

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.corcida.dmovie.databinding.FragmentPhotosBinding
import com.corcida.dmovie.ui.common.NoDataDialogFragment
import com.corcida.dmovie.ui.photos.PhotosUiModel.Content
import com.corcida.dmovie.ui.photos.PhotosUiModel.Error
import com.corcida.dmovie.ui.photos.PhotosUiModel.Loading
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PhotosFragment : Fragment() {

    private val photosAdapter = PhotosAdapter()
    private lateinit var noDataDialogFragment : NoDataDialogFragment
    private var _binding: FragmentPhotosBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<PhotosViewModel>()

    private val pickMultipleMedia =
        registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(3)) { uris ->
            if (uris.isNotEmpty()) {
                viewModel.saveNewFiles(uris)
            } else {
                Log.e("uri", "empty")
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPhotosBinding.inflate(inflater, container, false)
        setViews()
        viewModel.model.observe(viewLifecycleOwner, Observer(this::observeUiModel))
        viewModel.getData()
        return binding.root
    }
    private fun setViews(){
        setAdapters()
        binding.refreshLayout.isEnabled = false
        binding.refreshLayout.setOnRefreshListener {
            viewModel.refreshData()
        }
        noDataDialogFragment = NoDataDialogFragment {
            viewModel.getData()
        }

        binding.add.setOnClickListener { pickMultipleMedia.launch(PickVisualMediaRequest(
            ActivityResultContracts.PickVisualMedia.ImageOnly))  }
    }

    private fun setAdapters(){
        binding.photos.adapter = photosAdapter
    }

    private fun observeUiModel(model: PhotosUiModel){
        when (model){
            is Content -> {
                binding.refreshLayout.isRefreshing = false
                if (model.photos.isNotEmpty()) {
                    photosAdapter.photos = model.photos
                }
                setDataVisibility(model.photos.isNotEmpty())
            }
            is Error -> {
                noDataDialogFragment.show(childFragmentManager, "No data dialog")
            }
            Loading -> {
                binding.photos.visibility = View.VISIBLE

            }
        }
    }

    private fun setDataVisibility(isDataVisible: Boolean) {
        binding.photos.visibility = if (isDataVisible) View.VISIBLE else View.GONE
        binding.noPhoto.visibility = if (!isDataVisible) View.VISIBLE else View.GONE
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}