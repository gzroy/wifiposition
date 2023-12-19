package cn.roygao.wifiposition

import android.content.res.Configuration
import android.net.wifi.ScanResult
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cn.roygao.wifiposition.ui.theme.WifiPositionTheme
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WifiMeasureReport (positionName: String?, angle: String?) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var wifiScanResult by remember { mutableStateOf<List<WifiMeasureData>>(listOf(WifiMeasureData("-", 0))) }

    DisposableEffect(Unit) {
        val wifiScanService = WifiScanService(context)
        wifiScanService.init()

        val job = scope.launch {
            wifiScanService.data
                .receiveAsFlow()
                .onEach { wifiScanResult = it }
                .collect()
        }

        onDispose {
            wifiScanService.cancel()
            job.cancel()
        }
    }

    /*
    val wifiScanResult = listOf(
        WifiMeasureData("abc", 123),
        WifiMeasureData("abc", 123),
        WifiMeasureData("abc", 123),
        WifiMeasureData("abc", 123))

     */

    Column(modifier = Modifier.padding(all = 8.dp).fillMaxSize()) {
        Text(
            text = stringResource(id = R.string.report_title),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(id = R.string.report_position_name)+": "+positionName,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.secondary
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = stringResource(id = R.string.report_angle)+": "+angle,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.secondary
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = stringResource(id = R.string.report_result)+": ",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.secondary
        )
        Spacer(modifier = Modifier.height(4.dp))
        LazyColumn(
            Modifier.fillMaxWidth(),
            //contentPadding = PaddingValues(horizontal = 4.dp)
        ){
            stickyHeader {
                ItemHeader()
            }
            itemsIndexed(wifiScanResult) { index: Int, item: WifiMeasureData ->
                ItemRow(index, item)
            }
        }
    }
}

@Composable
fun ItemHeader() {
    Row(
        Modifier
            .fillMaxWidth()
            //.border(BorderStroke(0.5.dp, Color.Black))
    ) {
        Text(text = stringResource(R.string.report_header_bssid), fontWeight = FontWeight.Bold, modifier = Modifier
            .weight(5f))
        Text(text = stringResource(R.string.report_header_strength), fontWeight = FontWeight.Bold, modifier = Modifier
            .weight(5f))
    }
    Divider(
        color = Color.LightGray,
        modifier = Modifier
            .height(1.dp)
            .fillMaxHeight()
            .fillMaxWidth()
    )
}

@Composable
fun ItemRow(index: Int, item: WifiMeasureData) {
    val modifier: Modifier = Modifier.fillMaxWidth()
    Row(
        modifier = if (index%2 == 0) modifier.background(MaterialTheme.colorScheme.tertiaryContainer) else modifier
    ) {
        Text(text = item.bssId, modifier = Modifier
            .weight(5f))
        Text(text = item.signalStrength.toString(), modifier = Modifier
            .weight(5f))
    }
    Divider(
        color = Color.LightGray,
        modifier = Modifier
            .height(1.dp)
            .fillMaxHeight()
            .fillMaxWidth()
    )
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
fun PreviewMeasureReportScreen() {
    WifiPositionTheme {
        Surface(tonalElevation = 5.dp) {
            WifiMeasureReport("grid_1", "0")
        }
    }
}