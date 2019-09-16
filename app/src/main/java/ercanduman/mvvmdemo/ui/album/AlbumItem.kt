package ercanduman.mvvmdemo.ui.album

import androidx.navigation.findNavController
import com.xwray.groupie.databinding.BindableItem
import ercanduman.mvvmdemo.R
import ercanduman.mvvmdemo.data.db.entities.album.Album
import ercanduman.mvvmdemo.databinding.ListItemAlbumBinding

/**
 * This object created for Groupie library
 * and this entity object will be displayed in RecyclerView
 */

const val BUNDLE_ALBUM_ID = "BUNDLE_ALBUM_ID"

data class AlbumItem(private val album: Album) : BindableItem<ListItemAlbumBinding>() {
    override fun getLayout() = R.layout.list_item_album

    override fun bind(viewBinding: ListItemAlbumBinding, position: Int) {
        viewBinding.album = album
        viewBinding.listItemContent.setOnClickListener {
            // 1.way to pass data
            // val albumIdBundle = bundleOf(BUNDLE_ALBUM_ID to album.id)
            // it.findNavController().navigate(R.id.action_nav_album_to_nav_photo, albumIdBundle)

            // 2.way to pass data
            val action = AlbumsFragmentDirections.actionNavAlbumToNavPhoto(album.id)
            it.findNavController().navigate(action)
        }
    }
}