package hu.bme.aut.android.spaceinspector.viewmodel

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import hu.bme.aut.android.spaceinspector.repository.ApiRepository
import hu.bme.aut.android.spaceinspector.view.image.NasaImageState

class NasaImagesListFragmentViewModel  : ViewModel() {
    private val apiRepository = ApiRepository()

    var state: LiveData<NasaImageState>? = null

    var page: Int? = null

    fun getImages(text: String): LiveData<NasaImageState> {
        state = apiRepository.getImages(text, null, null, null, null, null, null, null, page,null, null, null, null, null)
        return state!!
    }
}