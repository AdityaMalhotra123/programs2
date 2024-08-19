package hu.ait.weatherinfo.ui.navigation

sealed class MainNavigation(val route: String) {
    object MainScreen : MainNavigation("mainscreen")
    object DetailsScreen : MainNavigation("detailsscreen?city={city}") {
        fun createRoute(city: String) : String {
            return "detailsscreen?city=${city}"
        }
    }
}