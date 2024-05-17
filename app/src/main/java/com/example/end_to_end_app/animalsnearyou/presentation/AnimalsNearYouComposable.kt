package com.example.end_to_end_app.animalsnearyou.presentation

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.end_to_end_app.R


class AnimalsNearYouComposable {
}



@Composable
fun AnimalsNearYouScreen() {
    val viewModel: AnimalsNearYouViewModel = hiltViewModel()
    Column(modifier = Modifier.fillMaxSize()) {
        Button(onClick = { }) {
            Text(text = "Send Event")
        }
        Spacer(modifier = Modifier.height(10.dp))

        MyGrid(viewModel)
    }

}


@Composable
fun MyGrid(viewModel: AnimalsNearYouViewModel) {
    viewModel.onEvent(AnimalsNearYouEvent.RequestInitialAnimals)
    val data = viewModel.state.collectAsState()
    updateScreen(data.value)
    LazyVerticalGrid(columns = GridCells.Fixed(2)){
        items(data.value.dataAnimals.size){
            AnimalUIElement(data.value.dataAnimals[it].photo, data.value.dataAnimals[it].name)
        }
    }
}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun AnimalUIElement(imgPath: String, desc: String) {
    Column(modifier = Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {

        GlideImage(model = imgPath, contentDescription = "", modifier=Modifier.size(150.dp)){
            it.error(R.drawable.dog_placeholder)
        }

        Text(text= desc, style = TextStyle(textAlign = TextAlign.Center))

    }
}

fun updateScreen(state: AnimalsNearYouViewState){
    handleFailure(state.failure)
    // submit list to the vertical grid = state.animals
}

fun handleFailure(failure: Throwable?) {
    val unhandledMessage = failure?.message ?: /* no failure then */ return
    val fallbackMessage = "An error occurred. Please try again later"
    val errorMessage = unhandledMessage.ifEmpty { fallbackMessage }
    Log.e("Error-AnimalScreen", "handleFailure: $errorMessage" )
}
