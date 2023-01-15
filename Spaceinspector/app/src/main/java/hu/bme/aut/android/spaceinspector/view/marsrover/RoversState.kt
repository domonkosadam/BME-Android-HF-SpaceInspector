package hu.bme.aut.android.spaceinspector.view.marsrover

import hu.bme.aut.android.spaceinspector.model.marsrover.Rovers

sealed class RoversState {
    object inProgress : RoversState()
    data class responseSuccess(val data: Rovers) : RoversState()
    data class responseError(val exceptionMsg: String) : RoversState()
}