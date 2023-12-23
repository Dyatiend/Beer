package ru.brewery.beer.ui

import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.collectAsLazyPagingItems
import org.koin.androidx.compose.koinViewModel
import ru.brewery.beer.R
import ru.brewery.beer.ui.theme.boldTitleText
import ru.brewery.beer.ui.theme.normalText
import kotlin.random.Random

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
                                onClick = { /*TODO*/ },
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_filter),
                                    contentDescription = "filter",
                                    tint = Color.DarkGray,
                                    modifier = Modifier.size(28.dp)
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
            }
        }
    }
}

@Composable
fun ColorBox(
    modifier : Modifier = Modifier,
    updateColor : (Color) -> Unit
) {
    Box(
        modifier = modifier
            .background(Color.Red)
            .clickable {
                updateColor.invoke(
                    Color(
                        Random.nextFloat(),
                        Random.nextFloat(),
                        Random.nextFloat(),
                        1f
                    )
                )
            }
    ) {

    }
}

@Composable
fun ImageCard(
    painter : Painter,
    contentDescription : String,
    title : String,
    modifier : Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp
        )
    ) {
        Box(
            modifier = Modifier.height(200.dp)
        ) {
            Image(
                painter = painter,
                contentDescription = contentDescription,
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black),
                            startY = 300f
                        )
                    )
            ) {

            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                contentAlignment = Alignment.BottomCenter,
            ) {
                Text(
                    text = title,
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 16.sp,
                        fontFamily = FontFamily.Cursive
                    )
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {

}

//val painter = painterResource(id = R.drawable.knight)
//val description = "Dark Knight"
//val title = "Legendary: Dark Knight"
//Box(
//modifier = Modifier
//.fillMaxWidth(0.5f)
//.padding(start = 12.dp, top = 12.dp)
//) {
//    ImageCard(
//        painter = painter,
//        contentDescription = description,
//        title = title
//    )
//}



//Box(
//modifier = Modifier
//.fillMaxSize()
//.background(Color(0xFF101010))
//) {
//    Text(
//        modifier = Modifier
//            .fillMaxWidth(),
//        text = buildAnnotatedString {
//            withStyle(
//                style = SpanStyle(
//                    color = Color.LightGray
//                )
//            ) {
//                append("Jetpack")
//            }
//            append(" compose")
//        },
//        color = Color.White,
//        fontSize = 24.sp,
//        fontFamily = FontFamily.SansSerif,
//        fontWeight = FontWeight.Bold,
//        fontStyle = FontStyle.Italic,
//        textAlign = TextAlign.Center,
//        textDecoration = TextDecoration.LineThrough
//    )
//}


//Column(
//modifier = Modifier.fillMaxSize()
//) {
//    val color = remember {
//        mutableStateOf(Color.Yellow)
//    }
//    ColorBox(
//        modifier = Modifier
//            .weight(1f)
//            .fillMaxSize(),
//    ) {
//        color.value = it
//    }
//    Box(
//        modifier = Modifier
//            .background(color.value)
//            .weight(1f)
//            .fillMaxSize()
//    ) {
//
//    }
//}

//var text by remember {
//    mutableStateOf(TextFieldValue(""))
//}
//Column(
//modifier = Modifier
//.fillMaxSize()
//.padding(start = 16.dp, end = 16.dp),
//verticalArrangement = Arrangement.Center,
//horizontalAlignment = Alignment.CenterHorizontally
//) {
//    OutlinedTextField(
//        value = text,
//        onValueChange = {
//            text = it
//        },
//        label = {
//            Text(text = "Search Beer")
//        },
//        singleLine = true,
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(4.dp)
//    )
//    Spacer(modifier = Modifier.height(8.dp))
//    Divider(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(start = 4.dp, end = 4.dp),
//        color = Color.DarkGray,
//        thickness = 1.dp
//    )
//    Spacer(modifier = Modifier.height(8.dp))
//    Button(
//        modifier = Modifier
//            .wrapContentSize(),
//        onClick = {
//            Toast.makeText(this@MainActivity, text.text, Toast.LENGTH_SHORT).show()
//        }
//    ) {
//        Text(text = "Click on me")
//    }
//}


//LazyColumn(
//) {
//    itemsIndexed(
//        listOf("This", "is", "program", "Jetpack", "Compose")
//    ) {index, item ->
//        Text(
//            text = "$item",
//            fontSize = 24.sp,
//            fontWeight = FontWeight.Bold,
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(horizontal = 16.dp, vertical = 4.dp)
//                .border(
//                    width = 1.dp,
//                    color = Color.LightGray,
//                    shape = RoundedCornerShape(16.dp)
//                ),
//            textAlign = TextAlign.Center
//        )
//    }
//}