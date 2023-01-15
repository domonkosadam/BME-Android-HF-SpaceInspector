package hu.bme.aut.android.spaceinspector.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import hu.bme.aut.android.spaceinspector.R
import hu.bme.aut.android.spaceinspector.databinding.RowFavouriteApodItemBinding
import hu.bme.aut.android.spaceinspector.databinding.RowNasaImagesItemBinding
import hu.bme.aut.android.spaceinspector.model.apod.Apod
import hu.bme.aut.android.spaceinspector.model.image.Item

class FavouriteApodAdapter(private val context: Context, private val listener: OnApodItemClickedListener): RecyclerView.Adapter<FavouriteApodAdapter.FavouriteApodViewHolder>() {
    var images: MutableList<Apod> = mutableListOf()

    interface OnApodItemClickedListener {
        fun onApodItemClicked(image: Apod)
        fun onApodItemRemove(image: Apod)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteApodViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_favourite_apod_item, parent, false)
        return FavouriteApodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavouriteApodViewHolder, position: Int) {
        val item = images[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = images.size

    fun addImages(newList: List<Apod>) {
        images.clear()
        images.addAll(newList)
        notifyDataSetChanged()
    }

    inner class FavouriteApodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding = RowFavouriteApodItemBinding.bind(itemView)
        var item: Apod? = null

        init {
            binding.cardView.setOnClickListener { listener.onApodItemClicked(item!!) }
            binding.ivRemove.setOnClickListener {
                listener.onApodItemRemove(item!!)
            }
        }

        fun bind(image: Apod) {
            item = image
            if (image.media_type == "image")
                Glide.with(context).load(image.hdurl).into(binding.ivImage)
            else
                Glide.with(context).load(image.thumbnail_url).into(binding.ivImage)
            binding.tvTitle.text = image.title
        }
    }
}