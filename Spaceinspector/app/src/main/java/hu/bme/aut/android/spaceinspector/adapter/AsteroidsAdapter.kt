package hu.bme.aut.android.spaceinspector.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import hu.bme.aut.android.spaceinspector.R
import hu.bme.aut.android.spaceinspector.databinding.RowAsteroidItemBinding
import hu.bme.aut.android.spaceinspector.model.neows.browse.NearEarthObject

class AsteroidsAdapter(private val listener: OnAsteroidSelectedListener): RecyclerView.Adapter<AsteroidsAdapter.AsteroidViewHolder>() {
    private var asteroids: List<NearEarthObject> = listOf()

    interface OnAsteroidSelectedListener {
        fun onAsteroidSelected(job: NearEarthObject?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_asteroid_item, parent, false)
        return AsteroidViewHolder(view)
    }

    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {
        val item = asteroids[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = asteroids.size

    fun addAsteroids(newList: List<NearEarthObject>) {
        asteroids = newList
        notifyDataSetChanged()
    }


    inner class AsteroidViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding = RowAsteroidItemBinding.bind(itemView)
        var item: NearEarthObject? = null

        init {
            binding.root.setOnClickListener { listener.onAsteroidSelected(item) }
        }

        fun bind(asteroid: NearEarthObject) {
            item = asteroid
            if (asteroid.is_potentially_hazardous_asteroid)
                binding.ivHazardous.setImageResource(R.drawable.hazard)
            else
                binding.ivHazardous.setImageResource(R.drawable.shield)
            binding.tvAsteroidName.text = asteroid.name
            binding.tvSize.text = "Diameter: ${asteroid.estimated_diameter.meters.estimated_diameter_min} - ${asteroid.estimated_diameter.meters.estimated_diameter_max} m"
        }
    }
}