package com.example.end_to_end_app

interface IDirectionsCompose {
    val route: String
    val icon: Int
    val title: String
}

object DirectionsFeatureSearch : IDirectionsCompose {
    override val route: String
        get() = "feature-search"
    override val icon: Int
        get() = R.drawable.ic_launcher_background
    override val title: String
        get() = "Search"
}

object DirectionsFeatureAnimalsNearYou : IDirectionsCompose {
    override val route: String
        get() = "feature-animals-near-you"
    override val icon: Int
        get() = R.drawable.dog_placeholder
    override val title: String
        get() = "Animals"
}