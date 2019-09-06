package ercanduman.mvvmdemo

import android.app.Application
import ercanduman.mvvmdemo.data.db.AppDatabase
import ercanduman.mvvmdemo.data.network.JsonPlaceHolderApi
import ercanduman.mvvmdemo.data.network.NetworkConnectionInterceptor
import ercanduman.mvvmdemo.data.preferences.PreferenceProvider
import ercanduman.mvvmdemo.data.repository.AlbumsRepository
import ercanduman.mvvmdemo.data.repository.PhotosRepository
import ercanduman.mvvmdemo.ui.album.AlbumsViewModelFactory
import ercanduman.mvvmdemo.ui.photo.PhotosViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class MainApplication : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@MainApplication))

        /**
         * singleton: retrieves only single instance of the required object
         * provider: can provide instance of object any time needed
         */
        bind() from singleton { NetworkConnectionInterceptor(instance()) }
        bind() from singleton { JsonPlaceHolderApi(instance()) }
        bind() from singleton { AppDatabase(instance()) }
        bind() from singleton { PreferenceProvider(instance()) }
        bind() from singleton { AlbumsRepository(instance(), instance(), instance()) }
        bind() from singleton { PhotosRepository(instance(), instance(), instance()) }
        bind() from provider { AlbumsViewModelFactory(instance()) }
        bind() from provider { PhotosViewModelFactory(instance()) }
    }

}