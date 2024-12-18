package cat.dam.yeison.retromusic.model

import cat.dam.yeison.retromusic.repository.LastAddedRepository
import cat.dam.yeison.retromusic.repository.SongRepository
import cat.dam.yeison.retromusic.repository.TopPlayedRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

abstract class AbsCustomPlaylist(
    id: Long,
    name: String
) : Playlist(id, name), KoinComponent {

    abstract fun songs(): List<Song>

    protected val songRepository by inject<SongRepository>()

    protected val topPlayedRepository by inject<TopPlayedRepository>()

    protected val lastAddedRepository by inject<LastAddedRepository>()
}