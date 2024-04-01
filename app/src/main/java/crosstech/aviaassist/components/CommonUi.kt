package crosstech.aviaassist.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
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
    airportName: String = ""
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

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
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
    AirportComponent(airportCode = "HGH", time = LocalTime.of(12, 5), airportName = "杭州")
}

@Composable
fun Capsule(
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
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
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (icon != null) {
                Icon(
                    icon,
                    contentDescription = null,
                    modifier = Modifier
                        .size(16.dp)
                        .padding(
                            start = dimensionResource(id = R.dimen.padding_tiny)
                        )
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

@Composable
fun PaidTimeWidget(
    time: Int,
    multiplier: Double,
    reliable: Boolean,
    modifier: Modifier = Modifier
) {
    val effectiveTime = (time * multiplier).toInt()
    Row {
        VerticalLabel(
            title = "承包时间",
            content = { Text(text = time.toFormattedString(), style = MaterialTheme.typography.labelMedium) })
        VerticalLabel(
            title = "系数",
            content = {
                Capsule(
                    text = "x$multiplier"
                )
            })
        VerticalLabel(
            title = "计费时间",
            content = { Text(text = effectiveTime.toFormattedString(), style = MaterialTheme.typography.labelMedium) }
        )
        VerticalLabel(
            title = "可靠性",
            content = {
                if (reliable) {
                    Capsule(
                        icon = Icons.Default.AutoAwesome,
                        text = "源自码表",
                        outlineColor = colorResource(id = R.color.correct)
                    )
                } else {
                    Capsule(
                        icon = Icons.Default.QuestionMark,
                        text = "推算",
                        outlineColor = colorResource(id = R.color.wrong)
                    )
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PaidTimePreview() {
    PaidTimeWidget(time = 150, multiplier = 1.2, reliable = true)
}
@Preview(showBackground = true)
@Composable
fun PaidTimePreviewWrong() {
    PaidTimeWidget(time = 150, multiplier = 1.2, reliable = false)
}

@Composable fun VerticalLabel(
    title: String,
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(dimensionResource(id = R.dimen.padding_small))
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier
                .padding(bottom = dimensionResource(R.dimen.padding_small))
        )
        content()
    }
}

@Composable fun HorizontalLabel(
    title: String,
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically
){
    Row(
        verticalAlignment = verticalAlignment,
        modifier = modifier
            .padding(horizontal = dimensionResource(id = R.dimen.padding_small))
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier
                .padding(bottom = dimensionResource(R.dimen.padding_small))
        )
        Spacer(modifier = Modifier.weight(1f))
        content()
    }
}