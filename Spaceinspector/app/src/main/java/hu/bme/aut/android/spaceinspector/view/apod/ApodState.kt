package hu.bme.aut.android.spaceinspector.view.apod

import hu.bme.aut.android.spaceinspector.model.apod.Apod

sealed class ApodState {
    object inProgress : ApodState()
    data class responseSuccess(val data: Apod) : ApodState()
    data class responseError(val exceptionMsg: String) : ApodState()
}