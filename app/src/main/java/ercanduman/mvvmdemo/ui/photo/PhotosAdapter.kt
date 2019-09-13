package ercanduman.mvvmdemo.ui.photo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ercanduman.mvvmdemo.R
import ercanduman.mvvmdemo.data.db.entities.photo.Photo
import kotlinx.android.synthetic.main.list_item_photo.view.*

class PhotosAdapter : ListAdapter<Photo, PhotosAdapter.PhotoViewHolder>(diffCallback) {
    object diffCallback : DiffUtil.ItemCallback<Photo>() {
        override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_photo, parent, false)
        return PhotoViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val photo = getItem(position) ?: return

        holder.photoTitle.text = photo.title
        holder.photoPhotoId.text = photo.id.toString()
        holder.photoAlbumId.text = photo.albumId.toString()

        Glide.with(holder.itemView.context)
            .load(photo.url)
            .placeholder(R.drawable.ic_launcher_background)
            .into(holder.photoImage)
    }

    class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val photoImage = itemView.list_item_photo_url
        val photoTitle = itemView.list_item_photo_title
        val photoPhotoId = itemView.list_item_photo_id
        val photoAlbumId = itemView.list_item_photo_album_id
    }
}