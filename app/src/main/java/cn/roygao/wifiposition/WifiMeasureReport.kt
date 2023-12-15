package cn.roygao.wifiposition

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WifiMeasureReport (positionName: String?, angle: Float?) {
    val list = listOf<WifiMeasureData>(
        WifiMeasureData("abc", 3.5f),
        WifiMeasureData("abc", 3.5f),
        WifiMeasureData("abc", 3.5f),
        WifiMeasureData("abc", 3.5f),
        WifiMeasureData("abc", 3.5f),
        WifiMeasureData("abc", 3.5f),
        WifiMeasureData("abc", 3.5f)
    )
    Column() {
        Text(
            text = stringResource(id = R.string.report_title),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(id = R.string.report_position_name)+": "+positionName,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(10.dp)
        )
        LazyColumn(
            Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 4.dp)
        ){
            item {
                ItemHeader()
            }
            itemsIndexed(list) { index: Int, item: WifiMeasureData ->
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
            .weight(5f)
            .padding(10.dp))
        Text(text = stringResource(R.string.report_header_strength), fontWeight = FontWeight.Bold, modifier = Modifier
            .weight(5f)
            .padding(10.dp))
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
    var modifier: Modifier = Modifier.fillMaxWidth()
    Row(
        modifier = if (index%2 == 0) modifier.background(Color.LightGray) else modifier
    ) {
        Text(text = item.bssId, modifier = Modifier
            .weight(5f)
            .padding(10.dp))
        Text(text = item.signalStrength.toString(), modifier = Modifier
            .weight(5f)
            .padding(10.dp))
    }
    Divider(
        color = Color.LightGray,
        modifier = Modifier
            .height(1.dp)
            .fillMaxHeight()
            .fillMaxWidth()
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewMeasureReportScreen() {
    WifiMeasureReport("grid_1", 123.5f)
}