package hu.bme.aut.android.spaceinspector.model.marsrover

data class MarsRoverImage(
    val camera: Camera,
    val earth_date: String,
    val id: Int,
    val img_src: String,
    val rover: Rover,
    val sol: Int
)