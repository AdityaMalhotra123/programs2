package hu.ait.andwallet.navigation

sealed class MainNavigation(val route: String) {
    object MainScreen : MainNavigation("mainscreen")
    object SummaryScreen : MainNavigation(
        "summaryscreen?income={income}&expense={expense}") {
        fun createRoute(income: Int, expense: Int) : String {
            return "summaryscreen?income=$income&expense=$expense"
        }
    }
}