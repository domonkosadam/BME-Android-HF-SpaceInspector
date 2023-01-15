package hu.bme.aut.android.spaceinspector.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import hu.bme.aut.android.spaceinspector.repository.ApiRepository
import hu.bme.aut.android.spaceinspector.view.marsrover.MarsRoverListState
import java.util.*

class MarsRoverDetailsFragmentViewModel : ViewModel() {
    private val apiRepository = ApiRepository()

    var cameraCode: String? = null

    var roverName: String? = null

    var sol: Int? = null

    var earthDate: String? = null

    var state: MutableLiveData<MarsRoverListState>? = null

    init {
        val cal = Calendar.getInstance()
        earthDate = "${String.format("%02d",cal.get(Calendar.YEAR))}-${String.format("%02d",cal.get(
            Calendar.MONTH)+1)}-${String.format("%02d",cal.get(Calendar.DAY_OF_MONTH))}"
    }

    fun getLatestImages(): MutableLiveData<MarsRoverListState> {
        state = apiRepository.getMarsRoverLatestImages(roverName ?: "")
        return state!!
    }

    fun getImages(): MutableLiveData<MarsRoverListState> {
        Log.d("PARAMS", "$roverName, $earthDate, $cameraCode")
            state =
                apiRepository.getMarsRoverImages(roverName ?: "", sol, earthDate, cameraCode)
        return state!!
    }
}