package hu.bme.aut.android.spaceinspector.view.image

import hu.bme.aut.android.spaceinspector.model.apod.Apod
import hu.bme.aut.android.spaceinspector.model.image.NasaImage
import hu.bme.aut.android.spaceinspector.view.apod.ApodState

sealed class NasaImageState {
    object inProgress : NasaImageState()
    data class responseSuccess(val data: NasaImage) : NasaImageState(), java.io.Serializable
    data class responseError(val exceptionMsg: String) : NasaImageState()
}