package com.example.end_to_end_app.animalsnearyou.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel


class AnimalsNearYouComposable {
}



@Composable
fun AnimalsNearYouScreen(viewModel: AnimalsNearYouViewModel = hiltViewModel()) {
    Column {
        Button(onClick = { viewModel.onEvent(AnimalsNearYouEvent.RequestAnimals) }) {
            Text(text = "Send Event")
        }
    }


}
