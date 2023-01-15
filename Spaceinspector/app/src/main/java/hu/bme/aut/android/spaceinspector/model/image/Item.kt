package hu.bme.aut.android.spaceinspector.model.image

import java.io.Serializable

data class Item(
    val `data`: List<Data>?,
    val href: String?,
    val links: List<Link>?,
) : Serializable