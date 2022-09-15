package it.marcodallaba.beerbox.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Beer(
    val id: Int,
    val name: String,
    @field:SerializedName("tagline")
    val tagLine: String?,
    val description: String?,
    @field:SerializedName("image_url")
    val imageUrl: String?,
    val ebc: Float
) : Serializable