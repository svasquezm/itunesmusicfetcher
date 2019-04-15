package cl.svasquezm.itunesmusicfetcher

import android.app.Application
import androidx.room.Room
import cl.svasquezm.itunesmusicfetcher.data.repositories.TrackRepository
import cl.svasquezm.itunesmusicfetcher.framework.models.database.TrackDatabase
import cl.svasquezm.itunesmusicfetcher.framework.models.repositories.TrackLocalSource
import cl.svasquezm.itunesmusicfetcher.framework.models.repositories.TrackRemoteSource
import cl.svasquezm.itunesmusicfetcher.framework.utils.PlayMediaHelper
import cl.svasquezm.itunesmusicfetcher.ui.viewmodels.factories.MainViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class ITunesMusicFetcherApplication : Application(), KodeinAware {
    override val kodein: Kodein by Kodein.lazy {

        // Room database instance
        bind<TrackDatabase>() with singleton {
            Room.databaseBuilder(applicationContext, TrackDatabase::class.java, "track_database")
                .allowMainThreadQueries()
                .build()
        }

        // Media player class
        bind<PlayMediaHelper>() with singleton { PlayMediaHelper() }

        // Repository dependencies
        bind() from singleton { TrackLocalSource(instance()) }
        bind() from singleton { TrackRemoteSource() }
        bind() from singleton { TrackRepository(instance(), instance()) }

        // View model factory
        bind() from provider { MainViewModelFactory(instance(), instance()) }
    }
}