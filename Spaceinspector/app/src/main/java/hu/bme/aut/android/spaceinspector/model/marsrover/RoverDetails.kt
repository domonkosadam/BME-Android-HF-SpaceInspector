package hu.bme.aut.android.spaceinspector.model.marsrover

import java.io.Serializable

data class RoverDetails(
    val cameras: List<Camera>,
    val id: Int,
    val landing_date: String,
    val launch_date: String,
    val max_date: String,
    val max_sol: Int,
    val name: String,
    val status: String,
    val total_photos: Int
) : Serializable