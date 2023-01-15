package hu.bme.aut.android.spaceinspector.network.apod

import hu.bme.aut.android.spaceinspector.model.apod.Apod
import hu.bme.aut.android.spaceinspector.network.NasaApiKey
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NasaApodManager {
    private val retrofit: Retrofit
    private val nasaApodApi: NasaApodApi

    private const val SERVICE_URL = "https://api.nasa.gov"

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(SERVICE_URL)
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        nasaApodApi = retrofit.create(NasaApodApi::class.java)
    }

    fun getApodData(apiKey: String?, date: String?, conceptTags: Boolean?, hd: Boolean?, count: Int?, startDate: String?, endDate: String?, thumbs: Boolean?): Call<Apod?>? {
        return nasaApodApi.getApodData(apiKey, date, conceptTags, hd, count, startDate, endDate, thumbs)
    }
}