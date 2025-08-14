package com.horizon.daggerhiltapp.ui.viewmodel

import android.location.Location
import androidx.lifecycle.ViewModel
import com.horizon.daggerhiltapp.domain.repository.LocationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val locationRepo: LocationRepository
) : ViewModel() {

    private val _location = MutableStateFlow<Location?>(null)
    val location: StateFlow<Location?> = _location.asStateFlow()

    fun loadLocation() {
        locationRepo.getLastKnowLocation { loc ->
            _location.value = loc
        }
    }

    fun getLastKnowLocate() {
        locationRepo.getLocationClient { loc ->
            _location.value = loc
        }
    }

}