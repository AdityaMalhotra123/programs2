package hu.ait.weatherinfo.network

import hu.ait.weatherinfo.data.WeatherItem
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {
    @GET("data/2.5/weather")
    suspend fun getWeather(
        @Query("q") cityName: String,
        @Query("units") units: String,
        @Query("appid") apiKey: String
    ) : WeatherItem
}