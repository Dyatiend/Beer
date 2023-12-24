package ru.brewery.beer.ui

import android.media.MediaPlayer
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.collectAsLazyPagingItems
import org.koin.androidx.compose.koinViewModel
import ru.brewery.beer.R
import ru.brewery.beer.data.domain.StyleModel
import ru.brewery.beer.ui.theme.BeerTheme

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        val player = MediaPlayer.create(this, R.raw.pivo)

        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setContent {
            BeerTheme {
                player.start()

                val viewModel = koinViewModel<MainViewModel>()

                val query = viewModel.filter.query.collectAsState("")
                val beers = viewModel.resultList.collectAsLazyPagingItems()

                val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
                var isSheetOpen by rememberSaveable {
                    mutableStateOf(false)
                }

                val secondSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
                var isSecondSheetOpen by rememberSaveable {
                    mutableStateOf(false)
                }

                var selectedAbvRange by remember { mutableStateOf<Pair<Int, Int>?>(null) }
                var selectedIbuRange by remember { mutableStateOf<Pair<Int, Int>?>(null) }

                var isOrganic by rememberSaveable {
                    mutableStateOf(false)
                }
                var isRetired by rememberSaveable {
                    mutableStateOf(false)
                }


                val config = viewModel.config.collectAsState(null)
                val styles = viewModel.styles.collectAsState(null)
                var selectedStyles by remember { mutableStateOf(emptySet<StyleModel>()) }

                val thirdSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
                var isThirdSheetOpen by rememberSaveable {
                    mutableStateOf(false)
                }

                val fourthSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
                var isFourthSheetOpen by rememberSaveable {
                    mutableStateOf(false)
                }




                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                ) {


                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 12.dp, end = 12.dp, top = 18.dp, bottom = 12.dp)
                    ) {
                        Text(
                            text = "Пиво",
                            fontSize = 32.sp,
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier
                                .wrapContentSize()
                                .padding(start = 8.dp)
                        )
                        Divider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 20.dp),
                            thickness = 1.dp, color = MaterialTheme.colorScheme.onBackground
                        )
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(20.dp)
                        )
                        OutlinedTextField(
                            value = query.value ?: String(),
                            onValueChange = {
                                viewModel.filter.setQuery(it)
                            },
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .padding(horizontal = 8.dp),

                            leadingIcon = {
                                Icon(
                                    painter = painterResource(R.drawable.ic_search),
                                    contentDescription = "Search beer",
                                    tint = MaterialTheme.colorScheme.onBackground
                                )
                            },
                            placeholder = {
                                Text(
                                    text = stringResource(id = R.string.Find),

                                    )
                            },
                            singleLine = true,
                            trailingIcon = {
                                Row(modifier = Modifier) {
                                    IconButton(
                                        onClick = {
                                            if (!query.value.isNullOrBlank()) viewModel.filter.setQuery(
                                                String()
                                            )
                                        },
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_close),
                                            contentDescription = "clear",
                                            tint = MaterialTheme.colorScheme.onBackground,
                                            modifier = Modifier.size(28.dp)
                                        )
                                    }
                                    IconButton(
                                        onClick = {
                                            isSecondSheetOpen = true
                                        },
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_filter),
                                            contentDescription = "filter",
                                            tint = MaterialTheme.colorScheme.onBackground,
                                            modifier = Modifier.size(28.dp)
                                        )
                                    }
                                    IconButton(
                                        onClick = {
                                            isSheetOpen = true
                                        },
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_sort),
                                            contentDescription = "sort",
                                            tint = MaterialTheme.colorScheme.onBackground,
                                            modifier = Modifier.size(24.dp)
                                        )
                                    }
                                }
                            },
                        )
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(12.dp)
                        )
                        BeerLazyList(beers = beers)
                        if (isSheetOpen) {
                            ModalBottomSheet(
                                onDismissRequest = {
                                    isSheetOpen = false
                                },
                                sheetState = sheetState,
                                modifier = Modifier
                                    .wrapContentHeight()
                                    .fillMaxWidth()
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 12.dp, end = 12.dp, bottom = 8.dp)
                                ) {
                                    RadioButtons(
                                        viewModel = viewModel,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .wrapContentHeight()
                                    )
                                }
                            }
                        }

                        if (isSecondSheetOpen) {
                            ModalBottomSheet(
                                onDismissRequest = {
                                    isSecondSheetOpen = false
                                },
                                sheetState = secondSheetState,
                                modifier = Modifier
                                    .wrapContentHeight()
                                    .fillMaxWidth()
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 12.dp, end = 12.dp, bottom = 8.dp)
                                ) {
                                    Text(
                                        fontSize = 22.sp,
                                        text = "Стили пива"
                                    )
                                    MultiSelectList(
                                        items = styles.value?.data ?: emptyList(),
                                        startSelectedItems = selectedStyles,
                                        onSelectionChanged = {
                                            selectedStyles = it.toSet()
                                            viewModel.filter.styles.value =
                                                it.map(StyleModel::id).takeIf { it.isNotEmpty() }
                                        }
                                    )

                                    Button(
                                        onClick = {
                                            isThirdSheetOpen = true
                                        },
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Text(
                                            text = "Крепкость: ${
                                                selectedAbvRange?.let { (start, end) ->
                                                    "от $start до $end"
                                                } ?: "По умолчанию"
                                            }"
                                        )
                                    }
                                    Button(
                                        onClick = {
                                            isFourthSheetOpen = true
                                        },
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Text(
                                            text = "Горькость: ${
                                                selectedIbuRange?.let { (start, end) ->
                                                    "от $start до $end"
                                                } ?: "По умолчанию"
                                            }"
                                        )
                                    }

                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            "Только органическое",
                                            fontSize = 22.sp,
                                            modifier = Modifier.padding(4.dp)
                                        )
                                        Spacer(modifier = Modifier.weight(1f))
                                        Switch(
                                            checked = isOrganic,
                                            onCheckedChange = {
                                                isOrganic = it
                                                viewModel.filter.isOrganic.value =
                                                    if (it) true else null
                                            }
                                        )
                                    }

                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            "Снято с продаж",
                                            fontSize = 22.sp,
                                            modifier = Modifier.padding(4.dp)
                                        )
                                        Spacer(modifier = Modifier.weight(1f))
                                        Switch(
                                            checked = isRetired,
                                            onCheckedChange = {
                                                isRetired = it
                                                viewModel.filter.isRetired.value =
                                                    if (it) true else null
                                            }
                                        )
                                    }
                                }
                            }
                        }

                        if (isThirdSheetOpen) {
                            ModalBottomSheet(
                                onDismissRequest = {
                                    isThirdSheetOpen = false
                                },
                                sheetState = thirdSheetState,
                                modifier = Modifier
                                    .wrapContentHeight()
                                    .fillMaxWidth()
                            ) {
                                RangeSelector(
                                    minValue = config.value?.data?.abvMin?.toInt() ?: 0,
                                    maxValue = config.value?.data?.abvMax?.toInt() ?: 100,
                                    _startValue = selectedAbvRange?.first
                                        ?: config.value?.data?.abvMin?.toInt() ?: 0,
                                    _endValue = selectedAbvRange?.second
                                        ?: config.value?.data?.abvMax?.toInt() ?: 100,
                                    onRangeSelected = { start, end ->
                                        selectedAbvRange = Pair(start, end)
                                        viewModel.filter.abvMin.value = start.toDouble()
                                        viewModel.filter.abvMax.value = end.toDouble()
                                        isThirdSheetOpen = false
                                    }
                                )
                            }
                        }

                        if (isFourthSheetOpen) {
                            ModalBottomSheet(
                                onDismissRequest = {
                                    isFourthSheetOpen = false
                                },
                                sheetState = fourthSheetState,
                                modifier = Modifier
                                    .wrapContentHeight()
                                    .fillMaxWidth()
                            ) {
                                RangeSelector(
                                    minValue = config.value?.data?.ibuMin?.toInt() ?: 0,
                                    maxValue = config.value?.data?.ibuMax?.toInt() ?: 1000,
                                    _startValue = selectedIbuRange?.first
                                        ?: config.value?.data?.ibuMin?.toInt() ?: 0,
                                    _endValue = selectedIbuRange?.second
                                        ?: config.value?.data?.ibuMax?.toInt() ?: 1000,
                                    onRangeSelected = { start, end ->
                                        selectedIbuRange = Pair(start, end)
                                        viewModel.filter.ibuMin.value = start.toDouble()
                                        viewModel.filter.ibuMax.value = end.toDouble()
                                        isFourthSheetOpen = false
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}