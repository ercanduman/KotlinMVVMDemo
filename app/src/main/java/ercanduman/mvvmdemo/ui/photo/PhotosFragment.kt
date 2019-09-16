package ercanduman.mvvmdemo.ui.photo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ercanduman.mvvmdemo.R
import ercanduman.mvvmdemo.data.db.entities.photo.Photo
import ercanduman.mvvmdemo.ui.ProcessListener
import ercanduman.mvvmdemo.util.*
import kotlinx.android.synthetic.main.fragment_photo.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class PhotosFragment : Fragment(), KodeinAware, ProcessListener {
    override val kodein by kodein()
    private val factory: PhotosViewModelFactory by instance()

    private lateinit var photosViewModel: PhotosViewModel
    lateinit var processListener: ProcessListener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        photosViewModel = ViewModelProvider(this, factory).get(PhotosViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_photo, container, false)
        processListener = this

        var albumId = -1
        // 1.way to get passed data
        // albumId = arguments?.getInt(BUNDLE_ALBUM_ID)

        // 2.way to get passed data
        arguments?.let {
            val safeArgs = PhotosFragmentArgs.fromBundle(it)
            albumId = safeArgs.albumId
        }

        context?.logd("Passed albumId: $albumId")
        photosViewModel.albumId = albumId
        bindUI(albumId)
        return root
    }

    private fun bindUI(albumId: Int?) = Coroutines.main {
        try {
            processListener.onStarted()
            photosViewModel.allAlbumPhotos.await().observe(this, Observer { items ->
                fragment_photo_text_photo.text = "Album ID: $albumId  -  Item Size: ${items.size} "
                initRecyclerView(items)
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

    private fun initRecyclerView(items: List<Photo>) {
        context?.logd("PhotosFragment.initRecyclerView.Listed item size: ${items.size}")
        val mAdapter = PhotosAdapter()
        mAdapter.submitList(items)
        fragment_photo_recycler_view.apply {
            setHasFixedSize(true)
            adapter = mAdapter
        }
        fragment_photo_parent_layout.snackbar("Listed item size: ${items.size}")
    }

    override fun onStarted() {
        fragment_photo_progress_bar.show()
        context?.logd("onStarted: Getting data from api is STARTED...")
    }

    override fun onSuccess() {
        context?.logd("onSuccess: getting data from api is FINISHED successfully...")
        fragment_photo_progress_bar.hide()
    }

    override fun onFailed(message: String) {
        context?.logd("onFailed: getting data from api is FAILED...")
        fragment_photo_progress_bar.hide()
        fragment_photo_parent_layout.snackbar("Process got error: $message")
    }
}