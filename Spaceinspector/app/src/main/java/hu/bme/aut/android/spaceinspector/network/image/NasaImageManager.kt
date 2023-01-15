package hu.bme.aut.android.spaceinspector.network.image

import hu.bme.aut.android.spaceinspector.model.apod.Apod
import hu.bme.aut.android.spaceinspector.model.image.NasaImage
import hu.bme.aut.android.spaceinspector.network.apod.NasaApodApi
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NasaImageManager {
    private val retrofit: Retrofit
    private val nasaImagesApi: NasaImagesApi

    private const val SERVICE_URL = "https://images-api.nasa.gov/"

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(SERVICE_URL)
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        nasaImagesApi = retrofit.create(NasaImagesApi::class.java)
    }

    fun getImages(q: String?,
                    center: String?,
                    description: String?,
                    description_508: String?,
                    keywords: String?,
                    location: String?,
                    media_type: String?,
                    nasa_id: String?,
                    page: Int?,
                    photographer: String?,
                    secondary_creator: String?,
                    title: String?,
                    year_start: String?,
                    year_end: String?): Call<NasaImage?>? {
        return nasaImagesApi.getImages(q, center, description, description_508, keywords, location, media_type, nasa_id, page, photographer, secondary_creator, title, year_start, year_end)
    }
}