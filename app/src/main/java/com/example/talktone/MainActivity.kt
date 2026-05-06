package com.example.talktone

import android.os.Bundle
import android.speech.tts.TextToSpeech
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
import java.util.Locale

class MainActivity : ComponentActivity() {

    private var tts: TextToSpeech? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        tts = TextToSpeech(this) { status ->
            if (status == TextToSpeech.SUCCESS) {
                tts?.language = Locale("am") // Amharic
            }
        }

        setContent {
            val appViewModel: AppViewModel = viewModel()
            val isDark by appViewModel.isDarkMode.collectAsState()
            val language by appViewModel.language.collectAsState()
            val streak by appViewModel.streak.collectAsState()
            val showCongrats by appViewModel.showCongrats.collectAsState()
            val isOnboarded by appViewModel.isOnboarded.collectAsState()
            val userProfile by appViewModel.userProfile.collectAsState()

            TalktoneTheme(darkTheme = isDark) {
                AppNavHost(
                    viewModel = appViewModel,
                    isDark = isDark,
                    language = language,
                    streak = streak,
                    showCongrats = showCongrats,
                    isOnboarded = isOnboarded,
                    userProfile = userProfile,
                    tts = tts
                )
            }
        }
    }

    override fun onDestroy() {
        tts?.stop()
        tts?.shutdown()
        super.onDestroy()
    }
}

