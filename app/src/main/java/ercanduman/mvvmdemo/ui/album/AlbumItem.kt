package ercanduman.mvvmdemo.ui.album

import com.xwray.groupie.databinding.BindableItem
import ercanduman.mvvmdemo.R
import ercanduman.mvvmdemo.data.db.entities.album.Album
import ercanduman.mvvmdemo.databinding.ListItemAlbumBinding

/**
 * This object created for Groupie library
 * and this entity object will be displayed in RecyclerView
 */
data class AlbumItem(private val album: Album) : BindableItem<ListItemAlbumBinding>() {
    override fun getLayout() = R.layout.list_item_album

    override fun bind(viewBinding: ListItemAlbumBinding, position: Int) {
        viewBinding.album = album
    }
}