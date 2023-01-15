package hu.bme.aut.android.spaceinspector.view.marsrover

import hu.bme.aut.android.spaceinspector.model.marsrover.MarsRoverImage
import hu.bme.aut.android.spaceinspector.model.marsrover.RoverDetails
import hu.bme.aut.android.spaceinspector.model.marsrover.Rovers

sealed class MarsRoverListState {
    object inProgress : MarsRoverListState()
    data class responseSuccess(val data: List<MarsRoverImage>) : MarsRoverListState()
    data class responseError(val exceptionMsg: String) : MarsRoverListState()
}