package cn.roygao.wifiposition

//import androidx.compose.material.MaterialTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import cn.roygao.wifiposition.ui.theme.WifiPositionTheme

private const val REQUEST_CODE = 1

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //ActivityCompat.requestPermissions(this,
        //    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
        //    REQUEST_CODE)
        setContent {
            WifiPositionTheme {
                // A surface container using the 'background' color from the theme
                Surface(tonalElevation = 5.dp, modifier = Modifier.fillMaxHeight()) {
                    AppNavHost()
                }
            }
        }
    }
}