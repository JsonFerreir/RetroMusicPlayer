package cat.dam.yeison.retromusic.views.insets

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import cat.dam.yeison.retromusic.util.PreferenceUtil
import dev.chrisbanes.insetter.applyInsetter

class InsetsConstraintLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    init {
        if (!isInEditMode && !PreferenceUtil.isFullScreenMode)
            applyInsetter {
                type(navigationBars = true) {
                    padding(vertical = true)
                }
            }
    }
}