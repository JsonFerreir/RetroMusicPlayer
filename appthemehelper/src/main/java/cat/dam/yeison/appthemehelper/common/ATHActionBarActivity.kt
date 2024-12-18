package cat.dam.yeison.appthemehelper.common

import androidx.appcompat.widget.Toolbar

import cat.dam.yeison.appthemehelper.util.ToolbarContentTintHelper

class ATHActionBarActivity : ATHToolbarActivity() {

    override fun getATHToolbar(): Toolbar? {
        return ToolbarContentTintHelper.getSupportActionBarView(supportActionBar)
    }
}
