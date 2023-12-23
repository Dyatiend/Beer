package ru.brewery.beer.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ru.brewery.beer.R
import ru.brewery.beer.data.domain.BeerModel
import ru.brewery.beer.data.domain.LabelsModel
import ru.brewery.beer.data.domain.StyleModel
import ru.brewery.beer.ui.theme.normalText
import ru.brewery.beer.ui.theme.titleText

@Composable
fun BeerItem(
    beer : BeerModel,
    modifier: Modifier
) {
    OutlinedCard(
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = beer.labels.medium,
                contentDescription = null,
                modifier = Modifier
                    .weight(1f)
                    .height(150.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier
                .width(8.dp))
            Column(
                modifier = Modifier
                    .weight(3f)
                    .wrapContentHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = beer.name,
                    style = titleText,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                if (beer.abv != null) {
                    Text(
                        text = stringResource(R.string.Alcohol, beer.abv.toString()),
                        style = normalText,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                if (beer.ibu != null) {
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = stringResource(R.string.IBU, beer.ibu.toString()),
                        style = normalText,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = stringResource(R.string.Organic, if (beer.isOrganic) "Да" else "Нет"),
                    style = normalText,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = stringResource(R.string.Style, beer.style.name),
                    style = normalText,
                    modifier = Modifier.fillMaxWidth()
                )
                if (!beer.description.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = stringResource(R.string.Description, beer.description.toString()),
                        style = normalText,
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun BeerItemPreview() {
    BeerItem(
        beer = BeerModel(
            id = "1",
            name = "Старый мельник",
            description = "пиздатое пиво невьебенное ахуенное апупенное пенное не горькое вкусное наилучшее",
            abv = 4.6,
            ibu = 120.0,
            isOrganic = true,
            isRetired = false,
            labels = LabelsModel(
                icon = "",
                medium = "",
                large = ""
            ),
            style = StyleModel(
                id = 1,
                name = "Светлое не фильтрованное",
                description = "Нахуй не нужно (описание)"
            )
        ),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    )
}