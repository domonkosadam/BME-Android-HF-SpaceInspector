package hu.bme.aut.android.spaceinspector.model.image

data class Data(
    val center: String?,
    val date_created: String?,
    val description: String?,
    val keywords: List<String>?,
    val media_type: String?,
    val nasa_id: String?,
    val title: String?
)