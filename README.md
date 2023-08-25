# Sample Android Project

Everything you need to start a modern, scalable and robust android project.
you got everything you need to get started , no more fundamental setup , boring primary
configuration and boilerplates.

![introduce_app](app/src/main/assets/introduce_app.gif)

This Android project template is built using the following technologies:

- MVVM (Model-View-ViewModel) architectural pattern
- Clean Architecture
- Hilt for dependency injection
- Coroutines and Flow for asynchronous programming
- Retrofit for networking
- Gson for JSON parsing
- Navigation Component for move between fragments
- Safe Args
- View Binding for UI binding
- Glide
- Dark Theme
- Multi Language

## Project Structure

The project follows the Clean Architecture principles, organizing code into layers:

- **app**: Contains the main Android application module.
- **data**: Implements the data layer, including repositories, data sources, and models.
- **domain**: Defines the business logic layer, including use cases and domain models.
- **presentation**: Implements the presentation layer, including ViewModels, UI components, and data binding.

## Dependency Injection

Hilt is used for dependency injection, providing a simple and efficient way to manage dependencies throughout the application. The use of Hilt ensures a modular and testable codebase.

## Asynchronous Programming

Coroutines are utilized for handling asynchronous operations in a concise and efficient manner. Coroutines simplify the code by providing structured concurrency and allowing developers to write asynchronous code in a sequential style.

## Network Communication

Retrofit is employed for handling network communication, providing a robust and flexible framework for making API requests. Gson is used for JSON parsing, ensuring efficient and reliable serialization and deserialization of data.
