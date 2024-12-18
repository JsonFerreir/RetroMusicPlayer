package cat.dam.yeison.retromusic.interfaces

import cat.dam.yeison.retromusic.model.Album
import cat.dam.yeison.retromusic.model.Artist
import cat.dam.yeison.retromusic.model.Genre

interface IHomeClickListener {
    fun onAlbumClick(album: Album)

    fun onArtistClick(artist: Artist)

    fun onGenreClick(genre: Genre)
}