package cat.dam.yeison.retromusic.model.smartplaylist

import androidx.annotation.DrawableRes
import cat.dam.yeison.retromusic.R
import cat.dam.yeison.retromusic.model.AbsCustomPlaylist

abstract class AbsSmartPlaylist(
    name: String,
    @DrawableRes val iconRes: Int = R.drawable.ic_queue_music
) : AbsCustomPlaylist(
    id = PlaylistIdGenerator(name, iconRes),
    name = name
)