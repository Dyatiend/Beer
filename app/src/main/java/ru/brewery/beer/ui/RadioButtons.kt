package ru.brewery.beer.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import ru.brewery.beer.R
import ru.brewery.beer.data.domain.Order
import ru.brewery.beer.data.domain.Sort
import ru.brewery.beer.ui.theme.normalText
import ru.brewery.beer.ui.theme.titleText

@Composable
fun RadioButtons(
    viewModel : MainViewModel = koinViewModel(),
    modifier : Modifier
) {
    val config = viewModel.config.collectAsState(null)
    val sorts = viewModel.filter.sort.collectAsState(null)
    Column(
        modifier = modifier
    ) {
        Text(text = stringResource(id = R.string.Sorted), style = titleText, modifier = Modifier.wrapContentSize())
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "По умолчанию",
                style = normalText,
                modifier = Modifier.wrapContentSize()
            )
            RadioButton(
                selected = sorts.value?.sort == null || sorts.value?.sort == Sort.NAME,
                onClick = {
                    if (sorts.value?.sort != null || sorts.value?.sort != Sort.NAME) {
                        viewModel.filter.setSort(null, null)
                    }
                },
                colors = RadioButtonDefaults.colors(
                    selectedColor = Color.DarkGray,
                    unselectedColor = Color.DarkGray
                )
            )
        }
        config.value?.data?.sortValues?.forEach { sortType ->
            when (sortType) {
                Sort.NAME -> {}
                Sort.ABV -> {
                    config.value?.data?.orderValues?.forEach { orderType ->
                        when (orderType) {
                            Order.ASC -> {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentHeight(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = stringResource(id = R.string.ByAscAlcohol),
                                        style = normalText,
                                        modifier = Modifier.wrapContentSize()
                                    )
                                    RadioButton(
                                        selected = sorts.value?.sort == Sort.ABV && sorts.value?.order == Order.ASC,
                                        onClick = {
                                            if (sorts.value?.sort != Sort.ABV || sorts.value?.order != Order.ASC) {
                                                viewModel.filter.setSort(Sort.ABV, Order.ASC)
                                            }
                                        },
                                        colors = RadioButtonDefaults.colors(
                                            selectedColor = Color.DarkGray,
                                            unselectedColor = Color.DarkGray
                                        )
                                    )
                                }
                            }
                            Order.DESC -> {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentHeight(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = stringResource(id = R.string.ByDescAlcohol),
                                        style = normalText,
                                        modifier = Modifier.wrapContentSize()
                                    )
                                    RadioButton(
                                        selected = sorts.value?.sort == Sort.ABV && sorts.value?.order == Order.DESC,
                                        onClick = {
                                            if (sorts.value?.sort != Sort.ABV || sorts.value?.order != Order.DESC) {
                                                viewModel.filter.setSort(Sort.ABV, Order.DESC)
                                            }
                                        },
                                        colors = RadioButtonDefaults.colors(
                                            selectedColor = Color.DarkGray,
                                            unselectedColor = Color.DarkGray
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
                Sort.IBU -> {
                    config.value?.data?.orderValues?.forEach { orderType ->
                        when (orderType) {
                            Order.ASC -> {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentHeight(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = stringResource(id = R.string.ByAscIBU),
                                        style = normalText,
                                        modifier = Modifier.wrapContentSize()
                                    )
                                    RadioButton(
                                        selected = sorts.value?.sort == Sort.IBU && sorts.value?.order == Order.ASC,
                                        onClick = {
                                            if (sorts.value?.sort != Sort.IBU || sorts.value?.order != Order.ASC) {
                                                viewModel.filter.setSort(Sort.IBU, Order.ASC)
                                            }
                                        },
                                        colors = RadioButtonDefaults.colors(
                                            selectedColor = Color.DarkGray,
                                            unselectedColor = Color.DarkGray
                                        )
                                    )
                                }
                            }
                            Order.DESC -> {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentHeight(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = stringResource(id = R.string.ByDescIBU),
                                        style = normalText,
                                        modifier = Modifier.wrapContentSize()
                                    )
                                    RadioButton(
                                        selected = sorts.value?.sort == Sort.IBU && sorts.value?.order == Order.DESC,
                                        onClick = {
                                            if (sorts.value?.sort != Sort.IBU || sorts.value?.order != Order.DESC) {
                                                viewModel.filter.setSort(Sort.IBU, Order.DESC)
                                            }
                                        },
                                        colors = RadioButtonDefaults.colors(
                                            selectedColor = Color.DarkGray,
                                            unselectedColor = Color.DarkGray
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}