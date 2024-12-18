package cat.dam.yeison.retromusic.model.smartplaylist

import cat.dam.yeison.retromusic.App
import cat.dam.yeison.retromusic.R
import cat.dam.yeison.retromusic.model.Song
import kotlinx.parcelize.Parcelize

@Parcelize
class ShuffleAllPlaylist : AbsSmartPlaylist(
    name = App.getContext().getString(R.string.action_shuffle_all),
    iconRes = R.drawable.ic_shuffle
) {
    override fun songs(): List<Song> {
        return songRepository.songs()
    }
}