package it.marcodallaba.beerbox.data.source.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import it.marcodallaba.beerbox.data.Beer

@Dao
interface BeersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBeers(beers: List<Beer>)

    @Query("SELECT * FROM beer")
    fun getBeersPagingSource(): PagingSource<Int, Beer>

    @Query("DELETE FROM beer")
    suspend fun clearAllBeers()

    @Query("SELECT MIN(insertTime) FROM beer")
    suspend fun getOldestInsertTime(): Long?
}