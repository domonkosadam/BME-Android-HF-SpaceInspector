package hu.bme.aut.android.spaceinspector.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.spaceinspector.R
import hu.bme.aut.android.spaceinspector.adapter.ApproachAdapter.ApproachViewHolder
import hu.bme.aut.android.spaceinspector.databinding.RowApproachItemBinding
import hu.bme.aut.android.spaceinspector.model.neows.browse.CloseApproachData

class ApproachAdapter(): RecyclerView.Adapter<ApproachViewHolder>() {
    private var approaches: List<CloseApproachData> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApproachViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_approach_item, parent, false)
        return ApproachViewHolder(view)
    }

    override fun onBindViewHolder(holder: ApproachViewHolder, position: Int) {
        val item = approaches[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = approaches.size

    fun addApproaches(newList: List<CloseApproachData>) {
        approaches = newList
        notifyDataSetChanged()
    }

    inner class ApproachViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding = RowApproachItemBinding.bind(itemView)

        fun bind(approach: CloseApproachData) {
            binding.tvDate.text = "Date: ${approach.close_approach_date}"
            binding.tvOrbitingBody.text = "Orbiting body: ${approach.orbiting_body}"
            binding.tvVelocity.text = "Velocity: ${approach.relative_velocity.kilometers_per_hour} km/h"
            binding.tvDistance.text = "Miss Distance: ${approach.miss_distance.kilometers} km"
        }
    }
}