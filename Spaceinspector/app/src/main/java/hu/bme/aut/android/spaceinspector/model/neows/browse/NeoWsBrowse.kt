package hu.bme.aut.android.spaceinspector.model.neows.browse

data class NeoWsBrowse(
    val links: Links,
    val near_earth_objects: List<NearEarthObject>,
    val page: Page
)