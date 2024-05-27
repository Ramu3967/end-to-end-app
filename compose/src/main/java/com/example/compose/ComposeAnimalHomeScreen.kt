package com.example.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.compose.features.animalsnearyou.AnimalsNearYouScreen
import com.example.compose.features.search.AnimalsSearchScreen

@Composable
fun ComposeAnimalHomeScreen(navController: NavHostController) {
    Scaffold (
        bottomBar = { BottomNav(navController = navController) },
        topBar = { TopAppNav()}
    ){
        Box(modifier= Modifier.padding(it)){
            NavHost(navController = navController,
                startDestination = DirectionsFeatureAnimalsNearYou.route){
                composable(DirectionsFeatureSearch.route){
                    AnimalsSearchScreen()

                }
                composable(DirectionsFeatureAnimalsNearYou.route){
                    AnimalsNearYouScreen()
                }

            }
        }
    }
}

@Composable
fun BottomNav(navController: NavHostController) {
    var bottomBarState by rememberSaveable {
        mutableIntStateOf(0)
    }
    val directions = listOf(DirectionsFeatureAnimalsNearYou, DirectionsFeatureSearch)
    NavigationBar {
        directions.forEachIndexed { index, direction ->
            NavigationBarItem(
                selected = bottomBarState == index,
                onClick = {
                    bottomBarState = index
                    navController.navigate(direction.route)
                },
                icon = {
                    Icon(imageVector = direction.icon, contentDescription = "")
                },
                label = { Text(text = direction.title) })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppNav() {
    TopAppBar(
        title = {
            Text(
                text = "PetFinder Compose",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = Color(0xFF03A9F4) // Set background color
        ),
        modifier = Modifier.fillMaxWidth(),
    )
}
