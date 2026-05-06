package com.example.talktone.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Onboarding : Screen("onboarding")
    object Home : Screen("home")
    object Poems : Screen("poems")
    object PoemDetail : Screen("poem_detail/{itemId}") { fun createRoute(id: Int) = "poem_detail/$id" }
    object Terets : Screen("terets")
    object TeretDetail : Screen("teret_detail/{itemId}") { fun createRoute(id: Int) = "teret_detail/$id" }
    object Misale : Screen("misale")
    object MisaleDetail : Screen("misale_detail/{itemId}") { fun createRoute(id: Int) = "misale_detail/$id" }
    object Novels : Screen("novels")
    object NovelDetail : Screen("novel_detail/{itemId}") { fun createRoute(id: Int) = "novel_detail/$id" }
    object Quiz : Screen("quiz")
    object Quotes : Screen("quotes")
    object BookReader : Screen("book_reader")
    object ReadBook : Screen("read_book/{bookId}") { fun createRoute(id: Int) = "read_book/$id" }
    object Podcast : Screen("podcast")
    object Settings : Screen("settings")
    object AdminLogin : Screen("admin_login")
    object AdminDashboard : Screen("admin_dashboard")
    object CreatorSubmit : Screen("creator_submit")
    object MySubmissions : Screen("my_submissions")
    object BeginnerLearn : Screen("beginner_learn")
    object CommunityFeed : Screen("community_feed")
}
