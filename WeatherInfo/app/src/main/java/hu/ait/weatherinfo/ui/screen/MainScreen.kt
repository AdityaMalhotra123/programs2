package hu.ait.weatherinfo.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.text.isDigitsOnly
import androidx.hilt.navigation.compose.hiltViewModel
import hu.ait.weatherinfo.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    cityViewModel: CityViewModel = hiltViewModel(),
    onNavigateToDetails: (String) -> Unit
) {

    var showAddDialog by rememberSaveable {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.weather_report))
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    showAddDialog = true
                },
                containerColor = MaterialTheme.colorScheme.secondary,
                shape = RoundedCornerShape(16.dp),
            ) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = stringResource(R.string.add),
                    tint = Color.White,
                )
            }
        },
        content = {
            CityListContent(
                Modifier.padding(it), cityViewModel, onNavigateToDetails
            )

            if (showAddDialog) {
                AddNewCityDialog(cityViewModel,
                    onDismissRequest = {
                        showAddDialog = false
                    })
            }
        }
    )
}

@Composable
fun CityListContent(
    modifier: Modifier,
    cityViewModel: CityViewModel,
    onNavigateToDetails: (String) -> Unit
) {
    val cityList : List<String> = cityViewModel.getAllCityList()

    Column(
        modifier = modifier
    ) {
        if (cityList.isEmpty()) {
            Text(text = stringResource(R.string.no_items))
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(cityList) {
                    CityCard(it,
                        onRemoveItem = { cityViewModel.removeCity(it) },
                        onNavigateToDetails = onNavigateToDetails
                    )
                }
            }
        }

    }
}

@Composable
fun CityCard(
    city: String,
    onRemoveItem: () -> Unit = {},
    onNavigateToDetails: (String) -> Unit
) {

    Card(
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        modifier = Modifier.padding(5.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(city, modifier = Modifier.fillMaxWidth(0.2f))
                Spacer(modifier = Modifier.fillMaxSize(0.55f))
                Spacer(modifier = Modifier.width(10.dp))
                Icon(
                    imageVector = Icons.Filled.Info,
                    contentDescription = stringResource(R.string.details),
                    modifier = Modifier.clickable {
                        onNavigateToDetails(city)
                    },
                    tint = Color.Black
                )
                Spacer(modifier = Modifier.width(10.dp))
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = stringResource(R.string.delete),
                    modifier = Modifier.clickable {
                        onRemoveItem()
                    },
                    tint = Color.Red
                )
            }
        }
    }
}

@Composable
fun AddNewCityDialog(
    cityViewModel: CityViewModel,
    onDismissRequest: () -> Unit
) {
    var city by rememberSaveable {
        mutableStateOf("")
    }
    var isValid by rememberSaveable { mutableStateOf(true) }

    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Add City",
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentSize(Alignment.Center),
                    textAlign = TextAlign.Center,
                )

                OutlinedTextField(
                    value = city,
                    onValueChange = {
                        city = it
                        isValid = it.isNotEmpty()
                    },
                    label = { Text(text = "Enter City here:") },
                    isError = !isValid
                )

                if (!isValid) {
                    Text(text = "Please enter a city name", color = Color.Red)
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(
                        enabled = isValid,
                        onClick = {
                            cityViewModel.addCity(city)
                            onDismissRequest()
                        }
                    ) {
                        Text(text = "Save")
                    }
                    TextButton(onClick = { onDismissRequest() }) {
                        Text(text = "Cancel")
                    }
                }

            }
        }
    }
}