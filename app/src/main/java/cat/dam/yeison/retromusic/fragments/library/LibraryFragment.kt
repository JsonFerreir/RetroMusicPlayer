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
package cat.dam.yeison.retromusic.fragments.library

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.text.parseAsHtml
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import cat.dam.yeison.appthemehelper.ThemeStore
import cat.dam.yeison.appthemehelper.common.ATHToolbarActivity.getToolbarBackgroundColor
import cat.dam.yeison.appthemehelper.util.ToolbarContentTintHelper
import cat.dam.yeison.retromusic.R
import cat.dam.yeison.retromusic.databinding.FragmentLibraryBinding
import cat.dam.yeison.retromusic.dialogs.CreatePlaylistDialog
import cat.dam.yeison.retromusic.dialogs.ImportPlaylistDialog
import cat.dam.yeison.retromusic.extensions.setUpMediaRouteButton
import cat.dam.yeison.retromusic.extensions.whichFragment
import cat.dam.yeison.retromusic.fragments.base.AbsMainActivityFragment
import cat.dam.yeison.retromusic.model.CategoryInfo
import cat.dam.yeison.retromusic.util.PreferenceUtil

class LibraryFragment : AbsMainActivityFragment(R.layout.fragment_library) {

    private var _binding: FragmentLibraryBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentLibraryBinding.bind(view)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mainActivity.setBottomNavVisibility(true)
        mainActivity.setSupportActionBar(binding.toolbar)
        mainActivity.supportActionBar?.title = null
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigate(
                R.id.action_search,
                null,
                navOptions
            )
        }
        setupNavigationController()
        setupTitle()
    }

    private fun setupTitle() {
        val color = ThemeStore.accentColor(requireContext())
        val hexColor = String.format("#%06X", 0xFFFFFF and color)
        val appName = "Retro <span  style='color:$hexColor';>Music</span>".parseAsHtml()
        binding.appNameText.text = appName
    }

    private fun setupNavigationController() {
        val navHostFragment = whichFragment<NavHostFragment>(R.id.fragment_container)
        val navController = navHostFragment.navController
        val navInflater = navController.navInflater
        val navGraph = navInflater.inflate(R.navigation.library_graph)

        val categoryInfo: CategoryInfo = PreferenceUtil.libraryCategory.first { it.visible }
        if (categoryInfo.visible) {
            navGraph.setStartDestination(categoryInfo.category.id)
        }
        navController.graph = navGraph
        NavigationUI.setupWithNavController(mainActivity.navigationView, navController)
        navController.addOnDestinationChangedListener { _, _, _ ->
            binding.appBarLayout.setExpanded(true, true)
        }
    }

    override fun onPrepareMenu(menu: Menu) {
        ToolbarContentTintHelper.handleOnPrepareOptionsMenu(requireActivity(), binding.toolbar)
    }

    override fun onCreateMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
        ToolbarContentTintHelper.handleOnCreateOptionsMenu(
            requireContext(),
            binding.toolbar,
            menu,
            getToolbarBackgroundColor(binding.toolbar)
        )
        //Setting up cast button
        requireContext().setUpMediaRouteButton(menu)
    }

    override fun onMenuItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> findNavController().navigate(
                R.id.settings_fragment,
                null,
                navOptions
            )
            R.id.action_import_playlist -> ImportPlaylistDialog().show(
                childFragmentManager,
                "ImportPlaylist"
            )
            R.id.action_add_to_playlist -> CreatePlaylistDialog.create(emptyList()).show(
                childFragmentManager,
                "ShowCreatePlaylistDialog"
            )
        }
        return false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
