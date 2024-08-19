package hu.ait.shoppinglist.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import hu.ait.shoppinglist.R
import hu.ait.shoppinglist.data.ShoppingItem
import kotlinx.coroutines.delay

@Composable
fun ItemScreen(
    title: String,
    description: String,
    price: Float,
    status: Boolean
) {
    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Text(text = "Title: ${title}")
        Text(text = "Description: ${description}")
        Text(text = "Price: ${price}")
        Text(text = "Status (Bought?): ${status}")
    }
}