package hu.bme.aut.android.spaceinspector.network

import hu.bme.aut.android.spaceinspector.model.apod.Apod
import hu.bme.aut.android.spaceinspector.model.image.NasaImage
import hu.bme.aut.android.spaceinspector.model.marsrover.*
import hu.bme.aut.android.spaceinspector.model.neows.browse.NeoWsBrowse
import hu.bme.aut.android.spaceinspector.model.neows.neo.NeoWsNeo
import hu.bme.aut.android.spaceinspector.network.NasaApiKey.apiKey
import hu.bme.aut.android.spaceinspector.network.apod.NasaApodManager
import hu.bme.aut.android.spaceinspector.network.image.NasaImageManager
import hu.bme.aut.android.spaceinspector.network.marsrover.MarsRoverManager
import hu.bme.aut.android.spaceinspector.network.neows.NasaNeoWsManager
import retrofit2.Call

object NetworkManager {
    fun getApodData(apiKey: String, date: String?, conceptTags: Boolean?, hd: Boolean?, count: Int?, startDate: String?, endDate: String?, thumbs: Boolean?): Call<Apod?>? {
        return NasaApodManager.getApodData(apiKey, date, conceptTags, hd, count, startDate, endDate, thumbs)
    }

    fun getAsteroids(page: Int?, size: Int?, apiKey: String?): Call<NeoWsBrowse?>? {
        return NasaNeoWsManager.getAsteroids(page, size, apiKey)
    }

    fun getAsteroidData(apiKey: String?, asteroidId: Int): Call<NeoWsNeo?>? {
        return NasaNeoWsManager.getAsteroidData(apiKey, asteroidId)
    }

    fun getMarsRoverImages(apiKey: String?, roverName: String, sol: Int?, earthDate: String?, camera: String?): Call<Photos>? {
        return MarsRoverManager.getMarsRoverImages(roverName, apiKey, sol, earthDate, camera)
    }

    fun getMarsRoverLatestImages(apiKey: String?, roverName: String): Call<LatestPhotos>? {
        return MarsRoverManager.getMarsRoverLatestImages(roverName, apiKey)
    }

    fun getMarsRovers(apiKey: String?): Call<Rovers?>? {
        return MarsRoverManager.getMarsRovers(apiKey)
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
        return NasaImageManager.getImages(q, center, description, description_508, keywords, location, media_type, nasa_id, page, photographer, secondary_creator, title, year_start, year_end)
    }
}