package hu.ait.weatherinfo.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import hu.ait.weatherinfo.R
import hu.ait.weatherinfo.data.WeatherItem


@Composable
fun DetailsScreen(
    cityName: String,
    weatherDetailsViewModel: WeatherDetailsViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        weatherDetailsViewModel.getWeatherInfo(cityName, "metric", "b661e1c284869dc0c98bc08e678c2d3e")
    }
    Column {
        when (weatherDetailsViewModel.weatherUiState) {
            is WeatherUiState.Init -> {}
            is WeatherUiState.Loading -> CircularProgressIndicator()
            is WeatherUiState.Success ->
                WeatherDetailsItem(
                    (weatherDetailsViewModel.weatherUiState as WeatherUiState.Success).weatherResult
                )
            is WeatherUiState.Error -> Text(
                text = stringResource(R.string.error)  + "${(weatherDetailsViewModel.weatherUiState as WeatherUiState.Error).errorMsg}"
                )
        }
    }
}

@Composable
fun WeatherDetailsItem (
    weatherResult: WeatherItem
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        weatherResult.name?.let {
            Text(
                text = it,
                fontWeight = FontWeight.Bold,
                fontSize = 45.sp
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("https://openweathermap.org/img/w/${ weatherResult.weather?.get(0)?.icon }.png")
                .crossfade(true)
                .build(),
            contentDescription = stringResource(R.string.image),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(10.dp))

        Text(
            text = stringResource(R.string.temperature) + weatherResult.main?.temp.toString(),
            fontSize = 15.sp
        )


        Spacer(modifier = Modifier.width(10.dp))

        Text(
            text = stringResource(R.string.max_temperature) + weatherResult.main?.tempMax.toString(),
            fontSize = 15.sp
        )
        Spacer(modifier = Modifier.width(10.dp))

        Text(
            text = stringResource(R.string.min_temperature) + weatherResult.main?.tempMin.toString(),
            fontSize = 15.sp
        )
        Spacer(modifier = Modifier.width(10.dp))

        Text(
            text = stringResource(R.string.pressure) + weatherResult.main?.pressure.toString(),
            fontSize = 15.sp
        )
    }
}