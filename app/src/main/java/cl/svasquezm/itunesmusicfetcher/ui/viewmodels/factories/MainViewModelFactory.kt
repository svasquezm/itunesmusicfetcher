package cl.svasquezm.itunesmusicfetcher.ui.viewmodels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cl.svasquezm.itunesmusicfetcher.data.repositories.TrackRepository
import cl.svasquezm.itunesmusicfetcher.framework.utils.PlayMediaHelper
import cl.svasquezm.itunesmusicfetcher.ui.viewmodels.MainViewModel

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(private val trackRepository: TrackRepository,
                           private val playMediaHelper: PlayMediaHelper) : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(trackRepository, playMediaHelper) as T
    }
}