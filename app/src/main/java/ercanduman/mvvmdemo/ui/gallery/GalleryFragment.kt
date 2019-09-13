package ercanduman.mvvmdemo.ui.gallery

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
import ercanduman.mvvmdemo.ui.photo.PhotosAdapter
import ercanduman.mvvmdemo.util.*
import kotlinx.android.synthetic.main.fragment_gallery.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class GalleryFragment : Fragment(), KodeinAware, ProcessListener {
    override val kodein by kodein()
    private val factory: GalleryViewModelFactory by instance()

    private lateinit var galleryViewModel: GalleryViewModel
    lateinit var processListener: ProcessListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        galleryViewModel = ViewModelProvider(this, factory).get(GalleryViewModel::class.java)
        val rootView = inflater.inflate(R.layout.fragment_gallery, container, false)
        processListener = this
        bindUI()
        return rootView
    }

    private fun bindUI() = Coroutines.main {
        try {
            processListener.onStarted()
            galleryViewModel.allPhotos.await().observe(this, Observer { photos ->
                initRecyclerView(photos)
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

    private fun initRecyclerView(photos: List<Photo>) {
        context?.logd("GalleryFragment.initRecyclerView.Listed item size: ${photos.size}")
        val photosAdapter = PhotosAdapter()
        photosAdapter.submitList(photos)
        fragment_gallery_recycler_view.apply {
            setHasFixedSize(true)
            adapter = photosAdapter
        }
        fragment_gallery_parent_layout.snackbar("Listed item size: ${photos.size}")
    }

    override fun onStarted() {
        fragment_gallery_progress_bar.show()
        context?.logd("onStarted: Getting data from api is STARTED...")
    }

    override fun onSuccess() {
        context?.logd("onSuccess: getting data from api is FINISHED successfully...")
        fragment_gallery_progress_bar.hide()
    }

    override fun onFailed(message: String) {
        context?.logd("onFailed: getting data from api is FAILED...")
        fragment_gallery_progress_bar.hide()
        fragment_gallery_parent_layout.snackbar("Process got error: $message")
    }
}
