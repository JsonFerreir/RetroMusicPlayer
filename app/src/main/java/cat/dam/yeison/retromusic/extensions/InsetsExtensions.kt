package cat.dam.yeison.retromusic.extensions

import androidx.core.view.WindowInsetsCompat
import cat.dam.yeison.retromusic.util.PreferenceUtil
import cat.dam.yeison.retromusic.util.RetroUtil

fun WindowInsetsCompat?.getBottomInsets(): Int {
    return if (PreferenceUtil.isFullScreenMode) {
        return 0
    } else {
        this?.getInsets(WindowInsetsCompat.Type.systemBars())?.bottom ?: RetroUtil.navigationBarHeight
    }
}
