package com.corcida.dmovie.framework.mappers

import android.location.Geocoder
import com.corcida.dmovie.framework.local.entity.LocationEntity
import com.corcida.dmovie.ui.map.LocationModel
import com.corcida.domain.Location
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import android.location.Location as DeviceLocation
import com.corcida.dmovie.framework.remote.model.Location as ServiceLocation

fun Location.toRoomLocation() : LocationEntity =
    LocationEntity(0, latitude, longitude, address, date)

fun Location.toServiceLocation(device: String) : ServiceLocation =
    ServiceLocation(device, latitude, longitude, address, date)

fun Location.toUiModelLocation(id: Int, selected: Boolean) : LocationModel =
    LocationModel(id, selected, latitude, longitude, address, date)

fun LocationEntity.toDomainLocation() : Location =
    Location(latitude, longitude, address, date)

fun ServiceLocation.toDomainLocation() : Location =
    Location(latitude, longitude, address, date)

@Suppress("DEPRECATION")
fun DeviceLocation.toDomainLocation(geocoder: Geocoder) : Location  {
    val addresses = this.let {
        geocoder.getFromLocation(latitude, longitude, 1)
    }
    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val date = sdf.format(Date())
    val address = addresses?.firstOrNull()?.getAddressLine(0)
    return Location(latitude, longitude, address?:"Dirección desconocida", date)
}