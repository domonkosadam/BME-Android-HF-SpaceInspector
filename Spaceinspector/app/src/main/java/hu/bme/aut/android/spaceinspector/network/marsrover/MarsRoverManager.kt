package hu.bme.aut.android.spaceinspector.network.marsrover

import hu.bme.aut.android.spaceinspector.model.apod.Apod
import hu.bme.aut.android.spaceinspector.model.marsrover.*
import hu.bme.aut.android.spaceinspector.network.NasaApiKey.apiKey
import hu.bme.aut.android.spaceinspector.network.apod.NasaApodApi
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MarsRoverManager {
    private val retrofit: Retrofit
    private val marsRoverApi: MarsRoverApi

    private const val SERVICE_URL = "https://api.nasa.gov"

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(SERVICE_URL)
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        marsRoverApi = retrofit.create(MarsRoverApi::class.java)
    }

    fun getMarsRoverImages(roverName: String, apiKey: String?, sol: Int?, earthDate: String?, camera: String?): Call<Photos>? {
        return marsRoverApi.getMarsRoverImages(roverName, apiKey, sol, earthDate, camera)
    }

    fun getMarsRoverLatestImages(roverName: String, apiKey: String?): Call<LatestPhotos>? {
        return marsRoverApi.getMarsRoverLatestImages(roverName, apiKey)
    }

    fun getMarsRovers(apiKey: String?): Call<Rovers?>? {
        return marsRoverApi.getMarsRovers(apiKey)
    }
}