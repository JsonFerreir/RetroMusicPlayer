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
package cat.dam.yeison.retromusic.fragments.player.flat

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import cat.dam.yeison.appthemehelper.util.ColorUtil
import cat.dam.yeison.appthemehelper.util.MaterialValueHelper
import cat.dam.yeison.appthemehelper.util.ToolbarContentTintHelper
import cat.dam.yeison.retromusic.R
import cat.dam.yeison.retromusic.databinding.FragmentFlatPlayerBinding
import cat.dam.yeison.retromusic.extensions.colorControlNormal
import cat.dam.yeison.retromusic.extensions.drawAboveSystemBars
import cat.dam.yeison.retromusic.extensions.whichFragment
import cat.dam.yeison.retromusic.fragments.base.AbsPlayerFragment
import cat.dam.yeison.retromusic.fragments.player.PlayerAlbumCoverFragment
import cat.dam.yeison.retromusic.helper.MusicPlayerRemote
import cat.dam.yeison.retromusic.model.Song
import cat.dam.yeison.retromusic.util.PreferenceUtil
import cat.dam.yeison.retromusic.util.ViewUtil
import cat.dam.yeison.retromusic.util.color.MediaNotificationProcessor
import cat.dam.yeison.retromusic.views.DrawableGradient

class FlatPlayerFragment : AbsPlayerFragment(R.layout.fragment_flat_player) {
    override fun playerToolbar(): Toolbar {
        return binding.playerToolbar
    }

    private var valueAnimator: ValueAnimator? = null
    private lateinit var controlsFragment: FlatPlaybackControlsFragment
    private var lastColor: Int = 0
    override val paletteColor: Int
        get() = lastColor

    private var _binding: FragmentFlatPlayerBinding? = null
    private val binding get() = _binding!!


    private fun setUpSubFragments() {
        controlsFragment = whichFragment(R.id.playbackControlsFragment)
        val playerAlbumCoverFragment: PlayerAlbumCoverFragment =
            whichFragment(R.id.playerAlbumCoverFragment)
        playerAlbumCoverFragment.setCallbacks(this)
    }

    private fun setUpPlayerToolbar() {
        binding.playerToolbar.inflateMenu(R.menu.menu_player)
        binding.playerToolbar.setNavigationOnClickListener { requireActivity().onBackPressedDispatcher.onBackPressed() }
        binding.playerToolbar.setOnMenuItemClickListener(this)
        ToolbarContentTintHelper.colorizeToolbar(
            binding.playerToolbar,
            colorControlNormal(),
            requireActivity()
        )
    }

    private fun colorize(i: Int) {
        if (valueAnimator != null) {
            valueAnimator?.cancel()
        }

        valueAnimator = ValueAnimator.ofObject(ArgbEvaluator(), android.R.color.transparent, i)
        valueAnimator?.addUpdateListener { animation ->
            val drawable = DrawableGradient(
                GradientDrawable.Orientation.TOP_BOTTOM,
                intArrayOf(animation.animatedValue as Int, android.R.color.transparent), 0
            )
            _binding?.colorGradientBackground?.background = drawable
        }
        valueAnimator?.setDuration(ViewUtil.RETRO_MUSIC_ANIM_TIME.toLong())?.start()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFlatPlayerBinding.bind(view)
        setUpPlayerToolbar()
        setUpSubFragments()
        binding.playerToolbar.drawAboveSystemBars()
    }

    override fun onShow() {
        controlsFragment.show()
    }

    override fun onHide() {
        controlsFragment.hide()
    }

    override fun toolbarIconColor(): Int {
        val isLight = ColorUtil.isColorLight(paletteColor)
        return if (PreferenceUtil.isAdaptiveColor)
            MaterialValueHelper.getPrimaryTextColor(requireContext(), isLight)
        else
            colorControlNormal()
    }

    override fun onColorChanged(color: MediaNotificationProcessor) {
        lastColor = color.backgroundColor
        controlsFragment.setColor(color)
        libraryViewModel.updateColor(color.backgroundColor)
        ToolbarContentTintHelper.colorizeToolbar(
            binding.playerToolbar,
            colorControlNormal(),
            requireActivity()
        )
        if (PreferenceUtil.isAdaptiveColor) {
            colorize(color.backgroundColor)
        }
    }

    override fun onFavoriteToggled() {
        toggleFavorite(MusicPlayerRemote.currentSong)
    }

    override fun toggleFavorite(song: Song) {
        super.toggleFavorite(song)
        if (song.id == MusicPlayerRemote.currentSong.id) {
            updateIsFavorite()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
