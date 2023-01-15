package hu.bme.aut.android.spaceinspector.network.neows

import hu.bme.aut.android.spaceinspector.model.apod.Apod
import hu.bme.aut.android.spaceinspector.model.neows.browse.NeoWsBrowse
import hu.bme.aut.android.spaceinspector.model.neows.neo.NeoWsNeo
import hu.bme.aut.android.spaceinspector.network.NasaApiKey.apiKey
import hu.bme.aut.android.spaceinspector.network.apod.NasaApodApi
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NasaNeoWsManager {
    private val retrofit: Retrofit
    private val neoWsApi: NasaNeoWsApi

    private const val SERVICE_URL = "https://api.nasa.gov"

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(SERVICE_URL)
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        neoWsApi = retrofit.create(NasaNeoWsApi::class.java)
    }

    fun getAsteroids(page: Int?, size: Int?, apiKey: String?): Call<NeoWsBrowse?>? {
        return neoWsApi.getAsteroids(page, size, apiKey)
    }

    fun getAsteroidData(apiKey: String?, asteroidId: Int): Call<NeoWsNeo?>? {
        return neoWsApi.getAsteroidData(apiKey, asteroidId)
    }
}