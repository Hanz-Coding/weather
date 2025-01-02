# Weather App

This is a weather forecasting application built using modern Android development practices. The app predicts the weather for the next 7 days and shows the current weather using data from the [Open-Meteo API](https://open-meteo.com/).

The app is designed using **Clean Architecture**, **MVVM (Model-View-ViewModel)** pattern, and incorporates **Dependency Injection** using **Koin**, **Retrofit** with **Moshi** for API handling, and **Jetpack Compose** for the UI.

## Features
- Display current weather data (temperature, wind speed, humidity, etc.).
- Display weather forecast for the next 7 days.
- Beautiful and responsive UI with **Jetpack Compose**.
- Fetch weather data from the Open-Meteo API.
- Use **Clean Architecture** for code organization and maintainability.
- Implement **MVVM** architecture for separating UI and business logic.
- **Koin** for easy dependency injection.

## Technologies Used
- **Jetpack Compose**: UI framework for building modern, reactive UIs.
- **Koin**: Dependency injection framework for Kotlin.
- **Retrofit**: Network library for making API calls.
- **Moshi**: JSON library used for parsing JSON responses.
- **Open-Meteo API**: Provides weather data for the app.
- **Clean Architecture**: Ensures separation of concerns, making the code more testable, maintainable, and scalable.
- **MVVM**: A design pattern that separates the user interface (View), business logic (Model), and ViewModel.
  
## API Integration

The weather data is fetched from the Open-Meteo API by making a GET request to `https://api.open-meteo.com/`. The API provides current weather information and forecasts for the upcoming 7 days.

### Example API Endpoint for Weather Data:
https://api.open-meteo.com/v1/forecast?hourly=temperature_2m,weathercode,relativehumidity_2m,windspeed_10m,pressure_msl,rain&daily=temperature_2m_max,temperature_2m_min,weather_code&latitude=37.4220936&longitude=-122.083922


## Setup

1. Clone the repository:
   ```bash
   git clone https://github.com/Hanz-Coding/weather.git
   cd weather-app

2. Open the project in Android Studio.

3. Build the project:

4. Go to File > Sync Project with Gradle Files.
Make sure to add your API keys and configure network calls in the appropriate classes if necessary.

Architecture Overview
## Clean Architecture
This project follows Clean Architecture principles, where the application is organized into layers:

- Domain Layer: Contains the business logic and use cases. It is independent of any frameworks or external libraries.
- Data Layer: Responsible for network communication, data parsing, and caching.
- Presentation Layer: Contains the UI logic, ViewModel, and uses Jetpack Compose for rendering the user interface.
## MVVM
- Model: Represents the data (weather forecast and current weather).
- View: The UI layer using Jetpack Compose that observes data from the ViewModel.
- ViewModel: Holds the data for the UI and handles the logic for data fetching and state management.
## Dependency Injection with Koin
We use Koin for managing dependencies. It simplifies the process of injecting dependencies into the app's classes, like ViewModels, Network clients, etc.

To set up Koin in the project, add the necessary dependencies in build.gradle and define modules that provide instances of your services, repositories, and use cases.

## Retrofit & Moshi
We use Retrofit for API requests and Moshi for converting JSON data into Kotlin data objects. The API calls are made asynchronously using Retrofit’s suspend functions.
