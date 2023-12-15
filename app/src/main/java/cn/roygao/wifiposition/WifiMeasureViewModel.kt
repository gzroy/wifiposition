package cn.roygao.wifiposition

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class WifiMeasureViewModel : ViewModel() {
    //private val _uiState = MutableStateFlow(WifiMeasureData())
    //val uiState: StateFlow<WifiMeasureData> = _uiState.asStateFlow()

    var positionName by mutableStateOf("")
        private set

    fun updatePositionName(name: String) {
        positionName = name
    }
}