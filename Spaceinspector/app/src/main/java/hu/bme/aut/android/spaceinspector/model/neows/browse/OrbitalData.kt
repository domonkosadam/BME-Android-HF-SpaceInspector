package hu.bme.aut.android.spaceinspector.model.neows.browse

data class OrbitalData(
    val aphelion_distance: String,
    val ascending_node_longitude: String,
    val data_arc_in_days: Int,
    val eccentricity: String,
    val epoch_osculation: String,
    val equinox: String,
    val first_observation_date: String,
    val inclination: String,
    val jupiter_tisserand_invariant: String,
    val last_observation_date: String,
    val mean_anomaly: String,
    val mean_motion: String,
    val minimum_orbit_intersection: String,
    val observations_used: Int,
    val orbit_class: OrbitClass,
    val orbit_determination_date: String,
    val orbit_id: String,
    val orbit_uncertainty: String,
    val orbital_period: String,
    val perihelion_argument: String,
    val perihelion_distance: String,
    val perihelion_time: String,
    val semi_major_axis: String
)