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
import ercanduman.mvvmdemo.util.Coroutines
import ercanduman.mvvmdemo.util.hide
import ercanduman.mvvmdemo.util.show
import kotlinx.android.synthetic.main.fragment_album.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class AlbumsFragment : Fragment(), KodeinAware {
    override val kodein by kodein()
    private val factory: AlbumsViewModelFactory by instance()
    private lateinit var albumsViewModel: AlbumsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        albumsViewModel = ViewModelProvider(this, factory).get(AlbumsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_album, container, false)

        bindUI()
        return root
    }

    private fun bindUI() = Coroutines.main {
        progress_bar_albums.show()
        albumsViewModel.albums.await().observe(this, Observer {
            initRecyclerView(it.toAlbumItem())
            progress_bar_albums.hide()
        })
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
}