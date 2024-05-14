package com.example.end_to_end_app.animalsnearyou.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage


class AnimalsNearYouComposable {
}



@Composable
fun AnimalsNearYouScreen() {
    val viewModel: AnimalsNearYouViewModel = hiltViewModel()
    Column(modifier = Modifier.fillMaxSize()) {
        Button(onClick = { viewModel.onEvent(AnimalsNearYouEvent.RequestAnimals) }) {
            Text(text = "Send Event")
        }
        Spacer(modifier = Modifier.height(10.dp))

        MyGrid(viewModel)

    }

}


@Composable
fun MyGrid(viewModel: AnimalsNearYouViewModel) {
    val data = viewModel.state.collectAsState()
    LazyVerticalGrid(columns = GridCells.Adaptive(200.dp)){
        items(data.value.dataAnimals.size){
            AnimalUIElement(data.value.dataAnimals[it].photo, data.value.dataAnimals[it].name)
        }
    }

}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun AnimalUIElement(imgPath: String, desc: String) {
    Column(modifier = Modifier.padding(20.dp)) {

        GlideImage(model = imgPath, contentDescription = "")

        Text(text= desc)

    }
}
