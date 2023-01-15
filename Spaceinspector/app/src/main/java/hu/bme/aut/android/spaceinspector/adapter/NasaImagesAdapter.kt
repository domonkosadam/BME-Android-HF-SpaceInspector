package hu.bme.aut.android.spaceinspector.adapter

import android.content.Context
import android.graphics.Bitmap
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import hu.bme.aut.android.spaceinspector.R
import hu.bme.aut.android.spaceinspector.databinding.RowAsteroidItemBinding
import hu.bme.aut.android.spaceinspector.databinding.RowNasaImagesItemBinding
import hu.bme.aut.android.spaceinspector.databinding.RowRoverImageItemBinding
import hu.bme.aut.android.spaceinspector.model.image.Item
import hu.bme.aut.android.spaceinspector.model.image.NasaImage
import hu.bme.aut.android.spaceinspector.model.marsrover.MarsRoverImage
import hu.bme.aut.android.spaceinspector.model.neows.browse.NearEarthObject
import java.io.Serializable

class NasaImagesAdapter(private val context: Context, private val listener: OnImageClickedListener): RecyclerView.Adapter<NasaImagesAdapter.NasaImagesViewHolder>() {
    var images: ArrayList<Item> = ArrayList()

    interface OnImageClickedListener {
        fun onImageClickedListener(image: Item?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NasaImagesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_nasa_images_item, parent, false)
        return NasaImagesViewHolder(view)
    }

    override fun onBindViewHolder(holder: NasaImagesViewHolder, position: Int) {
        val item = images[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = images.size

    fun addImages(newList: List<Item>) {
        images.clear()
        images.addAll(newList)
        notifyDataSetChanged()
    }

    inner class NasaImagesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding = RowNasaImagesItemBinding.bind(itemView)
        var item: Item? = null

        init {
            binding.root.setOnClickListener { listener.onImageClickedListener(item) }
        }

        fun bind(image: Item) {
            item = image
            Glide.with(context).load(image?.links?.get(0)?.href).into(binding.ivImage)
            binding.tvTitle.text = image?.data?.get(0)?.title
        }
    }
}