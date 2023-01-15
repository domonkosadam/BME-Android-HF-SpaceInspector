package hu.bme.aut.android.spaceinspector.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import hu.bme.aut.android.spaceinspector.repository.ApiRepository
import hu.bme.aut.android.spaceinspector.view.neows.NeoWsBrowseState
import hu.bme.aut.android.spaceinspector.view.neows.NeoWsNeoState

class NeoWsDetailsFragmentViewModel : ViewModel() {
    private val apiRepository = ApiRepository()

    private lateinit var data: LiveData<NeoWsNeoState>

    fun getAsteroidData(asteroidId: Int): LiveData<NeoWsNeoState> {
        if (!::data.isInitialized) {
            data = apiRepository.getAsteroidData(asteroidId)
        }
        return data
    }
}