package com.example.myapplicationre

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import android.Manifest
import android.annotation.SuppressLint
import android.location.Address
import android.location.Geocoder
import android.os.Looper
import android.util.Log
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import java.util.Locale
import kotlin.coroutines.coroutineContext

class location(context:Context) {
    var o = context
    var locationProvider: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    fun requestlocationupdate(view: view) {
        var locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                super.onLocationResult(p0)
                p0.lastLocation?.let {
                    var a = locationdata(longitude = it.longitude, latitude = it.latitude)

                    view.updatelocation(a)

                }

            }
        }


        var locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000).build()

        locationProvider.requestLocationUpdates(
            locationRequest,
            locationCallback, Looper.getMainLooper()

        )
    }

    fun haslocation(context: Context = o): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED


    }

    fun getReadableLocation(LOCATION: locationdata): Any {

        val geocoder = Geocoder(o, Locale.getDefault())


       var address:MutableList<Address>? = geocoder.getFromLocation(LOCATION.latitude, LOCATION.longitude,1)


        return if (address?.isNotEmpty() == true) {
            address[0].getAddressLine(0)
        }
        else{
            "not found"
        }
    }
}