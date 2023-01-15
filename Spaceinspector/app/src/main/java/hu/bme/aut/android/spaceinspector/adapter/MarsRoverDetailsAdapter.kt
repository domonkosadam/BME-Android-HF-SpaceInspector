package hu.bme.aut.android.spaceinspector.adapter

import android.content.Context
import android.graphics.Bitmap
import android.media.Image
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import hu.bme.aut.android.spaceinspector.R
import hu.bme.aut.android.spaceinspector.databinding.RowApproachItemBinding
import hu.bme.aut.android.spaceinspector.databinding.RowRoverImageItemBinding
import hu.bme.aut.android.spaceinspector.model.marsrover.MarsRoverImage
import hu.bme.aut.android.spaceinspector.model.neows.browse.CloseApproachData

class MarsRoverDetailsAdapter(private val context: Context, private val listener: OnRoverImageClickedListener): RecyclerView.Adapter<MarsRoverDetailsAdapter.MarsRoverDetailsViewHolder>() {
    private var images: List<MarsRoverImage> = listOf()

    interface OnRoverImageClickedListener {
        fun onRoverImageClicked(image: Bitmap)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarsRoverDetailsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_rover_image_item, parent, false)
        return MarsRoverDetailsViewHolder(view)
    }

    override fun onBindViewHolder(holder: MarsRoverDetailsViewHolder, position: Int) {
        val item = images[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = images.size

    fun addImages(newList: List<MarsRoverImage>) {
        images = newList
        notifyDataSetChanged()
    }

    inner class MarsRoverDetailsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding = RowRoverImageItemBinding.bind(itemView)

        init {
            binding.cvRoverImage.setOnClickListener {
                listener.onRoverImageClicked(binding.ivRoverImage.drawable.toBitmap())
            }
        }

        fun bind(image: MarsRoverImage) {
            binding.tvDate.text = image.earth_date
            binding.tvCamera.text = image.camera.full_name
            Glide.with(context).load(image.img_src).into(binding.ivRoverImage)
        }
    }
}