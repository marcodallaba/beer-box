package it.marcodallaba.beerbox.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import it.marcodallaba.beerbox.data.Beer
import it.marcodallaba.beerbox.data.RemoteKeys

@Database(entities = [Beer::class, RemoteKeys::class], version = 1)
abstract class BeersDatabase : RoomDatabase() {

    abstract fun beersDao(): BeersDao

    abstract fun remoteKeysDao(): RemoteKeysDao

}