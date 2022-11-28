package com.example.musicapp.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.musicapp.R
import com.example.musicapp.domain.model.MusicListing
import com.example.musicapp.presentation.util.ConvertSecondsToMMSS

@Composable
fun MusicItem(
    musicItem: MusicListing,
    enableIcon: Boolean = false,
    showIcon: Boolean = false,
    onClickIcon: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = dimensionResource(id = R.dimen.value_4dp),
                horizontal = dimensionResource(id = R.dimen.value_8dp)
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = musicItem.title,
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.subtitle1,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
            )

            Row() {
                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    Text(
                        text = stringResource(R.string.size_in_mb, musicItem.sizeInMb),
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.body2,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                }
                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    Text(
                        text = "-",
                        Modifier.padding(horizontal = dimensionResource(id = R.dimen.value_4dp))
                    )
                }
                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    Text(
                        text = ConvertSecondsToMMSS(musicItem.timeInSec),
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.body2,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                }
            }

            Divider(color = Color.LightGray, thickness = 1.dp)
        }


        if (showIcon) {
            IconButton(onClick = onClickIcon) {
                Icon(
                    imageVector = if (enableIcon) Icons.Filled.Done else Icons.Outlined.AddCircle,
                    contentDescription = null
                )
            }
        }
    }
}
