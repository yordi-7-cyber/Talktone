package com.example.talktone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.talktone.data.AmharicContent
import com.example.talktone.navigation.Screen
import com.example.talktone.ui.screens.*
import com.example.talktone.ui.theme.TalktoneTheme
import com.example.talktone.viewmodel.AppViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val appViewModel: AppViewModel = viewModel()
            val isDark by appViewModel.isDarkMode.collectAsState()
            val language by appViewModel.language.collectAsState()
            val streak by appViewModel.streak.collectAsState()
            val showCongrats by appViewModel.showCongrats.collectAsState()

            TalktoneTheme(darkTheme = isDark) {
                AppNavHost(
                    viewModel = appViewModel,
                    isDark = isDark,
                    language = language,
                    streak = streak,
                    showCongrats = showCongrats
                )
            }
        }
    }
}

@Composable
fun AppNavHost(
    viewModel: AppViewModel,
    isDark: Boolean,
    language: String,
    streak: com.example.talktone.data.ReadingStreakEntity?,
    showCongrats: Boolean
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(onFinished = {
                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Splash.route) { inclusive = true }
                }
            })
        }

        composable(Screen.Home.route) {
            HomeScreen(
                viewModel = viewModel,
                isDark = isDark,
                language = language,
                streak = streak,
                showCongrats = showCongrats,
                onNavigate = { route -> navController.navigate(route) }
            )
        }

        composable(Screen.Poems.route) {
            LiteratureListScreen(
                items = AmharicContent.poems,
                category = com.example.talktone.data.LiteratureCategory.POEM,
                language = language,
                isDark = isDark,
                onBack = { navController.popBackStack() },
                onItemClick = { id -> navController.navigate(Screen.PoemDetail.createRoute(id)) }
            )
        }

        composable(
            route = Screen.PoemDetail.route,
            arguments = listOf(navArgument("itemId") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("itemId") ?: return@composable
            val item = AmharicContent.poems.find { it.id == id } ?: return@composable
            LiteratureDetailScreen(
                item = item,
                language = language,
                isDark = isDark,
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Terets.route) {
            LiteratureListScreen(
                items = AmharicContent.terets,
                category = com.example.talktone.data.LiteratureCategory.TERET,
                language = language,
                isDark = isDark,
                onBack = { navController.popBackStack() },
                onItemClick = { id -> navController.navigate(Screen.TeretDetail.createRoute(id)) }
            )
        }

        composable(
            route = Screen.TeretDetail.route,
            arguments = listOf(navArgument("itemId") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("itemId") ?: return@composable
            val item = AmharicContent.terets.find { it.id == id } ?: return@composable
            LiteratureDetailScreen(
                item = item,
                language = language,
                isDark = isDark,
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Misale.route) {
            LiteratureListScreen(
                items = AmharicContent.misaleoch,
                category = com.example.talktone.data.LiteratureCategory.MISALE,
                language = language,
                isDark = isDark,
                onBack = { navController.popBackStack() },
                onItemClick = { id ->
                    val item = AmharicContent.misaleoch.find { it.id == id }
                    item?.let {
                        navController.navigate("misale_detail/$id")
                    }
                }
            )
        }

        composable(
            route = "misale_detail/{itemId}",
            arguments = listOf(navArgument("itemId") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("itemId") ?: return@composable
            val item = AmharicContent.misaleoch.find { it.id == id } ?: return@composable
            LiteratureDetailScreen(
                item = item,
                language = language,
                isDark = isDark,
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Quiz.route) {
            QuizScreen(
                viewModel = viewModel,
                language = language,
                isDark = isDark,
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Quotes.route) {
            QuotesScreen(
                viewModel = viewModel,
                language = language,
                isDark = isDark,
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.BookReader.route) {
            BookReaderScreen(
                viewModel = viewModel,
                language = language,
                isDark = isDark,
                onBack = { navController.popBackStack() },
                onOpenBook = { id -> navController.navigate(Screen.ReadBook.createRoute(id)) }
            )
        }

        composable(
            route = Screen.ReadBook.route,
            arguments = listOf(navArgument("bookId") { type = NavType.IntType })
        ) { backStackEntry ->
            val bookId = backStackEntry.arguments?.getInt("bookId") ?: return@composable
            ReadBookScreen(
                bookId = bookId,
                viewModel = viewModel,
                language = language,
                isDark = isDark,
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Settings.route) {
            SettingsScreen(
                viewModel = viewModel,
                isDark = isDark,
                language = language,
                streak = streak,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
