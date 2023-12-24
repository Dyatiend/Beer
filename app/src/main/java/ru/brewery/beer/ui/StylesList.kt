package ru.brewery.beer.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.brewery.beer.data.domain.StyleModel

@Composable
fun MultiSelectList(
    items: List<StyleModel>,
    startSelectedItems: Set<StyleModel>,
    onSelectionChanged: (List<StyleModel>) -> Unit
) {
    var selectedItems by remember { mutableStateOf(startSelectedItems) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(350.dp)
            .padding(16.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .border(1.dp, MaterialTheme.colorScheme.onBackground)
        ) {
            items(items) { styleModel ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(text = styleModel.name, fontSize = 22.sp)
                    Spacer(modifier = Modifier.weight(1f))
                    Checkbox(
                        checked = selectedItems.contains(styleModel),
                        onCheckedChange = { isChecked ->
                            selectedItems = if (isChecked) {
                                selectedItems.toMutableSet().apply { add(styleModel) }
                            } else {
                                selectedItems.toMutableSet().apply { remove(styleModel) }
                            }
                            onSelectionChanged(selectedItems.toList())
                        },
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }
    }
}

