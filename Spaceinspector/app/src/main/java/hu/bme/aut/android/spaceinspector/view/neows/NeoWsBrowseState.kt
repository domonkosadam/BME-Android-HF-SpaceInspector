package hu.bme.aut.android.spaceinspector.view.neows

import hu.bme.aut.android.spaceinspector.model.neows.browse.NeoWsBrowse
import hu.bme.aut.android.spaceinspector.model.neows.neo.NeoWsNeo

sealed class NeoWsBrowseState {
    object inProgress : NeoWsBrowseState()
    data class responseSuccess(val data: NeoWsBrowse) : NeoWsBrowseState()
    data class responseError(val exceptionMsg: String) : NeoWsBrowseState()
}