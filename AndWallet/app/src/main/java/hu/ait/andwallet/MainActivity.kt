package hu.ait.andwallet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
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
import hu.ait.andwallet.navigation.MainNavigation
import hu.ait.andwallet.screen.MainScreen
import hu.ait.andwallet.screen.SummaryScreen
import hu.ait.andwallet.ui.theme.AndWalletTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndWalletTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //AndWalletNavHost()
                    DrawButtons()
                }
            }
        }
    }
}

@Composable
fun AndWalletNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = MainNavigation.MainScreen.route
) {
    NavHost(
        modifier = modifier, navController = navController, startDestination = startDestination
    ) {
        composable(MainNavigation.MainScreen.route) {
            MainScreen(
                onNavigateToSummary = { all, important ->
                    navController.navigate(
                        MainNavigation.SummaryScreen.createRoute(all, important)
                    )
                }
            )
        }

        composable(MainNavigation.SummaryScreen.route,
            // extract all and important arguments
            arguments = listOf(
                navArgument("income"){type = NavType.IntType},
                navArgument("expense"){type = NavType.IntType})
        ) {
            val numIncome = it.arguments?.getInt("income")
            val numExpense = it.arguments?.getInt("expense")
            if (numIncome != null && numExpense != null) {
                SummaryScreen(
                    numIncome = numIncome,
                    numExpense = numExpense
                )
            }
        }
    }
}



@Composable
fun DrawButtons() {
    Column (modifier = Modifier.fillMaxSize()){
        Button(onClick = {}, modifier = Modifier.fillMaxWidth().weight(1f)) {
            Text(text = "PRESS")
        }
        Button(onClick = {}, modifier = Modifier.fillMaxWidth().weight(1f)) {
            Text(text = "PRESS")
        }
        Row (modifier = Modifier.fillMaxWidth().weight(1f))
        {
            Button(onClick = {}, modifier = Modifier.fillMaxHeight().weight(1f)) {
                Text(text = "PRESS")
            }
            Spacer(modifier = Modifier.fillMaxHeight().weight(1f))
            Button(onClick = {}, Modifier.fillMaxHeight().weight(1f)) {
                Text(text = "PRESS")
            }
        }
    }
}

