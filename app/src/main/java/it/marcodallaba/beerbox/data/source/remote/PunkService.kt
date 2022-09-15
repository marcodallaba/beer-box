package it.marcodallaba.beerbox.data.source.remote

import it.marcodallaba.beerbox.data.Beer
import retrofit2.http.GET
import retrofit2.http.Query

interface PunkService {

    @GET("beers")
    suspend fun getBeers(
        @Query("beer_name") beerName: String?,
        @Query("ebc_gt") minEgt: Float?,
        @Query("ebc_lt") maxEgt: Float?,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): List<Beer>

}