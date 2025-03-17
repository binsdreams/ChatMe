# Real-Time Chat Application - Chat Me

## Author
**Binil Thomas PR**  
binsdreams@gmail.com / binilthomaspr@gmail.com

## Features
- **User Authentication**: Supports signup/login via Firebase Authentication.
- **One-on-One Chat**: Real-time messaging functionality using Firestore.
- **Chat List**: Displays recent conversations along with the users.
- **Offline Support**: Loads the last messages from local storage.

## Features TODO - Future Roadmap
- **Typing Indicators**: Shows when the other user is typing.
- **Message Status**: Handles timestamps and statuses (sent, delivered).
- **Group Chat**: Multiple users chat in a group.
- **Media Sharing**: Multimedia chat enabled with images, voice notes, PDFs, videos, etc.
- **Custom UI**: Custom animations and UI elements.
- **User Profile & Settings**: User profile page and application settings, user preferences.

## Technical Stack

### Architecture
- **MVVM** (Model-View-ViewModel)

### Real-Time Communication
- **Firebase Firestore**

### Networking
- **Firebase SDK**

### Persistence
- **Room Database** and **SharedPreferences**

### Dependency Injection
- **Hilt**

### Unit Testing
- **JUnit** / **Mockito** (for ViewModel and API handling)

### Major Third-Party Libraries Used
- **Android Jetpack Compose**: For UI elements and modern UI development.
- **Hilt**: For dependency injection.
- **Firebase and Firestore**: For authentication and real-time chat communication.
- **Lottie**: For loading animations.
- **Room Database**: For local data persistence.
- **Android Security Library**: For encrypted shared preferences.
- **Mockito**: For unit testing.

## Getting Started

### Prerequisites
- [Android Studio Meerkat](https://developer.android.com/studio?gad_source=1&gclid=Cj0KCQjwkN--BhDkARIsAD_mnIpMDcEUqAyOuKxeIMrLZX36CpbcVzmkjh61lsVcSkNTkweS-PJL250aAqNhEALw_wcB&gclsrc=aw.ds)
- Firebase account (Firebase Auth & Firestore)
- Internet connection for real-time messaging

### Setup
1. Clone the repository:
   ```sh
   git clone https://github.com/binsdreams/ChatMe.git
   cd chat-app
   ```
2. Open the project in **Android Studio**.
3. Set up Firebase:
   - Add the `google-services.json` file to the `app/` directory.
   - Enable Authentication (Email/Password) in Firebase Console.
   - Set up Firestore Database.
4. Build and run the app.

### Running Tests
Execute unit tests using:
```sh
./gradlew test
```

## Contributing
Feel free to submit issues and pull requests.

## License
[MIT License](LICENSE)

