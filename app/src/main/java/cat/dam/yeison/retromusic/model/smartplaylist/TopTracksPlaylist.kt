package cat.dam.yeison.retromusic.model.smartplaylist

import cat.dam.yeison.retromusic.App
import cat.dam.yeison.retromusic.R
import cat.dam.yeison.retromusic.model.Song
import kotlinx.parcelize.Parcelize

@Parcelize
class TopTracksPlaylist : AbsSmartPlaylist(
    name = App.getContext().getString(R.string.my_top_tracks),
    iconRes = R.drawable.ic_trending_up
) {
    override fun songs(): List<Song> {
        return topPlayedRepository.topTracks()
    }
}