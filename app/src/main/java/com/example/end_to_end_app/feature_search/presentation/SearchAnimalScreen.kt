package com.example.end_to_end_app.feature_search.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.end_to_end_app.common.presentation.model.AnimalUIElement

@Preview
@Composable
fun AnimalsSearchScreen() {
    val viewModel: SearchAnimalsViewModel = hiltViewModel()
    viewModel.onEvent(SearchAnimalEvents.PrepareForSearchEvent)
    val composeState = viewModel.state.collectAsState()

    Column {
        Row(modifier = Modifier.fillMaxWidth()) {
            SearchView{viewModel.onEvent(SearchAnimalEvents.QueryInput(it))}
        }

        Row {
            DropdownView(items = composeState.value.ageFilterValues) {viewModel.onEvent(SearchAnimalEvents.AgeValueSelected(it))}
            DropdownView(items = composeState.value.typeFilterValues) {viewModel.onEvent(SearchAnimalEvents.TypeValueSelected(it))}
        }

        AnimalSearchGrid(composeState.value)
    }
}

@Composable
fun SearchView(action: (String) -> Unit) {
    var text by remember {
        mutableStateOf("")
    }
    OutlinedTextField(
        value = text,
        onValueChange = {
            text = it
            action(it)
        },
        placeholder = {
            Text(
                text = "Search for names or breeds",
                color = Color.Gray,
                modifier = Modifier
                    .padding(start = 60.dp)
            )
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            disabledContainerColor = Color.White,
            cursorColor = Color.Gray,
            focusedBorderColor = Color.Gray,
            unfocusedBorderColor = Color.Gray,
        ),
        leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF495E57))
    )
}

@Composable
fun AnimalSearchGrid(
    animalState: SearchAnimalViewState
) {

    // Observe the scroll state of the LazyGrid
    val lazyGridState = rememberLazyGridState()

    // Display the list of items in the LazyGrid
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        state = lazyGridState
    ) {
        items(animalState.searchResults.size) {
            AnimalUIElement(animalState.searchResults[it].photo, animalState.searchResults[it].name)
        }
    }
}


@Composable
fun DropdownView(items: List<String> = listOf("age1","age2","age3","age4"), action:(String)-> Unit) {

    // State to hold the selected item
    var selectedItem by remember { mutableStateOf(items.firstOrNull() ?: "Any") }

    // State to manage the dropdown menu visibility
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            BasicTextField(
                value = TextFieldValue(selectedItem),
                onValueChange = {
                    selectedItem = it.text
                },
                singleLine = true,

                readOnly = true,
                modifier = Modifier
                    .height(56.dp)
                    .border(1.dp, Color.Gray, MaterialTheme.shapes.small)
                    .padding(horizontal = 8.dp)
                    .clickable(onClick = { expanded = true })
            )
            IconButton(
                onClick = { expanded = !expanded }
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = "Toggle Dropdown"
                )
            }
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(IntrinsicSize.Min)
        ) {
            items.forEach { item ->
                DropdownMenuItem({
                    Text(text = item)
                },
                    onClick = {
                        selectedItem = item
                        expanded = false
                        action(item)
                    }
                )
            }
        }
    }
}
