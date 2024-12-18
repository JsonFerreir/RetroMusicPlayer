package cat.dam.yeison.retromusic.model.smartplaylist

import cat.dam.yeison.retromusic.App
import cat.dam.yeison.retromusic.R
import cat.dam.yeison.retromusic.model.Song
import kotlinx.parcelize.Parcelize

@Parcelize
class NotPlayedPlaylist : AbsSmartPlaylist(
    name = App.getContext().getString(R.string.not_recently_played),
    iconRes = R.drawable.ic_audiotrack
) {
    override fun songs(): List<Song> {
        return topPlayedRepository.notRecentlyPlayedTracks()
    }
}