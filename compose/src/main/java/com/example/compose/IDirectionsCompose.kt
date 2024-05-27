package com.example.compose

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

interface IDirectionsCompose {
    val route: String
    val icon: ImageVector
    val title: String
}

object DirectionsFeatureSearch : IDirectionsCompose {
    override val route: String
        get() = "feature-search"
    override val icon: ImageVector
        get() = Icons.Default.Search
    override val title: String
        get() = "Search"
}

object DirectionsFeatureAnimalsNearYou : IDirectionsCompose {
    override val route: String
        get() = "feature-animals-near-you"
    override val icon: ImageVector
        get() = Icons.Default.LocationOn
    override val title: String
        get() = "Animals"
}