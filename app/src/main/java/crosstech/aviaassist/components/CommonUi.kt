package crosstech.aviaassist.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import crosstech.aviaassist.R
import crosstech.aviaassist.utils.DateTimeUtil.Companion.toFormattedString
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun AirportComponent(
    airportCode: String,
    time: LocalTime,
    modifier: Modifier = Modifier,
    airportName: String = "",
) {
    Column(
        modifier = modifier
            .padding(dimensionResource(id = R.dimen.padding_small)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = airportCode, style = MaterialTheme.typography.headlineLarge)
        if (airportName != "")
            Text(text = airportName, style = MaterialTheme.typography.headlineSmall)
        Text(
            text = time.toFormattedString(),
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AirportComponentPreview() {
    AirportComponent(airportCode = "HGH", time = LocalTime.of(12, 5))
}