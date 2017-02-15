## Synopsis

Test application for fetching and playing videos from json feed. The app displays fetched items
in a resizable grid view. When the user clicks an item a media player opens and plays the video.

The application has a fast-forward feature whereby when the user presses the fast-forward button playback time is doubled.

The application uses a mix of Kotlin and java and adheres to MVP in both main and detail views.
The app also makes its network request using RxJava, Retrofit and OkHttp. The network request has a retry
policy of 3 times with exponential back-off. OkHttp also maintains an offline cache so unnecessary
network requests will not be made.


## Libraries

* Retrofit 2 : networking.
* Okhttp : okhttp.
* ExoPlayer (unofficial) : media player.
* RxJava : reactive programming.
* Mockito/Junit : testing.
* Espresso : testing.
* ButterKnife : view injection.
* Dagger2 : dependency injection.
* Picasso : image library.


## Installation

Clone project. Open in Android Studio. build.

## Tests

Uses Mockito and Junit4 for unit testing. Espresso and RESTMock used for integration testing.


