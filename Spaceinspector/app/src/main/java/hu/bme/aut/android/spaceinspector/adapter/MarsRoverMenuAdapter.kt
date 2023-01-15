package hu.bme.aut.android.spaceinspector.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.spaceinspector.R
import hu.bme.aut.android.spaceinspector.databinding.RowAsteroidItemBinding
import hu.bme.aut.android.spaceinspector.databinding.RowMarsRoverMenuItemBinding
import hu.bme.aut.android.spaceinspector.model.marsrover.RoverDetails
import hu.bme.aut.android.spaceinspector.model.neows.browse.NearEarthObject
import kotlinx.coroutines.NonDisposableHandle.parent

class MarsRoverMenuAdapter(private val listener: OnRoverSelectedListener): RecyclerView.Adapter<MarsRoverMenuAdapter.MarsRoverMenuViewHolder>() {
    var rovers: List<RoverDetails> = listOf()

    interface OnRoverSelectedListener {
        fun onRoverSelected(rover: RoverDetails?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarsRoverMenuViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_mars_rover_menu_item, parent, false)
        return MarsRoverMenuViewHolder(view)
    }

    override fun onBindViewHolder(holder: MarsRoverMenuViewHolder, position: Int) {
        val item = rovers[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = rovers.size

    fun addRovers(newList: List<RoverDetails>) {
        rovers = newList
        notifyDataSetChanged()
    }

    inner class MarsRoverMenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = RowMarsRoverMenuItemBinding.bind(itemView)
        var item: RoverDetails? = null

        init {
            binding.root.setOnClickListener { listener.onRoverSelected(item) }
        }

        fun bind(rover: RoverDetails) {
            item = rover
            binding.tvName.text = rover.name
            binding.tvStatus.text = rover.status
            binding.tvNoPhotos.text = rover.total_photos.toString()
            binding.tvDate.text =
                "${rover.landing_date} - ${rover.max_date}"
        }
    }
}