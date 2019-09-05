package ercanduman.mvvmdemo.ui.photo

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ercanduman.mvvmdemo.R
import ercanduman.mvvmdemo.databinding.ActivityPhotosBinding
import ercanduman.mvvmdemo.ui.ProcessListener
import ercanduman.mvvmdemo.util.*
import kotlinx.android.synthetic.main.activity_photos.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class PhotosActivity : AppCompatActivity(), ProcessListener, KodeinAware {
    override val kodein by kodein()
    private val factory: PhotosViewModelFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dataBinding: ActivityPhotosBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_photos)
        val viewModel = ViewModelProvider(this, factory).get(PhotosViewModel::class.java)
        dataBinding.viewmodel = viewModel
        viewModel.processListener = this

        viewModel.getAllPhotos().observe(this, Observer { photos ->
            if (photos != null && photos.isNotEmpty()) {
                activity_parent_layout.snackbar("Data from DB!")
                val stringBuilder = StringBuilder()
                photos.forEach { photo ->
                    stringBuilder.append(photo.toString())
                }
                activity_content.text = stringBuilder.toString()
            } else {
                activity_parent_layout.snackbar("No data found in DB!")
            }
        })
    }

    override fun onStarted() {
        logd("onStarted: Getting data from api is STARTED...")
        progress_bar.show()
        toast("Process started...")
    }

    override fun onSuccess() {
        logd("onSuccess: getting data from api is FINISHED successfully...")
        progress_bar.hide()
        toast("Process finished successfully")
/*        val stringBuilder = StringBuilder()
        photos.forEach { photo ->
            stringBuilder.append(photo.toString())
        }
        activity_content.text = stringBuilder.toString()*/
    }

    override fun onFailed(message: String) {
        logd("onSuccess: getting data from api is FAILED...")
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
