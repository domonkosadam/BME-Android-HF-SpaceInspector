package hu.bme.aut.android.spaceinspector.model.neows.browse

data class Page(
    val number: Int,
    val size: Int,
    val total_elements: Int,
    val total_pages: Int
)