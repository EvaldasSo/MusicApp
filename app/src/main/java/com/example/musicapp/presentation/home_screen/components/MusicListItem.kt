package com.example.musicapp.presentation.home_screen.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.musicapp.R
import com.example.musicapp.domain.model.MusicListing
import com.example.musicapp.presentation.util.ConvertSecondsToMMSS

@Composable
fun GroupMusic(
    onClick: (String) -> Unit,
    groupTitle: String,
    musicList: List<MusicListing>
) {
    Column() {
        Card(
            elevation = dimensionResource(id = R.dimen.value_16dp)
        ) {
            Column() {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = dimensionResource(id = R.dimen.value_16dp)),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = groupTitle,
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier.alignByBaseline()
                    )
                    Button(
                        modifier = Modifier.alignByBaseline(),
                        onClick = { onClick(groupTitle) }
                    ) {
                        Text(
                            text = stringResource(R.string.btn_text_see_all),
                            style = MaterialTheme.typography.button
                        )
                    }
                }
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {

                    itemsIndexed(musicList) { index, items ->
                        if (index > 4) return@itemsIndexed

                        GroupItem(items)
                    }
                }

            }

        }
    }
}

@Composable
fun GroupItem(
    music: MusicListing
) {
    Card(
        elevation = dimensionResource(id = R.dimen.value_16dp),
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.value_8dp))
            .wrapContentSize()
    ) {
        Column(
            modifier = Modifier
                .width(dimensionResource(id = R.dimen.value_180dp))
                .padding(dimensionResource(id = R.dimen.value_8dp)),
        ) {
            AsyncImage(
                model = music.thumbnail,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(dimensionResource(id = R.dimen.value_150dp))
                    .clip(MaterialTheme.shapes.medium)
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.value_4dp)))

            Text(
                text = music.description,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.caption
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    Text(
                        text = stringResource(R.string.size_in_mb, music.sizeInMb),
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.overline,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                }
                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    Text(
                        text = ConvertSecondsToMMSS(music.timeInSec),
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.overline,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                }
            }
        }
    }

}



