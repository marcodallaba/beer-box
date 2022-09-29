package it.marcodallaba.beerbox.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RemoteKeys(
    @PrimaryKey
    val beerId: Int,
    val prevKey: Int?,
    val nextKey: Int?
)