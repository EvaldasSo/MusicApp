package com.example.musicapp.presentation.navigation


import androidx.compose.foundation.layout.*

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource

import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow

import androidx.compose.ui.unit.dp
import com.example.musicapp.R
import com.example.musicapp.presentation.util.ConvertSecondsToMMSS

@Composable
fun NavItem(
    timeInSec: Int,
    title: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(dimensionResource(id = R.dimen.value_8dp)),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.alignByBaseline()
        )

        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.alignByBaseline()) {

            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(
                    text = ConvertSecondsToMMSS(timeInSec),
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.subtitle2,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }

            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.value_8dp)))
            IconButton(
                onClick = onClick
            ) {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowRight, contentDescription = null

                )
            }

        }
    }
}

