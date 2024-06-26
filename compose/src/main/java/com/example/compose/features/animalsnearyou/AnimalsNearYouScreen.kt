package com.example.compose.features.animalsnearyou


import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pf_utils.features.animalsnearyou.presentation.AnimalsNearYouEvent
import com.example.pf_utils.features.animalsnearyou.presentation.AnimalsNearYouViewModel
import com.example.pf_utils.features.animalsnearyou.presentation.AnimalsNearYouViewState
import com.example.pf_utils.model.AnimalUIElement
import com.example.pf_utils.model.MyLoader


@Composable
fun AnimalsNearYouScreen() {
    val viewModel: AnimalsNearYouViewModel = hiltViewModel()
    val animalState by viewModel.state.collectAsState()
    handleFailure(animalState.failure)

    Column(modifier = Modifier.fillMaxSize()) {

        Spacer(modifier = Modifier.height(10.dp))

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            InfiniteScrollableGrid(viewModel, animalState)
            MyLoader(animalState.loading)
        }
    }

}

@Composable
fun InfiniteScrollableGrid(
    viewModel: AnimalsNearYouViewModel,
    animalState: AnimalsNearYouViewState
) {

    // Observe the scroll state of the LazyGrid
    val lazyGridState = rememberLazyGridState()
    val isScrolledToEnd by remember(lazyGridState, animalState) {
        derivedStateOf {
            val visibleItems = lazyGridState.layoutInfo.visibleItemsInfo
            val lastVisibleItemIndex = visibleItems.lastOrNull()?.index ?: 0
            val totalItems = animalState.dataAnimals.size
            val isEndReached = lastVisibleItemIndex >= totalItems - 1
            isEndReached
        }
    }

    // Trigger the API call to load more items when scrolled to the end
    LaunchedEffect(isScrolledToEnd) {
        if (isScrolledToEnd) {
            viewModel.onEvent(AnimalsNearYouEvent.RequestMoreAnimals)
        }
    }

    // Display the list of items in the LazyGrid
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        state = lazyGridState
    ) {
        items(animalState.dataAnimals.size) {
            AnimalUIElement(animalState.dataAnimals[it].photo, animalState.dataAnimals[it].name)
        }
    }
}

fun handleFailure(failure: Throwable?) {
    val unhandledMessage = failure?.message ?: /* no failure then */ return
    val fallbackMessage = "An error occurred. Please try again later"
    val errorMessage = unhandledMessage.ifEmpty { fallbackMessage }
    Log.e("Error-AnimalScreen", "handleFailure: $errorMessage" )
}
