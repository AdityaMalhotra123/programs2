package hu.ait.weatherinfo.ui.screen

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CityViewModel @Inject constructor(

) : ViewModel() {

    private var cityList =
        mutableStateListOf<String>()

    fun addCity(city: String) {
        viewModelScope.launch(Dispatchers.IO) {
            cityList.add(city)
        }
    }

    fun removeCity(city: String) {
        viewModelScope.launch(Dispatchers.IO) {
            cityList.remove(city)
        }
    }

    fun getAllCityList(): List<String> {
        return cityList
    }


}