package com.corcida.dmovie.ui.main

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.corcida.dmovie.R
import com.corcida.dmovie.databinding.ActivityMainBinding
import com.corcida.dmovie.util.PermissionRequester
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()
    private val coarsePermissionRequester =
        PermissionRequester(this, Manifest.permission.ACCESS_COARSE_LOCATION)
    private var notificationPermissionRequester : PermissionRequester? = null
    @Inject lateinit var builder : NotificationCompat.Builder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        navView.setupWithNavController(navController)
        viewModel.model.observe(this, Observer(this::observeUiModel))
        viewModel.onCreate()
    }

    @SuppressLint("MissingPermission")
    private fun observeUiModel(model: MainUiModel){
        when (model) {
            is MainUiModel.Error -> Toast.makeText(this, model.error, Toast.LENGTH_SHORT).show()
            MainUiModel.Notify -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                   requestPermissionAndNotify()
                }else {
                    with(NotificationManagerCompat.from(this)) {
                        notify(0, builder.build())
                    }
                }
            }
            MainUiModel.RequestLocationPermission -> coarsePermissionRequester.request {
                if (it)
                    viewModel.onCoarsePermissionRequested()
            }
        }
    }

    @SuppressLint("MissingPermission")
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun requestPermissionAndNotify(){
        notificationPermissionRequester = PermissionRequester(this,
            Manifest.permission.POST_NOTIFICATIONS)
        notificationPermissionRequester?.request {
            if (it) with(NotificationManagerCompat.from(this)) {
                notify(0, builder.build())
            }
        }
    }
}