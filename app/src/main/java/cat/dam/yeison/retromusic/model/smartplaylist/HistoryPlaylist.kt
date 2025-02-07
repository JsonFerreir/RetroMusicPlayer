package cat.dam.yeison.retromusic.model.smartplaylist

import cat.dam.yeison.retromusic.App
import cat.dam.yeison.retromusic.R
import cat.dam.yeison.retromusic.model.Song
import kotlinx.parcelize.Parcelize
import org.koin.core.component.KoinComponent

@Parcelize
class HistoryPlaylist : AbsSmartPlaylist(
    name = App.getContext().getString(R.string.history),
    iconRes = R.drawable.ic_history
), KoinComponent {

    override fun songs(): List<Song> {
        return topPlayedRepository.recentlyPlayedTracks()
    }
}