package com.corcida.dmovie.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.corcida.dmovie.ui.common.ScopeViewModel
import com.corcida.usecases.location.FindLastLocationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val findLastLocationUseCase: FindLastLocationUseCase
): ScopeViewModel() {

    private val _model = MutableLiveData<MainUiModel>()
    val model: LiveData<MainUiModel> get() = _model
    private val timer = Timer()
    private val sendLocationsIsEnabled = false

    init {
        initScope()
    }
    fun onCreate(){
        _model.value = MainUiModel.RequestLocationPermission
    }

    fun onCoarsePermissionRequested() = launch {
        if (sendLocationsIsEnabled) {
            timer.scheduleAtFixedRate(object : TimerTask() {
                override fun run() {
                    getLastLocation()
                }
            }, 0L, 5 * (60 * 1000))
        }
    }

    private fun getLastLocation() = launch {
        findLastLocationUseCase.execute().onEach { dataState ->
            dataState.data?.let {
                _model.value = MainUiModel.Notify
            }

            dataState.error?.let {
                _model.value = MainUiModel.Error(it)
            }
        }.launchIn(this)
    }

}