
# CLEAN ARCH ANDROID APP

This Android application showcases Clean Architecture in action by preserving the dependency rule between the Domain Layer and other layers such as Data, Presentation layers.
Here the domain is Pet Adoption.

(NOTE - this is not the final version. README file will be updated.)






## Getting Started

- To get started with this project, follow these instructions:

```bash
https://github.com/Ramu3967/end-to-end-app.git
```
- Open the project in Android Studio.

- Build and run the project on your Android device or emulator.
    
## Use cases covered

- Project structure - Feature First structure (Screaming Architecture).

- Building the independent Layer - THE DOMAIN
    - Entities - Represents the core business logic, concepts in your app.
    - Use cases - Verbs of your app that can be performed on Entities. 

- Building the Data Layer
    - Data source 1 - Remote API Integration (OAuth2) with error handling.
    - Data source 2 - Cache using the ROOM db and its integration.
    - Implementing a Single Source of Truth for data access.
    - Dependency Injection using Dagger-Hilt framework.
    - Implementing Repos (contracts) for accessing data.
- Util Layer
    - Holds all the common utility functions for the UI layers to use.
    - Also holds the ViewModels, Event and State Classes, Resources that are common for both the UI layers.
- Presentation Layer
    - Building the UI layers using JETPACK COMPOSE and Imperative XML approaches (each has its own module). 
    - Using of MVVM-MVI architecture as it avoids getting into impossible states with the help of proper app state managment using Flows.
    - Sending Events and Receiving data from the ViewModels for the UI to update their states.
    - Uni-Directional data flow.
    - Implementing Use-cases that trigger the events.
    - Multi-Modular app structure for showing the advantages of clean architecture.

- Testing
    - Unit testing using Junit and Mockito, Robolectric.
    - Integration Testing using Hilt(for testing) and In-Memory db from the Room lib.
    - End-to-End testing, UI- testing using Espresso framework (pending).

## Technologies used
- Android SDK.
- Kotlin Coroutines and Flows.
- Dependency Injection using Dagger-Hilt.
- Room DB and designing the relational DBs with it.
- UI using JETPACK COMPOSE and XML.
- State managment and Navigation components in Compose.
- Gradle kts for managing the dependencies in a multi-modular app structure.
- Testing using Mockito, Junit, Robolectric, Hilt, Espresso framework.

## Demo

Here comes the demo of the both the apps whose User Interface is implemented using COMPOSE and XML that share the common code from the domain, data and other layers following a Clean Architecture, with slightly different UI.


# Demo 1 

# Demo 2





## Thigs I've learnt when working on this app

- This article/desc is about developing real-world android applications that are highly cohesive and lowly coupled which can be scaled and with future-proof architecture.
- By future-proof, I mean designing in a way that there are less changes to be made in the future.

# Chapter - 1: INTRO
### Cohesion 
- It refers to a degree to which elements in a module can belong together. The Higher the degree, the better the structure of that module/class will be.
- A module with high cohesion contains elements that are closely related and work together to achieve a single goal.
- Before jumping right in to the coding I'd always spend some time in planning the structure of the entire app.

### Coupling
- It refers to the degree to which one module knows about or depends on another module.
- In other words, it describes how well a module knows about another one’s internal details and this degree should be as low as possible.
- Low coupling can be achieved using interfaces or contracts between those modules making the application more modular.

### Orthogonality in Clean Artchitecture
- In software design, orthogonality means that changes in one area (or dimension) of the system do not affect other areas. 
- In other words, components or modules operate independently of one another.
- You can achieve this by having highly cohesive and loosely coupled modules/classes/packages etc.

# Chapter - 2: DOMAIN LAYER
When your business logic is spread throughout the app:
- Code will become hard to find.
- You’ll start reimplementing logic by accident.
- Your code will have mixed responsibilities and as the project grows, it’ll become harder to change it.
Hence you need to decouple the logic using a domain layer.

### Domain Layer
It describes the domain space. You can find these in this layer:
- Entities - Objects that model your domain space (they have IDs-identities).
- Value Objects - another kind of object that models your domain space (no ID).
- Interactors/Use cases - (verbs) logic that is applied on entities.
- Repos - defines contracts for data source access.
This layer shouldn’t depend on any other layer. You can change data from REST to GraphQL and presentation layer from using XML to compose, but domain layer shouldn’t be affected.

#### Inverting the Dependencies
- Use-cases often trigger the api calls but they should run on abstractions instead of the actual data layer.
- For this reason, you create repos inside the domain layer and use it as a contract for the data layer to implement them.
- In this way you invert the depnencies.

# Chapter - 3: DATA LAYER
- This layer holds all the data needed for the business to run.
- This module/layer is divided into "mainly" two packages (api and cache).
- The data might come from the device's cache, remote api etc. This layer must implement the contract/interface in the domain for the repository which the use-cases use.

## API package
- Form your model classes based on the response from the API. For this app, please checkout this link - https://www.petfinder.com/developers/v2/docs/
- You need Mapper classes to convert from the data layer's models to the domain's.
- Another way of implementing this is by using companion objects (which is employed in the Cache package within this module).

