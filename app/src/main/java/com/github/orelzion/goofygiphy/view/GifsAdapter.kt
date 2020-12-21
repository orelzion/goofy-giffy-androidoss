package com.github.orelzion.goofygiphy.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.orelzion.goofygiphy.R
import com.github.orelzion.goofygiphy.model.network.Data

class GifsAdapter: RecyclerView.Adapter<GifViewHolder>() {

    private var gifImages: List<Data> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifViewHolder {
        return GifViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.gif_item_view, parent, false)
        )
    }

    override fun onBindViewHolder(holder: GifViewHolder, position: Int) {
        val gifItem = gifImages[position]

        holder.bind(title = gifItem.title, gifUrl = gifItem.images.downsized_medium.url)
    }

    override fun getItemCount() = gifImages.size

    fun submitList(gifImages: List<Data>) {
        this.gifImages = gifImages
        notifyDataSetChanged()
    }
}

class GifViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val imageView: ImageView = itemView.findViewById(R.id.gifImageView)
    private val titleView: TextView = itemView.findViewById(R.id.gifTitle)

    fun bind(title: String, gifUrl: String) {
        titleView.text = title

        Glide.with(itemView.context)
            .load(gifUrl)
            .into(imageView)
    }
}