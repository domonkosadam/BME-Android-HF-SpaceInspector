package hu.bme.aut.android.spaceinspector.network.apod

import hu.bme.aut.android.spaceinspector.model.apod.Apod
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaApodApi {
    @GET("/planetary/apod")
    fun getApodData(
        @Query("api_key") apiKey: String?, /* API Key */
        @Query("date") datet: String?, /* A string in YYYY-MM-DD format indicating the date of the APOD image (example: 2014-11-03). Defaults to today's date. Must be after 1995-06-16, the first day an APOD picture was posted. There are no images for tomorrow available through this API. */
        @Query("concept_tags ") conceptTags : Boolean?, /* A boolean True|False indicating whether concept tags should be returned with the rest of the response. The concept tags are not necessarily included in the explanation, but rather derived from common search tags that are associated with the description text. (Better than just pure text search.) Defaults to False. */
        @Query("hd") hd: Boolean?, /* A boolean True|False parameter indicating whether or not high-resolution images should be returned. This is present for legacy purposes, it is always ignored by the service and high-resolution urls are returned regardless. */
        @Query("count") count: Int?, /* A positive integer, no greater than 100. If this is specified then count randomly chosen images will be returned in a JSON array. Cannot be used in conjunction with date or start_date and end_date. */
        @Query("start_date") startDate: String?, /* A string in YYYY-MM-DD format indicating the start of a date range. All images in the range from start_date to end_date will be returned in a JSON array. Cannot be used with date. */
        @Query("end_date ") endDate: String?, /* A string in YYYY-MM-DD format indicating that end of a date range. If start_date is specified without an end_date then end_date defaults to the current date. */
        @Query("thumbs") thumbs: Boolean?, /* A boolean parameter True|False inidcating whether the API should return a thumbnail image URL for video files. If set to True, the API returns URL of video thumbnail. If an APOD is not a video, this parameter is ignored. */
    ): Call<Apod?>?
}