@Composable
fun AppNavHost(
    viewModel: AppViewModel,
    isDark: Boolean,
    language: String,
    streak: com.example.talktone.data.ReadingStreakEntity?,
    showCongrats: Boolean,
    isOnboarded: Boolean,
    userProfile: com.example.talktone.data.UserProfile?,
    tts: TextToSpeech?
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Splash.route) {

        composable(Screen.Splash.route) {
            SplashScreen(onFinished = {
                val dest = if (!isOnboarded) Screen.Onboarding.route else Screen.Home.route
                navController.navigate(dest) {
                    popUpTo(Screen.Splash.route) { inclusive = true }
                }
            })
        }

        composable(Screen.Onboarding.route) {
            OnboardingScreen(
                viewModel = viewModel,
                onFinished = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Home.route) {
            HomeScreen(
                viewModel = viewModel, isDark = isDark, language = language,
                streak = streak, showCongrats = showCongrats, userProfile = userProfile,
                onNavigate = { route -> navController.navigate(route) }
            )
        }

        composable(Screen.Poems.route) {
            LiteratureListScreen(
                items = AmharicContent.poems,
                category = com.example.talktone.data.LiteratureCategory.POEM,
                language = language, isDark = isDark,
                onBack = { navController.popBackStack() },
                onItemClick = { id -> navController.navigate(Screen.PoemDetail.createRoute(id)) }
            )
        }

        composable(Screen.PoemDetail.route,
            arguments = listOf(navArgument("itemId") { type = NavType.IntType })
        ) { back ->
            val id = back.arguments?.getInt("itemId") ?: return@composable
            val item = AmharicContent.poems.find { it.id == id } ?: return@composable
            LiteratureDetailScreen(item = item, language = language, isDark = isDark,
                tts = tts, onBack = { navController.popBackStack() })
        }

        composable(Screen.Terets.route) {
            LiteratureListScreen(
                items = AmharicContent.terets,
                category = com.example.talktone.data.LiteratureCategory.TERET,
                language = language, isDark = isDark,
                onBack = { navController.popBackStack() },
                onItemClick = { id -> navController.navigate(Screen.TeretDetail.createRoute(id)) }
            )
        }

        composable(Screen.TeretDetail.route,
            arguments = listOf(navArgument("itemId") { type = NavType.IntType })
        ) { back ->
            val id = back.arguments?.getInt("itemId") ?: return@composable
            val item = AmharicContent.terets.find { it.id == id } ?: return@composable
            LiteratureDetailScreen(item = item, language = language, isDark = isDark,
                tts = tts, onBack = { navController.popBackStack() })
        }

        composable(Screen.Misale.route) {
            LiteratureListScreen(
                items = AmharicContent.misaleoch,
                category = com.example.talktone.data.LiteratureCategory.MISALE,
                language = language, isDark = isDark,
                onBack = { navController.popBackStack() },
                onItemClick = { id -> navController.navigate(Screen.MisaleDetail.createRoute(id)) }
            )
        }

        composable(Screen.MisaleDetail.route,
            arguments = listOf(navArgument("itemId") { type = NavType.IntType })
        ) { back ->
            val id = back.arguments?.getInt("itemId") ?: return@composable
            val item = AmharicContent.misaleoch.find { it.id == id } ?: return@composable
            LiteratureDetailScreen(item = item, language = language, isDark = isDark,
                tts = tts, onBack = { navController.popBackStack() })
        }

        composable(Screen.Novels.route) {
            LiteratureListScreen(
                items = AmharicContent.novels,
                category = com.example.talktone.data.LiteratureCategory.NOVEL,
                language = language, isDark = isDark,
                onBack = { navController.popBackStack() },
                onItemClick = { id -> navController.navigate(Screen.NovelDetail.createRoute(id)) }
            )
        }

        composable(Screen.NovelDetail.route,
            arguments = listOf(navArgument("itemId") { type = NavType.IntType })
        ) { back ->
            val id = back.arguments?.getInt("itemId") ?: return@composable
            val item = AmharicContent.novels.find { it.id == id } ?: return@composable
            LiteratureDetailScreen(item = item, language = language, isDark = isDark,
                tts = tts, onBack = { navController.popBackStack() })
        }

        composable(Screen.Quiz.route) {
            QuizScreen(viewModel = viewModel, language = language, isDark = isDark,
                onBack = { navController.popBackStack() })
        }

        composable(Screen.Quotes.route) {
            QuotesScreen(viewModel = viewModel, language = language, isDark = isDark,
                onBack = { navController.popBackStack() })
        }

        composable(Screen.BookReader.route) {
            BookReaderScreen(
                viewModel = viewModel, language = language, isDark = isDark,
                onBack = { navController.popBackStack() },
                onOpenBook = { id -> navController.navigate(Screen.ReadBook.createRoute(id)) }
            )
        }

        composable(Screen.ReadBook.route,
            arguments = listOf(navArgument("bookId") { type = NavType.IntType })
        ) { back ->
            val bookId = back.arguments?.getInt("bookId") ?: return@composable
            ReadBookScreen(bookId = bookId, viewModel = viewModel, language = language,
                isDark = isDark, onBack = { navController.popBackStack() })
        }

        composable(Screen.Podcast.route) {
            PodcastScreen(isDark = isDark, language = language,
                onBack = { navController.popBackStack() })
        }

        composable(Screen.BeginnerLearn.route) {
            BeginnerLearnScreen(isDark = isDark, onBack = { navController.popBackStack() })
        }

        composable(Screen.CreatorSubmit.route) {
            CreatorSubmitScreen(
                viewModel = viewModel, userProfile = userProfile,
                isDark = isDark, language = language,
                onBack = { navController.popBackStack() },
                onSubmitted = { navController.popBackStack() }
            )
        }

        composable(Screen.CommunityFeed.route) {
            CommunityFeedScreen(viewModel = viewModel, isDark = isDark, language = language,
                onBack = { navController.popBackStack() })
        }

        composable(Screen.Settings.route) {
            SettingsScreen(
                viewModel = viewModel, isDark = isDark, language = language,
                streak = streak, onBack = { navController.popBackStack() },
                onAdminClick = { navController.navigate(Screen.AdminLogin.route) }
            )
        }

        composable(Screen.AdminLogin.route) {
            AdminLoginScreen(
                viewModel = viewModel, isDark = isDark,
                onLoginSuccess = {
                    navController.navigate(Screen.AdminDashboard.route) {
                        popUpTo(Screen.AdminLogin.route) { inclusive = true }
                    }
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.AdminDashboard.route) {
            AdminDashboardScreen(
                viewModel = viewModel, isDark = isDark,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
