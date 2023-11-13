package com.corcida.dmovie.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.corcida.dmovie.databinding.FragmentProfileBinding
import com.corcida.dmovie.ui.common.NoDataDialogFragment
import com.corcida.dmovie.ui.common.adapter.MoviesAdapter
import com.corcida.dmovie.ui.common.loadUrlWithCircleCrop
import com.corcida.dmovie.ui.profile.ProfileUiModel.Content
import com.corcida.dmovie.ui.profile.ProfileUiModel.Error
import com.corcida.dmovie.ui.profile.ProfileUiModel.Loading
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private val knownForMoviesAdapter = MoviesAdapter()
    private lateinit var noDataDialogFragment : NoDataDialogFragment
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<ProfileViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        setViews()
        viewModel.model.observe(viewLifecycleOwner, Observer(this::observeUiModel))
        return binding.root
    }

    private fun setViews(){
        binding.knownFor.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.knownFor.adapter = knownForMoviesAdapter
        noDataDialogFragment = NoDataDialogFragment {
            noDataDialogFragment.dismiss()
            viewModel.getUser()
        }
        noDataDialogFragment.isCancelable = false
    }

    private fun observeUiModel(model: ProfileUiModel){
        when (model){
            is Content -> with(model.user) {
                binding.name.text = name
                binding.popularity.text = popularity.toString()
                binding.profilePic.loadUrlWithCircleCrop(image)
                knownForMoviesAdapter.movies = movies
                setLoading(false)
            }
            is Error -> {
                noDataDialogFragment.show(childFragmentManager, "No data dialog")
            }
            Loading -> setLoading(true)

        }
    }

    private fun setLoading(isLoading: Boolean) {
        binding.profileInformation.visibility = if (!isLoading) View.VISIBLE else View.INVISIBLE
        if (isLoading) binding.userLoading.root.startShimmer() else binding.userLoading.root.hideShimmer()
        binding.userLoading.root.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}