package hu.bme.aut.android.spaceinspector.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import hu.bme.aut.android.spaceinspector.model.marsrover.RoverDetails
import hu.bme.aut.android.spaceinspector.repository.ApiRepository
import hu.bme.aut.android.spaceinspector.view.marsrover.RoversState
import hu.bme.aut.android.spaceinspector.view.neows.NeoWsBrowseState

class MarsRoverMenuFragmentViewModel : ViewModel() {
    private val apiRepository = ApiRepository()

    private lateinit var data: LiveData<RoversState>

    fun getMarsRovers(): LiveData<RoversState> {
        if(!::data.isInitialized)
            data = apiRepository.getMarsRovers()
        return data
    }
}