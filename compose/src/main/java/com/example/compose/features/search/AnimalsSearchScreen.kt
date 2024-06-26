package com.example.compose.features.search

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Clear
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pf_utils.features.search.presentation.SearchAnimalEvents
import com.example.pf_utils.features.search.presentation.SearchAnimalViewState
import com.example.pf_utils.features.search.presentation.SearchAnimalsViewModel
import com.example.pf_utils.model.AnimalUIElement
import com.example.pf_utils.model.MyLoader
import com.example.pf_utils.model.UIAnimal

//@Preview
@Composable
fun AnimalsSearchScreen() {
    val viewModel: SearchAnimalsViewModel = hiltViewModel()
    LaunchedEffect(Unit){
        viewModel.onEvent(SearchAnimalEvents.PrepareForSearchEvent)
    }
    val composeState = viewModel.state.collectAsState()

    Log.e("AnimalsSearchScreen", "AnimalsSearchScreenMAIN: composed")

    Column {
        Row(modifier = Modifier.fillMaxWidth()) {
            SearchView{viewModel.onEvent(SearchAnimalEvents.QueryInput(it))}
        }

        UpdateScreens(state = composeState.value,
            ageAction = {viewModel.onEvent(SearchAnimalEvents.AgeValueSelected(it))},
            typeAction = {viewModel.onEvent(SearchAnimalEvents.TypeValueSelected(it))}
        )

        AnimalSearchGrid(composeState.value)
    }
}

@Composable
fun SearchView(action: (String) -> Unit) {
    var text by remember {
        mutableStateOf("")
    }
    Log.e("AnimalsSearchScreen", "SearchView: composed")
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
        trailingIcon = {
            if(text.isNotEmpty())
                Icon(Icons.Filled.Clear, contentDescription = null,
            modifier = Modifier.clickable { text = ""; action("") })},
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
fun UpdateScreens(state: SearchAnimalViewState, ageAction:(String)-> Unit, typeAction:(String)-> Unit) {

    val (noSearchQuery, searchResults, ageFilters, typeFilters,
        isSearchRemote, areRemoteResultsFound, failure) = state

    Column{
        UpdateFilterViews(
            ageFilters, typeFilters,
            ageAction = ageAction,
            typeAction = typeAction
        )
        UpdateResultsInGrid(searchResults)
        RemoteSearchViews(vis = isSearchRemote)
    }
    InitialStateViews(vis = noSearchQuery)
    NoResultsView(vis = areRemoteResultsFound)

    HandleFailures(failure = failure)
    Log.e("AnimalsSearchScreen", "UpdateScreens: composed")
}

@Composable
fun UpdateFilterViews(ageFilters: List<String>, typeFilters: List<String>, ageAction:(String)-> Unit, typeAction:(String)-> Unit) {
    Row {
        DropdownView(items = typeFilters, label = "Type", action = typeAction)
        DropdownView(items = ageFilters, label = "Age", action = ageAction)
    }
}

@Composable
fun DropdownView(items: List<String>, label: String, action:(String)-> Unit) {

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

            Column{
                Text(
                    text = label,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
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
            }
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

@Composable
fun UpdateResultsInGrid(searchResults: List<UIAnimal>) {
    // Observe the scroll state of the LazyGrid
    val lazyGridState = rememberLazyGridState()

    // Display the list of items in the LazyGrid
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        state = lazyGridState
    ) {
        items(searchResults.size) {
            AnimalUIElement(searchResults[it].photo, searchResults[it].name)
        }
    }
}

@Composable
fun NoResultsView(vis:Boolean) {
    if(vis)
        Column (modifier = Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally){
            Image(painter = painterResource(id = com.example.pf_utils.R.drawable.no_results_pug), contentDescription = "")
            Text(text = "Sorry No Results")
        }
}

@Composable
fun RemoteSearchViews(vis: Boolean) {
    if (vis)
        Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(painter = painterResource(id = com.example.pf_utils.R.drawable.img_searching), contentDescription = "")
                Text(text = "Please wait, search in progress")
                Spacer(modifier = Modifier.height(16.dp))
                MyLoader(isLoading = vis)
            }
        }
}

@Composable
fun InitialStateViews(vis:Boolean ) {
    if(vis)
        Column ( modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center){
            Image(painter = painterResource(id = com.example.pf_utils.R.drawable.init_search ), contentDescription = "",
                Modifier.size(200.dp))
            Text(text = "Use the above search field to find your new pet! Don't forget to use the filters to narrow down the search")
        }
}

@Composable
fun HandleFailures(failure: Throwable?) {
    if (failure != null) {
        Log.d("SearchScreen", "HandleFailures: ${failure.message}")
    }
}