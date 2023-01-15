package hu.bme.aut.android.spaceinspector.network.neows

import hu.bme.aut.android.spaceinspector.model.neows.browse.NeoWsBrowse
import hu.bme.aut.android.spaceinspector.model.neows.neo.NeoWsNeo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NasaNeoWsApi {
    @GET("/neo/rest/v1/neo/browse")
    fun getAsteroids(
        @Query("page") page: Int?,
        @Query("size") size: Int?,
        @Query("api_key") apiKey: String?, /* API Key */
    ): Call<NeoWsBrowse?>?

    @GET("/neo/rest/v1/neo/neo/{asteroid_id}")
    fun getAsteroidData(
        @Query("api_key") apiKey: String?, /* API Key */
        @Path("asteroid_id") asteroid_id: Int,
    ): Call<NeoWsNeo?>?
}