package ru.brewery.beer.ui

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
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import org.koin.androidx.compose.koinViewModel
import ru.brewery.beer.R
import ru.brewery.beer.ui.theme.boldTitleText
import ru.brewery.beer.ui.theme.normalText

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setContent {
            val viewModel = koinViewModel<MainViewModel>()

            val query = viewModel.filter.query.collectAsState("")
            val beers = viewModel.resultList.collectAsLazyPagingItems()

            val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
            var isSheetOpen by rememberSaveable {
                mutableStateOf(false)
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 12.dp, end = 12.dp, top = 18.dp, bottom = 12.dp)
                    .background(color = Color.White)
            ) {
                Text(
                    text = stringResource(R.string.Beer),
                    style = boldTitleText,
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(start = 8.dp)
                )
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp),
                    thickness = 1.dp, color = Color.DarkGray
                )
                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp))
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
                    textStyle = normalText,
                    leadingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.ic_search),
                            contentDescription = "Search beer",
                            tint = Color.DarkGray
                        )
                    },
                    placeholder = { Text(text = stringResource(id = R.string.Find), style = normalText) },
                    singleLine = true,
                    trailingIcon = {
                        Row(modifier = Modifier) {
                            IconButton(
                                onClick = {
                                          if (!query.value.isNullOrBlank()) viewModel.filter.setQuery(String())
                                },
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_close),
                                    contentDescription = "clear",
                                    tint = Color.DarkGray,
                                    modifier = Modifier.size(28.dp)
                                )
                            }
                            IconButton(
                                onClick = {
                                          // TODO
                                },
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_filter),
                                    contentDescription = "filter",
                                    tint = Color.DarkGray,
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
                                    tint = Color.DarkGray,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.DarkGray,
                        unfocusedBorderColor = Color.DarkGray
                        // TODO надо придумать че то
                    )
                )
                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .height(12.dp))
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
            }
        }
    }
}