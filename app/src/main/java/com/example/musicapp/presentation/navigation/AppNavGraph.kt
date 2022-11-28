package com.example.musicapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.musicapp.R
import com.example.musicapp.presentation.home_screen.HomeScreen

import com.example.musicapp.presentation.music_category_list.CategoryScreen
import com.example.musicapp.presentation.music_filesystem_list.MemoryScreen
import com.example.musicapp.presentation.music_memory_list.FilesystemScreen
import com.example.musicapp.presentation.navigation.AppDestinationsArgs.CATEGORY_TITLE

@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = AppDestinations.HOME_ROUTE,
    navActions: AppNavigationActions = remember(navController) {
        AppNavigationActions(navController)
    }
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {

        composable(AppDestinations.HOME_ROUTE) {
            HomeScreen(
                onGroupClick = { taskId ->
                    navActions.navigateToCategory(taskId)
                },
                navigationActions = navActions
            )
        }

        composable(
            AppDestinations.CATEGORY_ROUTE,
            arguments = listOf(
                navArgument(CATEGORY_TITLE) { type = NavType.StringType; nullable = true }
            )
        )
        { entry ->
            val categoryTitle = entry.arguments?.getString(CATEGORY_TITLE)
            CategoryScreen(
                topBarTitle = categoryTitle ?: stringResource(id = R.string.app_bar_category),
                onBack = { navController.popBackStack() }
            )
        }

        composable(AppDestinations.MEMORY_ROUTE) {
            MemoryScreen(
                topBarTitle = R.string.navigation_memory_title,
                onBack = { navActions.navigateToHome() }
            )
        }

        composable(AppDestinations.FILESYSTEM_ROUTE) {
            FilesystemScreen(
                topBarTitle = R.string.navigation_filesystem_title,
                onBack = { navActions.navigateToHome() }
            )
        }
    }
}