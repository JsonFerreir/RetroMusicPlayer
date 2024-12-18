package cat.dam.yeison.retromusic.interfaces

import android.view.View
import cat.dam.yeison.retromusic.db.PlaylistWithSongs

interface IPlaylistClickListener {
    fun onPlaylistClick(playlistWithSongs: PlaylistWithSongs, view: View)
}