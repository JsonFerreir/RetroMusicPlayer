/*
 * Copyright (c) 2020 Hemanth Savarla.
 *
 * Licensed under the GNU General Public License v3
 *
 * This is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 */
package cat.dam.yeison.retromusic.glide

import android.graphics.drawable.Drawable
import android.widget.ImageView
import cat.dam.yeison.appthemehelper.util.ATHUtil
import cat.dam.yeison.retromusic.glide.palette.BitmapPaletteTarget
import cat.dam.yeison.retromusic.glide.palette.BitmapPaletteWrapper
import cat.dam.yeison.retromusic.util.ColorUtil
import com.bumptech.glide.request.transition.Transition

abstract class SingleColorTarget(view: ImageView) : BitmapPaletteTarget(view) {

    private val defaultFooterColor: Int
        get() = ATHUtil.resolveColor(view.context, androidx.appcompat.R.attr.colorControlNormal)

    abstract fun onColorReady(color: Int)

    override fun onLoadFailed(errorDrawable: Drawable?) {
        super.onLoadFailed(errorDrawable)
        onColorReady(defaultFooterColor)
    }

    override fun onResourceReady(
        resource: BitmapPaletteWrapper,
        transition: Transition<in BitmapPaletteWrapper>?
    ) {
        super.onResourceReady(resource, transition)
        onColorReady(
            ColorUtil.getColor(
                resource.palette,
                ATHUtil.resolveColor(view.context, androidx.appcompat.R.attr.colorPrimary)
            )
        )
    }
}
