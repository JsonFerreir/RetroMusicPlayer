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
package cat.dam.yeison.retromusic.dialogs

import android.app.Dialog
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import cat.dam.yeison.retromusic.EXTRA_PLAYLIST_ID
import cat.dam.yeison.retromusic.R
import cat.dam.yeison.retromusic.db.PlaylistEntity
import cat.dam.yeison.retromusic.extensions.accentColor
import cat.dam.yeison.retromusic.extensions.colorButtons
import cat.dam.yeison.retromusic.extensions.extraNotNull
import cat.dam.yeison.retromusic.extensions.materialDialog
import cat.dam.yeison.retromusic.fragments.LibraryViewModel
import cat.dam.yeison.retromusic.fragments.ReloadType
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class RenamePlaylistDialog : DialogFragment() {

    private val libraryViewModel by activityViewModel<LibraryViewModel>()

    companion object {
        fun create(playlistEntity: PlaylistEntity): RenamePlaylistDialog {
            return RenamePlaylistDialog().apply {
                arguments = bundleOf(
                    EXTRA_PLAYLIST_ID to playlistEntity
                )
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val playlistEntity = extraNotNull<PlaylistEntity>(EXTRA_PLAYLIST_ID).value
        val layout = layoutInflater.inflate(R.layout.dialog_playlist, null)
        val inputEditText: TextInputEditText = layout.findViewById(R.id.actionNewPlaylist)
        val nameContainer: TextInputLayout = layout.findViewById(R.id.actionNewPlaylistContainer)
        nameContainer.accentColor()
        inputEditText.setText(playlistEntity.playlistName)
        return materialDialog(R.string.rename_playlist_title)
            .setView(layout)
            .setNegativeButton(android.R.string.cancel, null)
            .setPositiveButton(R.string.action_rename) { _, _ ->
                val name = inputEditText.text.toString()
                if (name.isNotEmpty()) {
                    libraryViewModel.renameRoomPlaylist(playlistEntity.playListId, name)
                    libraryViewModel.forceReload(ReloadType.Playlists)
                } else {
                    nameContainer.error = "Playlist name should'nt be empty"
                }
            }
            .create()
            .colorButtons()
    }
}
