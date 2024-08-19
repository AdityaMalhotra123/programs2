package hu.ait.weatherinfo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import hu.ait.weatherinfo.ui.navigation.MainNavigation
import hu.ait.weatherinfo.ui.screen.DetailsScreen
import hu.ait.weatherinfo.ui.screen.MainScreen
import hu.ait.weatherinfo.ui.theme.WeatherInfoTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherInfoTheme {
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
    startDestination: String = MainNavigation.MainScreen.route
) {
    NavHost(
        modifier = modifier, navController = navController, startDestination = startDestination
    ) {
        composable(MainNavigation.MainScreen.route) {
            MainScreen(
                onNavigateToDetails = { city ->
                    navController.navigate(
                        MainNavigation.DetailsScreen.createRoute(city))
                }
            )
        }

        composable(
            MainNavigation.DetailsScreen.route,
            // extract all and important arguments
            arguments = listOf(
                navArgument("city"){type = NavType.StringType},
        )) {
            val city = it.arguments?.getString("city")
            if (city != null) {
                DetailsScreen(cityName = city)
            }
        }
    }
}