### Connecting to the API 
- AnimalsNearYou feature requires the animal data according to the postal code and distance you specify.
- You tell the retrofit to make a GET request using the following query parameters.
- IIIIIIIIIIIInsert  image  hereeeeeeeeeeeeeeeeeeeeeeeee
- The petFinder server uses Oauth to authenticate the users with certain access tokens. You use this token and add it to each request as an http header using Interceptors.

### Interceptors
The current project uses 3 interceptors:
- Logging - logs http messages using OKHttpLogging library.
- Network - throws NetworkUnavailableException if there is a network error.
- Authenticate - Used to add authentication headers, checks for token expiry, refreshes it if needed etc.

### Testing
- MockWebServer - Lets you test your network code without connecting to a real server by creating a local web server which you can use to mock server responses.
- Create a mock response and store it in the assets folder. In the project, you store it in the debug srcSet so instrumented tests can also access it in the future.
- With this, unit tests can access all the assets, manifests, resources.
- IIIIIIIIIIIInsert  image  hereeeeeeeeeeeeeeeeeeeeeeeee
- You provide your fake server responses through objects called mockServer.Dispatchers.
- With mocks, you might implement too much logic. In cases like these, you can use a fake implementation and verify the final state that you want to test.
- Always focus on testing the behavior and not on the implementation.

## CACHE package
- You’d divide your db into various tables based on the entities and the relationship between them.
- The following is the ER diagram for this Application.
- IIIIIIIIIIIInsert  image  hereeeeeeeeeeeeeeeeeeeeeeeee
- DB DESIGN
    - one - one, one - many relationships,
    - aggregate/intermediate classes to represent relationships
    - 


### Testing
- Here, you’d perform integrated testing to test the network calls, cache storing mechanism in a realistic android environment.
- Hence, you’d need an emulator for it and so you’d write tests in the androidTest sourceset.
- So you’ll not be working with fakes/mocks but with the real device/emulator.

#### Hilt for testing
- You’ll need Hilt to maintain the dependencies for your tests.
- Similar to JunitRunner, RobolectricRunner, you have AndroidJUnitRunner which will run the tests on the emulator and not on the JVM.
- Hilt needs an application object to work with, so for tests, you need to create a separate AndroidRunner by passing the HiltTestApplication class.
- Tell gradle to use this runner instead of the default androidJunitRunner.

#### REPO TEST
- Annotate your test class with @HiltAndroidTest and Hilt will know it has to inject some dependencies here.
- Use @UninstallModules and @Unbind to remove the production modules from the dependency graph and provide their alternatives.
- For an interface's implementation use @BindValue.








# Chapter - 4: Presentation Layer
- The presentation layer encapsulates all the code related to the UI. You’ll find yourself changing UI code more than the business code here.

### Challenges
- State management - A state describes the state/info of an app (holding) at a given time and it may contain not only the data, but also the intrinsic properties of a view in the app such as its visibility, color of it etc.
- Not maintaining these states could result in weird bugs stemming up to impossible states.
- You need to deal with varsious life cycles.

### Architecture
- They help to maintain the Separation of Concerns across the app like the MVP, MVVM, MVI etc. They keep the UI dumb.
- Presenters in the MVP pattern are stateless (usually). MVVM has state management built in the architecture.
- In MVVM, the view subscribes to the changes in its state to the viewModel.
- But still, there are problems in it too. Ex - if you have tightly coupled properties as shown below, you might end up in an impossible state where you have the list items and the progress bar is loading.
- IIIIIIIIIIIInsert  image  hereeeeeeeeeeeeeeeeeeeeeeeee

### MVVM-MVI
- MVI enforces some rules
- IIIIIIIIIIIInsert  image  hereeeeeeeeeeeeeeeeeeeeeeeee
- *****You won’t work with the exact MVI (avoiding reducers and intents), but a combination of MVVM and MVI (unidirectional data flow architecture).
- IIIIIIIIIIIInsert  image  hereeeeeeeeeeeeeeeeeeeeeeeee


#### First Use-Case
- Every use case is a class with which you keep your app’s logic well-separated.
- A use case has a purpose and so it's better to have only one method in it.
- IIIIIIIIIIIInsert  image  hereeeeeeeeeeeeeeeeeeeeeeeee


#### Second Use-Case
- This feature is all about searching the animals and follows the similar pattern to that of the animals near you feature.
- You send searchEvents to the viewModel and it processes the info using the use cases and updates the states for the UI to consume.
- As the db is the source of truth for the data, the search would be a local one, if there are no results there, make an api call with the search parameters.
- IIIIIIIIIIIInsert  state image  hereeeeeeeeeeeeeeeeeeeeeeeee
- The complexity is in the various scenarios - starting to search locally, searching remotely, no results etc has their own set of values for the state. So, instead of updating all the fields with respect to the scenario, you make use of some util functions
- IIIIIIIIIIIInsert  util image  hereeeeeeeeeeeeeeeeeeeeeeeee





#### TESTING
- UI tests are a flakier as the code here may change frequently.
- One blind rule, make the UI as dumb as possible.
- Maintaining a Unidirectional flow of the data makes testing the VM a breeze. You just need to assert on the resultant state values.
- ESPRESSO testing is pending as of now.


