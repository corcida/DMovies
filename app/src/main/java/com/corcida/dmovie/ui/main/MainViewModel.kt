package com.corcida.dmovie.ui.main

import androidx.core.app.NotificationCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.corcida.dmovie.framework.mappers.toUiModelLocation
import com.corcida.dmovie.ui.common.ScopeViewModel
import com.corcida.dmovie.ui.map.MapUiModel
import com.corcida.usecases.location.FindLastLocationUseCase
import com.corcida.usecases.location.GetLastLocationsUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val findLastLocationUseCase: FindLastLocationUseCase
): ScopeViewModel() {

    private val _model = MutableLiveData<MainUiModel>()
    val model: LiveData<MainUiModel> get() = _model
    private val timer = Timer()

    init {
        initScope()
    }
    fun onCreate(){
        _model.value = MainUiModel.RequestLocationPermission
    }

    fun onCoarsePermissionRequested() = launch {
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                getLastLocation()
            }
        }, 0L, 5 * (60*1000))
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