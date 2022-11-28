package com.example.musicapp.presentation.home_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.musicapp.R
import com.example.musicapp.presentation.home_screen.components.GroupMusic
import com.example.musicapp.presentation.navigation.AppNavigationActions
import com.example.musicapp.presentation.navigation.NavItem

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun HomeScreen(
    onGroupClick: (String) -> Unit,
    navigationActions: AppNavigationActions,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        LazyColumn(
            modifier = Modifier
                .weight(0.6f)
                .padding(
                    vertical = dimensionResource(id = R.dimen.value_16dp),
                    horizontal = dimensionResource(id = R.dimen.value_16dp)
                )
        ) {
            val musicListMap = state.music.groupBy { it.category }
            items(items = musicListMap.map { it.key }) { item ->
                musicListMap[item]?.let {
                    GroupMusic(
                        onClick = onGroupClick, item, it
                    )
                }
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.value_8dp)))
            }
        }

        if (state.error.isNotBlank()) {
            Text(
                text = state.error,
                color = MaterialTheme.colors.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = dimensionResource(id = R.dimen.value_20dp))
            )
        }

        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(alignment = Alignment.CenterHorizontally))
        }

        Column(
            modifier = Modifier.padding(
                bottom = dimensionResource(id = R.dimen.value_64dp),
                start = dimensionResource(id = R.dimen.value_8dp)
            )
        ) {
            Text(
                text = stringResource(id = R.string.navigation_storage_title),
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(start = dimensionResource(id = R.dimen.value_8dp))
            )

            Spacer(modifier = Modifier.height(15.dp))

            val totalTimeInMemory =
                state.music.filter { it.isStoredInMemory }.sumOf { it.timeInSec }
            NavItem(
                timeInSec = totalTimeInMemory,
                title = stringResource(id = R.string.navigation_memory_title),
                onClick = { navigationActions.navigateToMemory() }
            )

            Divider(color = Color.LightGray, thickness = 1.dp)

            val totalTimeInFilesystem =
                state.music.filter { it.isStoredInDb }.sumOf { it.timeInSec }
            NavItem(
                timeInSec = totalTimeInFilesystem,
                title = stringResource(id = R.string.navigation_filesystem_title),
                onClick = { navigationActions.navigateToFilesystem() }
            )

        }
    }


}