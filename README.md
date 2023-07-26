# GameStore - A Game Store App

GameStore is an Android app built using Kotlin in Android Studio that serves as a clone of an app store, specifically tailored for games. It allows users to search for games based on their title using the IGDB API. Additionally, users can save games, leave reviews, and view game details with the help of two web APIs: IGDB API for game information and another API for storing reviews and favorite games. The app ensures a smooth user experience by handling internet connectivity issues and supporting landscape orientation.

## Features

- Game search based on title using the IGDB API.
- Sorting games displayed with priority of favorite games
- Game details display upon clicking on a game.
- Sort list of games to prioritize favorites on top.
- Search games by title within favorites only.
- Ability to leave reviews for games, with reviews stored on a web API.
- Review storage in the local database when there is no internet connection, with automatic synchronization upon reestablishing connection.
- Landscape orientation support for improved user experience.

## Libraries Used

- Retrofit: Used to communicate with web APIs (IGDB API and 2nd API) to fetch game data and save reviews.
- Room Library: Used for database management, specifically for storing reviews locally and synchronizing with the web API.
- Android Architecture Components: Used to implement best practices for Android app development.
- Other standard Android libraries and components: Used to create the user interface, handle navigation, and manage data.

## Screenshots



## Getting Started

To run the GameStore app on your Android device or emulator, follow these steps:

1. Clone the repository: `git clone https://github.com/your_username/GameStore.git`

2. Open the project in Android Studio.

3. Build and run the app on your device or emulator.

## API Integration

The app uses the IGDB API for fetching game data based on user searches. You will need to obtain your own API key from the IGDB API website (https://api-docs.igdb.com/#getting-started) and insert it into the app's code for it to work correctly.

Additionally, the app uses a 2nd API for storing and retrieving reviews. This API requires its own authentication and setup. Please refer to the API documentation (https://rma23ws.onrender.com/api-docs/#/) for more information.
