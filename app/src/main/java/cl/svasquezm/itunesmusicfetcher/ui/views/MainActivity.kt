package cl.svasquezm.itunesmusicfetcher.ui.views

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import cl.svasquezm.itunesmusicfetcher.R
import cl.svasquezm.itunesmusicfetcher.domain.models.TrackModel
import cl.svasquezm.itunesmusicfetcher.framework.utils.ItemAdapterPayload
import cl.svasquezm.itunesmusicfetcher.ui.viewmodels.MainViewModel
import cl.svasquezm.itunesmusicfetcher.ui.viewmodels.factories.MainViewModelFactory
import cl.svasquezm.itunesmusicfetcher.ui.views.adapters.TrackAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class MainActivity : AppCompatActivity(), KodeinAware {
    override val kodein: Kodein by kodein()
    private var showSearchItem = true
    private var adapter = TrackAdapter()

    private var viewModel: MainViewModel? = null
    private val viewModelFactory: MainViewModelFactory by instance()
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                showSearchItem = true
                viewModel?.getLocalTracks()
                swipeRefreshLayout.isEnabled = true
                refreshEmptyState()
                invalidateOptionsMenu()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_favorites -> {
                showSearchItem = false
                viewModel?.getAllFavoriteTracks()
                swipeRefreshLayout.isEnabled = false
                refreshEmptyState(forFavorites = true)
                invalidateOptionsMenu()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Setup ViewModel for this Activity
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
        val tracks = viewModel!!.getTracks()

        // Set new data to adapter each time we retrieve Tracks
        tracks.observe(this, Observer<List<TrackModel>> {
            adapter.clearTracks()
            adapter.setTracks(it)
            adapter.notifyDataSetChanged()
        })

        // Click listener for adapter to play/stop a track preview
        adapter.onPlayStopImageClickListener = { track, i ->
            adapter.notifyItemChangedRerstoringPrevious(i, ItemAdapterPayload.SHOW_LOADING)
            viewModel!!.playOrStopASong(track, callback = {
                adapter.notifyItemChanged(i, it)
            }, onFailed = {
                Toast.makeText(this@MainActivity,
                    R.string.no_preview_url_for_this_track,
                    Toast.LENGTH_SHORT).show()
            })
        }

        // Click listener for adapter to select/deselect it as a favorite one
        adapter.onFavoriteIconClickListener = { track, i ->
            adapter.notifyItemChanged(i)
            viewModel?.selectOrDeselectTackAsFavorite(track)
        }

        // Set main empty state for first time only
        showEmptyState(R.string.tracks_status_first_time)

        // SetUp RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        recyclerView.adapter = adapter

        // Set up SwipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener {
            retrieveRemoteTracks()
        }

        refreshEmptyState()
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        menu?.findItem(R.id.searchItem)?.isVisible = showSearchItem

        if(showSearchItem) {

            // Add Listeners to SearchView
            (menu?.findItem(R.id.searchItem)?.actionView as SearchView)
                .setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String): Boolean {
                        swipeRefreshLayout.isRefreshing = true
                        retrieveRemoteTracks(query)
                        return false
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        return false
                    }

                })
        }

        return true
    }

    private fun retrieveRemoteTracks(term: String = "") {
        viewModel?.getRemoteTracks(term, onSuccess = {
            swipeRefreshLayout.isRefreshing = false
            refreshEmptyState(false)
        }, onFailed = {
            Toast.makeText(this, getString(R.string.request_failed), Toast.LENGTH_SHORT).show()
            swipeRefreshLayout.isRefreshing = false
        })
    }

    private fun refreshEmptyState(forFavorites: Boolean = false){
        viewModel?.getTracks()?.value?.let {
            if(it.isEmpty()){
                showEmptyState(if(forFavorites){
                    R.string.tracks_status_no_favorites
                } else {
                    R.string.tracks_status_no_results
                })
            } else {
                hideEmptyState()
            }
        }
    }


    /**
     * Show empty state with specified text
     */
    private fun showEmptyState(stringResource: Int){
        emptyState.visibility = View.VISIBLE
        emptyState.text = getString(stringResource)
    }

    /**
     * Hide empty state
     */
    private fun hideEmptyState(){
        emptyState.visibility = View.GONE
    }
}
