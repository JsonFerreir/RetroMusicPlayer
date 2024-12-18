package cat.dam.yeison.retromusic.interfaces

import android.view.View
import cat.dam.yeison.retromusic.model.Genre

interface IGenreClickListener {
    fun onClickGenre(genre: Genre, view: View)
}