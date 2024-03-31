package crosstech.aviaassist.components

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.KeyframesSpec
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import crosstech.aviaassist.R
import crosstech.aviaassist.utils.toFormattedString
import java.time.LocalTime

@Composable
fun AirportComponent(
    airportCode: String,
    time: LocalTime,
    modifier: Modifier = Modifier,
    airportName: String = "",
    displayAirportCode: Boolean = true
) {
    Column(
        modifier = modifier
            .padding(dimensionResource(id = R.dimen.padding_small)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = airportCode,
            style = MaterialTheme.typography.headlineLarge,
            //color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = alpha)
        )

        Row (
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = airportName,
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier
                    .padding(end = dimensionResource(id = R.dimen.padding_tiny))
            )
            Text(
                text = time.toFormattedString(),
                style = MaterialTheme.typography.labelLarge
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun AirportComponentPreview() {
    AirportComponent(airportCode = "HGH", airportName = "杭州", time = LocalTime.of(12, 5))
}

@Composable
fun Capsule(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int = 0,
    text: String = "",
    outlineColor: Color = MaterialTheme.colorScheme.tertiary
) {
    Surface(
        modifier = modifier
            .wrapContentWidth(),
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corner_rad)),
        border = BorderStroke(1.dp, outlineColor),
        color = MaterialTheme.colorScheme.background
    ) {
        if (icon != 0) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null
            )
        }
        Text(
            text = text,
            modifier = Modifier
                .padding(horizontal = dimensionResource(id = R.dimen.padding_small)),
            style = MaterialTheme.typography.labelSmall,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CapsulePreview() {
    Capsule(
        text = "02:15"
    )
}

@Composable
fun CapsuleWithLineInMiddle(
    modifier: Modifier = Modifier,
    text: String = "",
    outlineColor: Color = MaterialTheme.colorScheme.tertiary
) {
    Box(modifier = modifier.fillMaxWidth()) {
        Divider(
            thickness = 1.dp,
            color = outlineColor,
            modifier = Modifier
                .align(Alignment.Center)
        )
        Capsule(
            text = text,
            modifier = Modifier
                .padding(horizontal = dimensionResource(id = R.dimen.padding_small))
                .align(Alignment.Center)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CapsuleWithLineInMiddlePreview() {
    CapsuleWithLineInMiddle(
        text = "02:15"
    )
}