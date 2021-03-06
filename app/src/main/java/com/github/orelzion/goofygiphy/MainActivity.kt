package com.github.orelzion.goofygiphy

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.github.orelzion.goofygiphy.model.network.GifData
import com.github.orelzion.goofygiphy.model.network.GiphyApiServiceImpl
import com.github.orelzion.goofygiphy.model.network.GiphyResponse
import com.github.orelzion.goofygiphy.view.GifsAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val IMAGE_DATA_KEY = "image_data"

    private lateinit var gifAdapter: GifsAdapter
    private lateinit var gifsListView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private var imageDataList = emptyList<GifData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar = findViewById(R.id.progressBar)
        gifsListView = findViewById(R.id.gifsList)

        gifAdapter = GifsAdapter().apply {
            clickListener = this@MainActivity::onItemClicked
        }
        gifsListView.adapter = gifAdapter

        if (savedInstanceState == null) {
            loadGifs()
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        savedInstanceState.getParcelableArrayList<GifData>(IMAGE_DATA_KEY)?.takeIf { it.isNotEmpty() }?.let {
            imageDataList = it
            gifAdapter.submitList(it)
            setProgressVisibility(false)
        } ?: run {
            loadGifs()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelableArrayList(IMAGE_DATA_KEY, ArrayList(imageDataList))
        super.onSaveInstanceState(outState)
    }

    private fun onItemClicked(gifData: GifData) {
        val fullScreenIntent = Intent(this, FullScreenImageActivity::class.java).apply {
            putExtra("url", gifData)
        }
        startActivity(fullScreenIntent)
    }

    private fun setProgressVisibility(show: Boolean) {
        progressBar.isVisible = show
    }

    private fun loadGifs() {
        val call = GiphyApiServiceImpl
            .service
            .fetchTrendingGifs("EEOBXcKu1hdwwYapZHU9QBcXMb5dNhEg")

        call.enqueue(GifCallback())

        setProgressVisibility(true)
    }

    inner class GifCallback : Callback<GiphyResponse> {
        override fun onResponse(call: Call<GiphyResponse>, response: Response<GiphyResponse>) {
            if (response.isSuccessful) {
                response.body()?.data?.let {
                    imageDataList = it
                    gifAdapter.submitList(it)
                }
            }
            setProgressVisibility(false)
        }

        override fun onFailure(call: Call<GiphyResponse>, t: Throwable) {
            setProgressVisibility(false)

            Log.e(MainActivity::javaClass.name, "Api call failed", t)
        }
    }
}