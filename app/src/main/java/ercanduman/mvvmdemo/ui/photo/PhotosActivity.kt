package ercanduman.mvvmdemo.ui.photo

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ercanduman.mvvmdemo.R
import ercanduman.mvvmdemo.data.db.AppDatabase
import ercanduman.mvvmdemo.data.db.entities.Photo
import ercanduman.mvvmdemo.data.network.JsonPlaceHolderApi
import ercanduman.mvvmdemo.data.network.NetworkConnectionInterceptor
import ercanduman.mvvmdemo.data.repository.PhotosRepository
import ercanduman.mvvmdemo.databinding.ActivityPhotosBinding
import ercanduman.mvvmdemo.ui.ProcessListener
import ercanduman.mvvmdemo.util.hide
import ercanduman.mvvmdemo.util.show
import ercanduman.mvvmdemo.util.snackbar
import ercanduman.mvvmdemo.util.toast
import kotlinx.android.synthetic.main.activity_photos.*

class PhotosActivity : AppCompatActivity(), ProcessListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val networkConnectionInterceptor = NetworkConnectionInterceptor(this)
        val api = JsonPlaceHolderApi(networkConnectionInterceptor)
        val appDatabase = AppDatabase(this)
        val repository = PhotosRepository(api, appDatabase)
        val factory = PhotoViewModelFactory(repository)

        val dataBinding: ActivityPhotosBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_photos)
        val viewModel = ViewModelProvider(this, factory).get(PhotosViewModel::class.java)
        dataBinding.viewmodel = viewModel
        viewModel.processListener = this

        viewModel.getAllPhotos().observe(this, Observer { photos ->
            if (photos != null && photos.isNotEmpty()) {
                activity_parent_layout.snackbar("Data from DB!")
                photos.forEach {
                    val stringBuilder = StringBuilder()
                    photos.forEach { photo ->
                        stringBuilder.append(photo.toString())
                    }
                    activity_content.text = stringBuilder.toString()
                }
            } else {
                activity_parent_layout.snackbar("No data found in DB!")
            }
        })
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
        activity_parent_layout.snackbar("Process got error: $message")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_photo_activity, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_get_data) {

            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
