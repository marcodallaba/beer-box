package it.marcodallaba.beerbox.di.module

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import it.marcodallaba.beerbox.data.source.local.BeersDatabase
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RoomModule {

    @Singleton
    @Provides
    fun providesBeersDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, BeersDatabase::class.java, "beers-database").build()

    @Singleton
    @Provides
    fun providesBeersDao(beersDatabase: BeersDatabase) = beersDatabase.beersDao()
}