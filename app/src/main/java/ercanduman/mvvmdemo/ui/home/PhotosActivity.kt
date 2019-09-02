package ercanduman.mvvmdemo.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import ercanduman.mvvmdemo.R
import ercanduman.mvvmdemo.data.db.entities.Photo
import ercanduman.mvvmdemo.databinding.ActivityPhotosBinding
import ercanduman.mvvmdemo.ui.ProcessListener
import ercanduman.mvvmdemo.util.hide
import ercanduman.mvvmdemo.util.show
import ercanduman.mvvmdemo.util.toast
import kotlinx.android.synthetic.main.activity_photos.*

class PhotosActivity : AppCompatActivity(), ProcessListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dataBinding: ActivityPhotosBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_photos)
        val viewModel = ViewModelProvider(this).get(PhotosViewModel::class.java)
        dataBinding.viewmodel = viewModel
        viewModel.processListener = this
    }

    override fun onStarted() {
        progress_bar.show()
        toast("Process started...")
    }

    override fun onSuccess(photos: List<Photo>) {
        progress_bar.hide()
        toast("Process finished successfully")
        val stringBuilder = StringBuilder()
        photos.forEach { photo ->
            stringBuilder.append(photo.toString())
        }
        activity_content.text = stringBuilder.toString()
    }

    override fun onFailed(message: String) {
        progress_bar.hide()
        toast("Process got error: $message")
    }
}
