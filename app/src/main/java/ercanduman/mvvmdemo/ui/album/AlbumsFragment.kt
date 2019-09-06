package ercanduman.mvvmdemo.ui.album

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import ercanduman.mvvmdemo.R
import ercanduman.mvvmdemo.data.db.entities.album.Album
import ercanduman.mvvmdemo.ui.ProcessListener
import ercanduman.mvvmdemo.util.*
import kotlinx.android.synthetic.main.fragment_album.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class AlbumsFragment : Fragment(), KodeinAware, ProcessListener {
    override val kodein by kodein()
    private val factory: AlbumsViewModelFactory by instance()
    private lateinit var albumsViewModel: AlbumsViewModel
    lateinit var processListener: ProcessListener
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        albumsViewModel = ViewModelProvider(this, factory).get(AlbumsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_album, container, false)
        processListener = this
        bindUI()
        return root
    }

    private fun bindUI() = Coroutines.main {
        try {
            processListener.onStarted()
            albumsViewModel.albums.await().observe(this, Observer {
                initRecyclerView(it.toAlbumItem())
                progress_bar_albums.hide()
                processListener.onSuccess()
            })
        } catch (e: ApiException) {
            processListener.onFailed(e.message!!)
            context?.logd("Error occurred: ${e.message}")
        } catch (e: NoNetworkException) {
            processListener.onFailed(e.message!!)
            context?.logd("Error occurred: ${e.message}")
        }
    }

    private fun initRecyclerView(albumItems: List<AlbumItem>) {
        val adapter = GroupAdapter<ViewHolder>().apply {
            addAll(albumItems)
        }
        recycler_view_album.apply {
            setHasFixedSize(true)
            this.adapter = adapter
        }
    }

    private fun List<Album>.toAlbumItem(): List<AlbumItem> {
        return this.map {
            AlbumItem(it)
        }
    }

    override fun onStarted() {
        progress_bar_albums.show()
        context?.logd("onStarted: Getting data from api is STARTED...")
    }

    override fun onSuccess() {
        context?.logd("onSuccess: getting data from api is FINISHED successfully...")
        progress_bar_albums.hide()
    }

    override fun onFailed(message: String) {
        context?.logd("onFailed: getting data from api is FAILED...")
        progress_bar_albums.hide()
        fragment_album_parent_layout.snackbar("Process got error: $message")
    }
}