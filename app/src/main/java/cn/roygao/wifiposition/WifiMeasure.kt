package cn.roygao.wifiposition

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import cn.roygao.wifiposition.ui.theme.WifiPositionTheme
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@Composable
fun MeasureScreen(
    navController: NavController,
    measureViewModel: WifiMeasureViewModel = viewModel()
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var angle by remember { mutableStateOf<Int>(0) }

    DisposableEffect(Unit) {
        val dataManager = SensorDataManager(context)
        dataManager.init()

        val job = scope.launch {
            dataManager.data
                .receiveAsFlow()
                .onEach { angle = it }
                .collect()
        }

        onDispose {
            dataManager.cancel()
            job.cancel()
        }
    }


    //val measureUiState = measureViewModel.uiState.collectAsState()
    Column(modifier = Modifier
        .padding(all = 8.dp)
        .fillMaxSize()) {
        Text(
            text = stringResource(R.string.screen_title),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(8.dp))
        val imageModifier = Modifier
            .height(150.dp)
            .fillMaxWidth()
            .border(BorderStroke(1.dp, MaterialTheme.colorScheme.primary))
            .background(MaterialTheme.colorScheme.background, RectangleShape)
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
            value = angle.toString(),
            onValueChange = { },
            label = { Text(text = stringResource(R.string.label_current_angle), style = MaterialTheme.typography.bodyMedium)},
            readOnly = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { navController.navigate("report/${measureViewModel.positionName}/$angle") },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text("Measure")
        }
    }
}


@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark"
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "DefaultPreviewLight"
)
@Composable
fun PreviewMeasureScreen() {
    WifiPositionTheme {
        Surface(tonalElevation = 5.dp) {
            MeasureScreen(rememberNavController())
        }
    }
}