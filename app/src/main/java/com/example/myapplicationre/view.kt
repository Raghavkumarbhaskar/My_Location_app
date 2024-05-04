package com.example.myapplicationre

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class view:ViewModel() {
    private var loc= mutableStateOf<locationdata?>(null)
    var loca:State<locationdata?> = loc

    fun updatelocation(new:locationdata)
    {
        loc.value = new
    }
}