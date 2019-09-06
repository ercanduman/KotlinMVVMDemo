package ercanduman.mvvmdemo.ui.photo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ercanduman.mvvmdemo.R
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
        photosViewModel.albumId = 3
        bindUI()
        return root
    }

    private fun bindUI() = Coroutines.main {
        try {
            processListener.onStarted()
            photosViewModel.allPhotos.await().observe(this, Observer {
                text_photo.text = it.size.toString()
                progress_bar_photos.hide()
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

    override fun onStarted() {
        progress_bar_photos.show()
        context?.logd("onStarted: Getting data from api is STARTED...")
    }

    override fun onSuccess() {
        context?.logd("onSuccess: getting data from api is FINISHED successfully...")
        progress_bar_photos.hide()
    }

    override fun onFailed(message: String) {
        context?.logd("onFailed: getting data from api is FAILED...")
        progress_bar_photos.hide()
        fragment_photo_parent_layout.snackbar("Process got error: $message")
    }
}