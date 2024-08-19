package hu.ait.weatherinfo.ui.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.ait.weatherinfo.data.WeatherItem
import hu.ait.weatherinfo.network.WeatherAPI
import javax.inject.Inject

@HiltViewModel
class WeatherDetailsViewModel @Inject constructor(
    var weatherAPI: WeatherAPI
) : ViewModel() {
    var weatherUiState: WeatherUiState by mutableStateOf(WeatherUiState.Init)
    suspend fun getWeatherInfo(cityName: String, units: String, appId: String) {
        weatherUiState = WeatherUiState.Loading

        try {
            val result = weatherAPI.getWeather(cityName, units, appId)
            weatherUiState = WeatherUiState.Success(result)
        } catch (e: Exception) {
            weatherUiState = WeatherUiState.Error(e.message!!)
        }
    }

}

sealed interface WeatherUiState {
    object Init : WeatherUiState
    object Loading : WeatherUiState
    data class Success(val weatherResult: WeatherItem) : WeatherUiState
    data class Error(val errorMsg: String) : WeatherUiState
}