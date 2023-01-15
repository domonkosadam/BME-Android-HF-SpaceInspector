package hu.bme.aut.android.spaceinspector.network.image

import hu.bme.aut.android.spaceinspector.model.image.NasaImage
import hu.bme.aut.android.spaceinspector.model.marsrover.Rovers
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaImagesApi {
    @GET("/search")
    fun getImages(
        @Query("q") q: String?, /* Free text search terms to compare to all indexed metadata. */
        @Query("center") center: String?, /* NASA center which published the media. */
        @Query("description") description: String?, /* Terms to search for in “Description” fields. */
        @Query("description_508") description_508: String?, /* Terms to search for in “508 Description” fields. */
        @Query("keywords") keywords: String?, /* Terms to search for in “Keywords” fields. Separate multiple values with commas. */
        @Query("location") location: String?, /* Terms to search for in “Location” fields. */
        @Query("media_type") media_type: String?, /* Media types to restrict the search to. Available types: [“image”, “audio”]. Separate multiple values with commas. */
        @Query("nasa_id") nasa_id: String?, /* The media asset’s NASA ID. */
        @Query("page") page: Int?, /* Page number, starting at 1, of results to get. */
        @Query("photographer") photographer: String?, /* The primary photographer’s name. */
        @Query("secondary_creator") secondary_creator: String?, /* A secondary photographer/videographer’s name. */
        @Query("title") title: String?, /* Terms to search for in “Title” fields. */
        @Query("year_start") year_start: String?, /* The start year for results. Format: YYYY. */
        @Query("year_end") year_end: String?, /* The end year for results. Format: YYYY. */
    ): Call<NasaImage?>?
}