package hu.bme.aut.android.spaceinspector.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import hu.bme.aut.android.spaceinspector.repository.ApiRepository
import hu.bme.aut.android.spaceinspector.view.apod.ApodState
import hu.bme.aut.android.spaceinspector.view.neows.NeoWsBrowseState

class NeoWsListFragmentViewModel : ViewModel() {
    private val apiRepository = ApiRepository()

    var page: Int? = null
    val size: Int? = 20

    var state: LiveData<NeoWsBrowseState>? = null

    fun getAsteroids(): LiveData<NeoWsBrowseState> {
        state = apiRepository.getAsteroids(page, size)
        return state!!
    }
}