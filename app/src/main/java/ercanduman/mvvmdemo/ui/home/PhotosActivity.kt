package ercanduman.mvvmdemo.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import ercanduman.mvvmdemo.R
import ercanduman.mvvmdemo.databinding.ActivityPhotosBinding
import ercanduman.mvvmdemo.ui.ProcessListener
import ercanduman.mvvmdemo.util.toast

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
        toast("Process started...")
    }

    override fun onSuccess() {
        toast("Process finished successfully")
    }

    override fun onFailed(message: String) {
        toast("Process got error: $message")
    }
}
