package com.github.orelzion.goofygiphy

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.github.orelzion.goofygiphy.model.network.GifData

class FullScreenImageActivity : AppCompatActivity() {

    private val imageView: ImageView by lazy { findViewById(R.id.fullImage) }
    private val shareButton: Button by lazy { findViewById(R.id.shareBtn) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_full_screen_image)

        intent.getParcelableExtra<GifData>("url")?.let {
            setupViews(it.images.downsized_medium.url)
        }
    }

    private fun setupViews(url: String) {
        loadImage(url)

        shareButton.setOnClickListener {
            shareWithOtherActivities(url)
        }
    }

    private fun shareWithOtherActivities(url: String) {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, "Check out this cool gif $url")
        }
        startActivity(sendIntent)
    }

    private fun loadImage(url: String) {
        Glide.with(this)
            .load(url)
            .placeholder(R.drawable.loading)
            .thumbnail(Glide.with(this).load(R.drawable.loading))
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(imageView)
    }
}