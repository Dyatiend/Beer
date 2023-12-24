package ru.brewery.beer.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.brewery.beer.ui.theme.BeerTheme

@Composable
fun RangeSelector(
    minValue: Int,
    maxValue: Int,
    _startValue: Int = minValue,
    _endValue: Int = maxValue,
    onRangeSelected: (Int, Int) -> Unit
) {
    var startValue by remember { mutableStateOf(_startValue) }
    var endValue by remember { mutableStateOf(_endValue) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 16.dp, start = 28.dp, end = 28.dp)
    ) {
        // Start Slider
        Slider(
            value = startValue.toFloat(),
            onValueChange = {
                startValue = it.toInt()
                if (startValue < minValue) {
                    startValue = minValue
                } else if (startValue > endValue) {
                    startValue = endValue
                }
            },
            valueRange = minValue.toFloat()..endValue.toFloat(),
            steps = (endValue - minValue)
        )
        Text("Нижняя граница: $startValue", modifier = Modifier.padding(16.dp))

        Spacer(modifier = Modifier.height(16.dp))

        // End Slider
        Slider(
            value = endValue.toFloat(),
            onValueChange = {
                endValue = it.toInt()
                if (endValue > maxValue) {
                    endValue = maxValue
                } else if (endValue < startValue) {
                    endValue = startValue
                }
            },
            valueRange = startValue.toFloat()..maxValue.toFloat(),
            steps = (maxValue - startValue)
        )
        Text("Верхняя граница: $endValue", modifier = Modifier.padding(16.dp))

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { onRangeSelected(startValue, endValue) },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text("Подтвердить выбор")
        }
    }
}

@Composable
fun RangeSelectorExample() {
    var selectedRange by remember { mutableStateOf<Pair<Int, Int>?>(null) }

    RangeSelector(
        minValue = 0,
        maxValue = 100,
        onRangeSelected = { start, end ->
            selectedRange = Pair(start, end)
        }
    )

    selectedRange?.let { (start, end) ->
        Text("Selected Range: $start - $end", modifier = Modifier.padding(16.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun RangeSelectorPreview() {
    BeerTheme {
        RangeSelectorExample()
    }
}
