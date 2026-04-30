package com.example.talktone.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Home : Screen("home")
    object Poems : Screen("poems")
    object PoemDetail : Screen("poem_detail/{itemId}") {
        fun createRoute(id: Int) = "poem_detail/$id"
    }
    object Terets : Screen("terets")
    object TeretDetail : Screen("teret_detail/{itemId}") {
        fun createRoute(id: Int) = "teret_detail/$id"
    }
    object Misale : Screen("misale")
    object Quiz : Screen("quiz")
    object Quotes : Screen("quotes")
    object BookReader : Screen("book_reader")
    object ReadBook : Screen("read_book/{bookId}") {
        fun createRoute(id: Int) = "read_book/$id"
    }
    object Settings : Screen("settings")
}
