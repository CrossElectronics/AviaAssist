package crosstech.aviaassist.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import crosstech.aviaassist.R
import crosstech.aviaassist.components.AirportComponent
import crosstech.aviaassist.components.CapsuleWithLineInMiddle
import crosstech.aviaassist.components.PaidTimeWidget
import crosstech.aviaassist.model.EvaluatedMission
import crosstech.aviaassist.model.FlightMission
import crosstech.aviaassist.utils.toFormattedString
import java.time.LocalDate

@Composable
fun FlightScreen(
    missionsByDate: Map<LocalDate, List<EvaluatedMission>>,
    airports: Map<String, String>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_small)),
    ) {
        for (missions in missionsByDate) {
            item {
                DailyMission(missions = missions, airports = airports)
            }
        }
    }
}

@Composable
fun DailyMission(
    missions: Map.Entry<LocalDate, List<EvaluatedMission>>,
    airports: Map<String, String>,
    modifier: Modifier = Modifier,
) {
    Card(
        elevation = CardDefaults.cardElevation(dimensionResource(id = R.dimen.elevation_shallow)),
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_small))
    ) {
        Column(
            modifier = Modifier
        ) {
            Text(
                text = missions.key.toFormattedString(),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .padding(
                        start = dimensionResource(id = R.dimen.padding_medium),
                        top = dimensionResource(id = R.dimen.padding_medium),
                        bottom = dimensionResource(id = R.dimen.padding_small)
                    )
            )
            Column(
                modifier = Modifier
            ) {
                for (mission in missions.value) {
                    MissionCard(mission = mission, airports = airports)
                }
            }
        }
    }
}

@Composable
fun MissionCard(
    mission: EvaluatedMission,
    airports: Map<String, String>,
    modifier: Modifier = Modifier,
) {
    val origAirportName = if (airports.containsKey(mission.originAirport)) airports[mission.originAirport] else ""
    val destAirportName = if (airports.containsKey(mission.destAirport)) airports[mission.destAirport] else ""

    var isExpanded by rememberSaveable { mutableStateOf(false) }

    ElevatedCard(
        modifier = modifier
            .padding(dimensionResource(id = R.dimen.padding_small))
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.animateContentSize(
                spring(
                    dampingRatio = Spring.DampingRatioNoBouncy,
                    stiffness = Spring.StiffnessLow
                )
            ),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.weight(0.3f))
                AirportComponent(
                    airportCode = mission.originAirport,
                    time = mission.takeoffDateTime.toLocalTime(),
                    modifier = Modifier
                        .weight(2f),
                    airportName = origAirportName ?: "",
                )
                Column(
                    modifier = Modifier
                        .padding(dimensionResource(id = R.dimen.padding_small))
                        .weight(1.5f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = mission.flightNumber,
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier
                            .padding(bottom = dimensionResource(id = R.dimen.padding_tiny))
                    )
                    CapsuleWithLineInMiddle(
                        text = mission.getDurationAsString()
                    )
                }
                AirportComponent(
                    airportCode = mission.destAirport,
                    time = mission.landingDateTime.toLocalTime(),
                    modifier = Modifier.weight(2f),
                    airportName = destAirportName ?: "",
                )
                Row(modifier = Modifier.weight(0.5f)) {
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(onClick = { isExpanded = !isExpanded },) {
                        Icon(if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore, null)
                    }
                }
            }

            if (isExpanded) {
                PaidTimeWidget(
                    time = mission.durationInMinutes,
                    multiplier = mission.multiplier,
                    reliable = mission.isAuthentic
                )
            }
        }
    }
}

@Preview
@Composable
fun MissionCardPreview() {
    val flt = FlightMission.parseListFromString("【2024-3-29】\nCA1740/B-1876  CTU09:15-11:55HGH")[0]
    MissionCard(mission = flt evaluateBy listOf(), mapOf())
}

@Preview(showBackground = true)
@Composable
fun FlightScreenPreview() {
    val flts = FlightMission.parseListFromString(
        "万益豪【七日机组排班,仅供参考】\n" +
                "【2024-3-28】\n" +
                "CA1739/B-1876  HGH21:25-00:25CTU\n" +
                "【2024-3-29】\n" +
                "CA1740/B-1876  CTU09:15-11:55HGH\n" +
                "【2024-3-30】\n" +
                "CA1743/B-6216  HGH06:30-08:55XIY\n" +
                "CA1744/B-6216  XIY10:05-12:25HGH"
    )
    val evalFlts = mutableListOf<EvaluatedMission>()
    flts.forEach { evalFlts.add(it evaluateBy listOf()) }
    FlightScreen(missionsByDate = evalFlts.groupBy { it.takeoffDateTime.toLocalDate() }, mapOf())
}