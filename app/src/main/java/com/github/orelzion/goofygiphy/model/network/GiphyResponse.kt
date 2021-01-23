package com.github.orelzion.goofygiphy.model.network

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
data class GiphyResponse(val data: List<GifData>)

@Parcelize
@Serializable
data class GifData(
    val id: String,
    val images: Image,
    val title: String
) : Parcelable

@Parcelize
@Serializable
data class Image(val downsized_medium: ImageProperties): Parcelable

@Parcelize
@Serializable
data class ImageProperties(val url: String): Parcelable
