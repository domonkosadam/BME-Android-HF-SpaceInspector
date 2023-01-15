package hu.bme.aut.android.spaceinspector.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import hu.bme.aut.android.spaceinspector.model.apod.Apod
import hu.bme.aut.android.spaceinspector.model.image.NasaImage
import hu.bme.aut.android.spaceinspector.model.marsrover.*
import hu.bme.aut.android.spaceinspector.model.neows.browse.NeoWsBrowse
import hu.bme.aut.android.spaceinspector.model.neows.neo.NeoWsNeo
import hu.bme.aut.android.spaceinspector.network.NasaApiKey
import hu.bme.aut.android.spaceinspector.network.NasaApiKey.apiKey
import hu.bme.aut.android.spaceinspector.network.NetworkManager
import hu.bme.aut.android.spaceinspector.network.marsrover.MarsRoverManager
import hu.bme.aut.android.spaceinspector.view.apod.ApodState
import hu.bme.aut.android.spaceinspector.view.image.NasaImageState
import hu.bme.aut.android.spaceinspector.view.marsrover.MarsRoverListState
import hu.bme.aut.android.spaceinspector.view.marsrover.RoversState
import hu.bme.aut.android.spaceinspector.view.neows.NeoWsBrowseState
import hu.bme.aut.android.spaceinspector.view.neows.NeoWsNeoState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiRepository {
    fun getApodData(date: String?, conceptTags: Boolean?, hd: Boolean?, count: Int?, startDate: String?, endDate: String?, thumbs: Boolean?): MutableLiveData<ApodState> {
        val resultData = MutableLiveData<ApodState>()
        resultData.value = ApodState.inProgress

        NetworkManager.getApodData(NasaApiKey.apiKey, date, conceptTags, hd, count, startDate, endDate, thumbs)?.enqueue(object : Callback<Apod?> {
            override fun onResponse(
                call: Call<Apod?>,
                response: Response<Apod?>
            ) {
                if (response.isSuccessful) {
                    resultData.postValue(response.body()
                        ?.let { ApodState.responseSuccess(it) })
                } else {
                    resultData.postValue(ApodState.responseError(response.message()))
                }
            }

            override fun onFailure(call: Call<Apod?>, throwable: Throwable) {
                Log.i("Error", throwable.message.toString())
                resultData.postValue(ApodState.responseError(throwable.message.toString()))
            }
        })

        return resultData
    }

    fun getAsteroids(page: Int?, size: Int?): MutableLiveData<NeoWsBrowseState> {
        val resultData = MutableLiveData<NeoWsBrowseState>()
        resultData.value = NeoWsBrowseState.inProgress

        NetworkManager.getAsteroids(page, size, NasaApiKey.apiKey)?.enqueue(object : Callback<NeoWsBrowse?> {
            override fun onResponse(
                call: Call<NeoWsBrowse?>,
                response: Response<NeoWsBrowse?>
            ) {
                if (response.isSuccessful) {
                    resultData.postValue(response.body()
                        ?.let { NeoWsBrowseState.responseSuccess(it) })
                } else {
                    resultData.postValue(NeoWsBrowseState.responseError(response.message()))
                }
            }

            override fun onFailure(call: Call<NeoWsBrowse?>, throwable: Throwable) {
                Log.i("Error", throwable.message.toString())
                resultData.postValue(NeoWsBrowseState.responseError(throwable.message.toString()))
            }
        })

        return resultData
    }

    fun getAsteroidData(asteroidId: Int): MutableLiveData<NeoWsNeoState> {
        val resultData = MutableLiveData<NeoWsNeoState>()
        resultData.value = NeoWsNeoState.inProgress

        NetworkManager.getAsteroidData(NasaApiKey.apiKey, asteroidId)?.enqueue(object : Callback<NeoWsNeo?> {
            override fun onResponse(
                call: Call<NeoWsNeo?>,
                response: Response<NeoWsNeo?>
            ) {
                if (response.isSuccessful) {
                    resultData.postValue(response.body()
                        ?.let { NeoWsNeoState.responseSuccess(it) })
                } else {
                    resultData.postValue(NeoWsNeoState.responseError(response.message()))
                }
            }

            override fun onFailure(call: Call<NeoWsNeo?>, throwable: Throwable) {
                Log.i("Error", throwable.message.toString())
                resultData.postValue(NeoWsNeoState.responseError(throwable.message.toString()))
            }
        })

        return resultData
    }

    fun getMarsRoverImages(roverName: String, sol: Int?, earthDate: String?, camera: String?): MutableLiveData<MarsRoverListState> {
        val resultData = MutableLiveData<MarsRoverListState>()
        resultData.value = MarsRoverListState.inProgress

        NetworkManager.getMarsRoverImages(NasaApiKey.apiKey, roverName, sol, earthDate, camera)?.enqueue(object : Callback<Photos> {
            override fun onResponse(
                call: Call<Photos>,
                response: Response<Photos>
            ) {
                if (response.isSuccessful) {
                    resultData.postValue(response.body()
                        ?.let { MarsRoverListState.responseSuccess(it.photos) })
                } else {
                    resultData.postValue(MarsRoverListState.responseError(response.message()))
                }
            }

            override fun onFailure(call: Call<Photos>, throwable: Throwable) {
                Log.i("Error", throwable.message.toString())
                resultData.postValue(MarsRoverListState.responseError(throwable.message.toString()))
            }
        })

        return resultData
    }

    fun getMarsRoverLatestImages(roverName: String): MutableLiveData<MarsRoverListState> {
        val resultData = MutableLiveData<MarsRoverListState>()
        resultData.value = MarsRoverListState.inProgress

        NetworkManager.getMarsRoverLatestImages(NasaApiKey.apiKey, roverName)?.enqueue(object : Callback<LatestPhotos> {
            override fun onResponse(
                call: Call<LatestPhotos>,
                response: Response<LatestPhotos>
            ) {
                if (response.isSuccessful) {
                    resultData.postValue(response.body()
                        ?.let { MarsRoverListState.responseSuccess(it.latest_photos) })
                } else {
                    resultData.postValue(MarsRoverListState.responseError(response.message()))
                }
            }

            override fun onFailure(call: Call<LatestPhotos>, throwable: Throwable) {
                Log.i("Error", throwable.message.toString())
                resultData.postValue(MarsRoverListState.responseError(throwable.message.toString()))
            }
        })

        return resultData
    }

    fun getMarsRovers(): MutableLiveData<RoversState> {
        val resultData = MutableLiveData<RoversState>()
        resultData.value = RoversState.inProgress

        NetworkManager.getMarsRovers(NasaApiKey.apiKey)?.enqueue(object : Callback<Rovers?> {
            override fun onResponse(
                call: Call<Rovers?>,
                response: Response<Rovers?>
            ) {
                if (response.isSuccessful) {
                    resultData.postValue(response.body()
                        ?.let { RoversState.responseSuccess(it)})
                } else {
                    resultData.postValue(RoversState.responseError(response.message()))
                }
            }

            override fun onFailure(call: Call<Rovers?>, throwable: Throwable) {
                Log.i("Error", throwable.message.toString())
                resultData.postValue(RoversState.responseError(throwable.message.toString()))
            }
        })

        return resultData
    }

    fun getImages(q: String?, center: String?, description: String?, description_508: String?, keywords: String?, location: String?, media_type: String?, nasa_id: String?, page: Int?, photographer: String?, secondary_creator: String?, title: String?, year_start: String?, year_end: String?): MutableLiveData<NasaImageState> {
        val resultData = MutableLiveData<NasaImageState>()
        resultData.value = NasaImageState.inProgress

        NetworkManager.getImages(q, center, description, description_508, keywords, location, media_type, nasa_id, page, photographer, secondary_creator, title, year_start, year_end)?.enqueue(object : Callback<NasaImage?> {
            override fun onResponse(
                call: Call<NasaImage?>,
                response: Response<NasaImage?>
            ) {
                if (response.isSuccessful) {
                    resultData.postValue(response.body()
                        ?.let { NasaImageState.responseSuccess(it)})
                } else {
                    resultData.postValue(NasaImageState.responseError(response.message()))
                }
            }

            override fun onFailure(call: Call<NasaImage?>, throwable: Throwable) {
                Log.i("Error", throwable.message.toString())
                resultData.postValue(NasaImageState.responseError(throwable.message.toString()))
            }
        })

        return resultData
    }
}