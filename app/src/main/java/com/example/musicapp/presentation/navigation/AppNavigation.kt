package com.example.musicapp.presentation.navigation

import androidx.navigation.NavHostController
import com.example.musicapp.presentation.navigation.AppDestinationsArgs.CATEGORY_TITLE
import com.example.musicapp.presentation.navigation.AppScreens.CATEGORY_SCREEN
import com.example.musicapp.presentation.navigation.AppScreens.FILESYSTEM_SCREEN
import com.example.musicapp.presentation.navigation.AppScreens.MEMORY_SCREEN
import com.example.musicapp.presentation.navigation.AppScreens.MUSIC_SCREEN

private object AppScreens {
    const val MUSIC_SCREEN = "music"
    const val CATEGORY_SCREEN = "category"
    const val MEMORY_SCREEN = "memory"
    const val FILESYSTEM_SCREEN = "filesystem"
}

object AppDestinationsArgs {
    const val CATEGORY_TITLE = "categoryTitle"
}

object AppDestinations {
    const val HOME_ROUTE = MUSIC_SCREEN
    const val CATEGORY_ROUTE = "$CATEGORY_SCREEN/{$CATEGORY_TITLE}"
    const val MEMORY_ROUTE = MEMORY_SCREEN
    const val FILESYSTEM_ROUTE = FILESYSTEM_SCREEN
}

class AppNavigationActions(private val navController: NavHostController) {
    fun navigateToHome() {
        navController.navigate(AppDestinations.HOME_ROUTE) {
            popUpTo(AppDestinations.HOME_ROUTE) {
                inclusive = true
            }
        }
    }

    fun navigateToMemory() {
        navController.navigate(AppDestinations.MEMORY_ROUTE)
    }

    fun navigateToFilesystem() {
        navController.navigate(AppDestinations.FILESYSTEM_ROUTE)
    }

    fun navigateToCategory(categoryId: String) {
        navController.navigate("$CATEGORY_SCREEN/$categoryId")
    }

}
