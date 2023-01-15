package hu.bme.aut.android.spaceinspector.network.marsrover

import hu.bme.aut.android.spaceinspector.model.apod.Apod
import hu.bme.aut.android.spaceinspector.model.marsrover.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarsRoverApi {
    @GET("/mars-photos/api/v1/rovers/{roverName}/photos")
    fun getMarsRoverImages(
        @Path("roverName") roverName: String,
        @Query("api_key") apiKey: String?, /* API Key */
        @Query("sol") sol: Int?,
        @Query("earth_date") earthDate : String?,
        @Query("camera") camera: String?,
    ): Call<Photos>?

    @GET("/mars-photos/api/v1/rovers/{roverName}/latest_photos")
    fun getMarsRoverLatestImages(
        @Path("roverName") roverName: String,
        @Query("api_key") apiKey: String?, /* API Key */
    ): Call<LatestPhotos>?

    @GET("/mars-photos/api/v1/rovers")
    fun getMarsRovers(
        @Query("api_key") apiKey: String?, /* API Key */
    ): Call<Rovers?>?
}