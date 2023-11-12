package com.corcida.dmovie.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.corcida.dmovie.ui.common.ScopeViewModel
import com.corcida.dmovie.ui.movies.MoviesUiModel
import com.corcida.usecases.movie.GetMoviesUseCase
import com.corcida.usecases.user.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase
): ScopeViewModel() {
    private val _model = MutableLiveData<ProfileUiModel>()
    val model: LiveData<ProfileUiModel>
        get() {
            if (_model.value == null) getUser()
            return _model
        }

    fun getUser() = launch {
        getUserUseCase.execute().onEach { dataState ->
            if (dataState.loading) _model.value = ProfileUiModel.Loading

            dataState.data?.let {
                _model.value = ProfileUiModel.Content(it)
            }

            dataState.error?.let {
                _model.value = ProfileUiModel.Error(it)
            }
        }.launchIn(this)
    }
}