package hu.ait.shoppinglist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import hu.ait.shoppinglist.data.ShoppingItem
import hu.ait.shoppinglist.ui.navigation.MainNavigation
import hu.ait.shoppinglist.ui.screen.ItemScreen
import hu.ait.shoppinglist.ui.screen.MainScreen
import hu.ait.shoppinglist.ui.screen.SplashScreen
import hu.ait.shoppinglist.ui.theme.ShoppingListTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShoppingListTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainNavHost()
                }
            }
        }
    }
}

@Composable
fun MainNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = MainNavigation.SplashScreen.route
) {
    NavHost(
        modifier = modifier, navController = navController, startDestination = startDestination
    ) {
        composable(MainNavigation.SplashScreen.route) {
            SplashScreen(onNavigateToMain = {navController.navigate(MainNavigation.MainScreen.route)})
        }
        composable(MainNavigation.MainScreen.route) {
            MainScreen(onNavigateToItem = { title, description, price, status ->
                navController.navigate(
                    MainNavigation.ItemScreen.createRoute(title, description, price, status))
            })
        }
        composable(MainNavigation.ItemScreen.route,
            arguments = listOf(
                navArgument("title"){type = NavType.StringType},
                navArgument("description"){type = NavType.StringType},
                navArgument("price"){type = NavType.FloatType},
                navArgument("status"){type = NavType.BoolType}
            )) {

            val title = it.arguments?.getString("title")
            val description = it.arguments?.getString("description")
            val price = it.arguments?.getFloat("price")
            val status = it.arguments?.getBoolean("status")
            if (title != null && description != null && price != null && status != null) {
                ItemScreen(title = title, description = description, price = price, status = status)
            }
        }

    }
}