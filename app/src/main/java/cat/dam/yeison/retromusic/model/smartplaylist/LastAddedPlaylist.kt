package cat.dam.yeison.retromusic.model.smartplaylist

import cat.dam.yeison.retromusic.App
import cat.dam.yeison.retromusic.R
import cat.dam.yeison.retromusic.model.Song
import kotlinx.parcelize.Parcelize

@Parcelize
class LastAddedPlaylist : AbsSmartPlaylist(
    name = App.getContext().getString(R.string.last_added),
    iconRes = R.drawable.ic_library_add
) {
    override fun songs(): List<Song> {
        return lastAddedRepository.recentSongs()
    }
}