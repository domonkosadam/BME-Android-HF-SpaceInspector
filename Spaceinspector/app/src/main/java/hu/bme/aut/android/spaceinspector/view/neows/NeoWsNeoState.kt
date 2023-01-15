package hu.bme.aut.android.spaceinspector.view.neows

import hu.bme.aut.android.spaceinspector.model.apod.Apod
import hu.bme.aut.android.spaceinspector.model.neows.neo.NeoWsNeo
import hu.bme.aut.android.spaceinspector.view.apod.ApodState

sealed class NeoWsNeoState {
    object inProgress : NeoWsNeoState()
    data class responseSuccess(val data: NeoWsNeo) : NeoWsNeoState()
    data class responseError(val exceptionMsg: String) : NeoWsNeoState()
}