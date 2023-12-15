package cn.roygao.wifiposition

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

//onNavigateToReport: () -> Unit
@Composable
fun MeasureScreen(
    navController: NavController,
    measureViewModel: WifiMeasureViewModel = viewModel()
) {
    val ctx = LocalContext.current
    val sensorManager: SensorManager = ctx.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    val directionSensorEventListener = object: SensorEventListener {
        override fun onSensorChanged(event: SensorEvent?) {
            if (event?.sensor.type == Sensor.)
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
            TODO("Not yet implemented")
        }

    }



    //val measureUiState = measureViewModel.uiState.collectAsState()
    Column(modifier = Modifier.padding(all = 8.dp)) {
        Text(
            text = stringResource(R.string.screen_title),
            style = MaterialTheme.typography.titleLarge,
        )
        Spacer(modifier = Modifier.height(8.dp))
        val imageModifier = Modifier
            .height(150.dp)
            .fillMaxWidth()
            .border(BorderStroke(1.dp, Color.Black))
        Image(
            painter = painterResource(id = R.drawable.indooratlas),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = imageModifier
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = measureViewModel.positionName,
            onValueChange = { measureViewModel.updatePositionName(it) },
            label = { Text(text = stringResource(R.string.label_position_name), style = MaterialTheme.typography.bodyMedium)},
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = "123.5",
            onValueChange = { },
            label = { Text(text = stringResource(R.string.label_current_angle), style = MaterialTheme.typography.bodyMedium)},
            readOnly = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextButton(
            onClick = { navController.navigate("report/${measureViewModel.positionName}/123.5") },
            shape = RectangleShape,
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .border(1.5.dp, MaterialTheme.colorScheme.secondary, CircleShape)
        ) {
            Text(
                text = "Measure",
                style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMeasureScreen() {
    MeasureScreen(rememberNavController())
}