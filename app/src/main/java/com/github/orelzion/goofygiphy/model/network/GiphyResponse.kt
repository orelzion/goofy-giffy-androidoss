package com.github.orelzion.goofygiphy.model.network

import kotlinx.serialization.Serializable

@Serializable
data class GiphyResponse(val data: List<Data>)

@Serializable
data class Data(
    val id: String,
    val images: Image,
    val title: String
)

@Serializable
data class Image(val downsized_medium: ImageProperties)

@Serializable
data class ImageProperties(val url: String)
