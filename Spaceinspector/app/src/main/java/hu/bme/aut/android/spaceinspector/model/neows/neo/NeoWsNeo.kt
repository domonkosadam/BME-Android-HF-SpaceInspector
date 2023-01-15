package hu.bme.aut.android.spaceinspector.model.neows.neo

data class NeoWsNeo(
    val absolute_magnitude_h: Double,
    val close_approach_data: List<CloseApproachData>,
    val designation: String,
    val estimated_diameter: EstimatedDiameter,
    val id: String,
    val is_potentially_hazardous_asteroid: Boolean,
    val is_sentry_object: Boolean,
    val links: Links,
    val name: String,
    val nasa_jpl_url: String,
    val neo_reference_id: String,
    val orbital_data: OrbitalData
)