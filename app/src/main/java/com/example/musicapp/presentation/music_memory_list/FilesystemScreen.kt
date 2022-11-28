package com.example.musicapp.presentation.music_memory_list

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.musicapp.R
import com.example.musicapp.presentation.components.MainTopAppBar
import com.example.musicapp.presentation.components.MusicItem
import com.example.musicapp.presentation.home_screen.HomeViewModel

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun FilesystemScreen(
    @StringRes topBarTitle: Int,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: FilesystemViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        scaffoldState = scaffoldState,
        modifier = modifier.fillMaxSize(),
        topBar = {
            MainTopAppBar(
                stringResource(topBarTitle),
                onBack
            )
        }
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    vertical = dimensionResource(id = R.dimen.value_16dp),
                    horizontal = dimensionResource(id = R.dimen.value_16dp)
                )
        ) {
            val musicListing = state.music
            items(items = musicListing) { item ->
                MusicItem(
                    musicItem = item,
                    showIcon = true,
                    enableIcon = item.isStoredInDb,
                    onClickIcon = { viewModel.processEditAction(item) }
                )
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.value_8dp)))
            }
        }

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {

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

        }
    }
}