package com.example.anythingapp

sealed class NavigationItem(var route: String, var icon: Int, var title: String) {
    object Home: NavigationItem("home", R.drawable.ic_baseline_home_24, "Home")
    object Music: NavigationItem("music", R.drawable.ic_baseline_music_note_24, "Music")
    object Movie: NavigationItem("movie", R.drawable.ic_baseline_movie_24, "Movie")
    object Books: NavigationItem("books", R.drawable.ic_baseline_menu_book_24, "Book")
    object Profile: NavigationItem("profile", R.drawable.ic_baseline_person_24, "Profile")
}
