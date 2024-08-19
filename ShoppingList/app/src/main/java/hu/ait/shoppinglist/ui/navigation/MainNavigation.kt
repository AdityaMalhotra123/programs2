package hu.ait.shoppinglist.ui.navigation

import hu.ait.shoppinglist.data.ShoppingItem

sealed class MainNavigation(val route: String) {
    object MainScreen : MainNavigation("mainscreen")
    object SplashScreen : MainNavigation("splashscreen")
    object ItemScreen : MainNavigation("itemscreen?title={title}&description={description}&price={price}&status={status}") {
        fun createRoute(title: String, description: String, price: Float, status: Boolean) : String {
            return "itemscreen?title=${title}&description=${description}&price=${price}&status=${status}"
        }
    }
}