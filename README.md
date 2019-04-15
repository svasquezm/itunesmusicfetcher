
# ITunes Music Fetcher
This is a small application which fetch a list of tracks from apple music's API and allow user to play previews via streaming. It contains two Views:
1. Home: which show a list and user can search tracks by term
2. A Favorite List: which user can manage their favorites and use them whenever it wants

## Implementation
### Clean architecture implementation
This project uses an Clean Architecture represented by 5 layers distribuited in 3 Gradle modules.
1. Domain Module: contains the general model to be used by the superior layers. It does not depends of any other layer
2. Data Module: contains abstractions of repositories and Sources (Local and Remote) to retrieve required data. It dependes on Domain module.
3. App Module: contains the implementations of the Presentation layer and the Framework layer

This scheme was followed according to Antonio Leiva's approach (see Resources section below).
The main idea of this approach is to reuse Domain and Data for any Kotlin project implementation (for example, a server side application)

### Android architecture components use
In the UI-side, I've used Constraint Layouts and Android Architecture components to implement a MVVM architecture. It uses `ViewModel` and `LiveData` to bind `Room` data to the `Activity`.

### Other dependencies
To make request on server, I've implemented it using `Retrofit2` and `anko` library to implement async operations. To reproduce media music streaming I've used `MediaPlayer` class.

All is made in a `dependency injection` pattern, which all relevant classes (`Room database`, `Media player`, `Repository dependencies` and `View model factory`) are injected using `Kodein`. See `ITunesMusicFetcherApplication` class for more information.

### Resources
1. https://antonioleiva.com/clean-architecture-android/)
2. https://developer.android.com/topic/libraries/architecture/room
3. https://developer.android.com/topic/libraries/architecture/livedata
4. https://developer.android.com/topic/libraries/architecture/viewmodel
5. https://developer.android.com/guide/topics/media/mediaplayer
6. https://developer.android.com/training/constraint-layout
7. https://square.github.io/retrofit/
8. https://github.com/Kotlin/anko
9. https://github.com/Kodein-Framework/Kodein-DI