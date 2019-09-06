package ercanduman.mvvmdemo.ui.album

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ercanduman.mvvmdemo.R
import ercanduman.mvvmdemo.util.Coroutines
import ercanduman.mvvmdemo.util.toast
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

        Coroutines.main {
            val albumList = albumsViewModel.albums.await()
            albumList.observe(this, Observer { albums ->
                context?.toast(albums.size.toString())
                val stringBuilder = StringBuilder()
                if (albums != null && albums.isNotEmpty()) {
                    albums.forEach { album ->
                        stringBuilder.append(album).append("\n\n")
                    }
                }
                text_album.text = stringBuilder.toString()
            })
        }
        return root
    }
}