package hu.bme.aut.android.spaceinspector.model

sealed class DatabaseResult {
    object inProgress : DatabaseResult()
    data class success(val data: Boolean) : DatabaseResult()
    object error : DatabaseResult()
}