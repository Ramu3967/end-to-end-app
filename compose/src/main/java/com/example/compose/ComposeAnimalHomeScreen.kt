package com.example.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.compose.features.animalsnearyou.AnimalsNearYouScreen

@Composable
fun ComposeAnimalHomeScreen(navController: NavHostController) {
    Scaffold (
        bottomBar = { BottomNav(navController = navController) },
        topBar = { TopAppNav()}
    ){
        Box(modifier= Modifier.padding(it)){
            NavHost(navController = navController,
                startDestination = DirectionsFeatureSearch.route){
                composable(DirectionsFeatureSearch.route){
//                    AnimalsSearchScreen()
                    AnimalsNearYouScreen()

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
    val directions = listOf(DirectionsFeatureSearch, DirectionsFeatureAnimalsNearYou)
    NavigationBar {
        directions.forEachIndexed { index, direction ->
            NavigationBarItem(
                selected = bottomBarState == index,
                onClick = {
                    bottomBarState = index
                    navController.navigate(direction.route)
                },
                icon = { direction.icon },
                label = { Text(text = direction.title) })
        }
    }
}

@Composable
fun TopAppNav() {
    Text(text = "PetFinder")
}