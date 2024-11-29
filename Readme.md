# 🎥 VynkApp
VynkApp is a social media platform built using Jetpack Compose and Firebase, allowing users to upload, view, and interact with video posts. The app supports dynamic feeds, in-app notifications(Future Scope), and user authentication, following the MVVM architecture for scalability and maintainability.

---

## Table of Contents
- [Important Notes](⚠️ Important Notes)
- [Features](🚀 Features)
- [Architecture](🧱 Architecture)
- [Screens](📱 Screens)
- [Tech Stack](🛠️ Tech Stack)
- [Screenshots](📸 Screenshots)
- [Installation](🧰 How to Run the Project)
- [Contribution](🤝 Contributing)
- [License](📄 License)
- [Feedback](💬 Feedback)

---

## ⚠️ Important Notes
1. Authentication:
User authentication is implemented using Firebase Authentication.
2. Storage Handling:
The storage functionality is implemented via the Firebase Emulators Suite to handle video uploads without incurring costs.
To switch back to live Firebase services, modify the network initialization in NetworkModule.kt. Replace the emulator IP address with the normal Firebase Storage initialization function.

---

## 🚀 Features
- **User Authentication:**
Secure login and sign-up using Firebase Authentication.
- **Dynamic Feed:**
Scrollable feed of video posts, each with captions. Videos play automatically when visible.
- **Video Upload:**
Users can upload videos with captions via an intuitive UI.
- **Logout Functionality:**
Logout securely to protect user data.
- **In-app Notifications(Future Scope):**
Get notified of app events and updates.

---

## 🧱 Architecture
The app follows the **MVVM (Model-View-ViewModel)** architecture:
- **ViewModel:** Manages UI-related data and communicates with repositories.
- **Repository:** Handles business logic and data fetching.
- **Compose UI:** Creates a reactive UI that updates based on state changes.

---

## 📱 Screens
### 1. **Login Screen**
Users can log in using their email and password. Incorrect login attempts trigger error notifications via Snackbar.

### 2. **Signup Screen**
New users can create an account. Redirects to the feed upon successful signup.

### 3. **Feed Screen**
Displays a list of video posts using a lazy column. Video playback is dynamically controlled based on visibility.

### 4. **Upload Screen**
Allows users to upload videos with captions. Video selection is integrated with the file picker.

---

## 🛠️ Tech Stack
- **Kotlin**
- **Jetpack Compose:** For declarative UI components.
- **Hilt:** Dependency injection framework.
- **Navigation Component:** Handles screen transitions.
- **Firebase Emulators Suite:** For data storage.(Used due to billing problem for Firebase Storage)

---

## 📸 Screenshots

<p align="center"> <img src="./Screenshot 0.jpg" alt="Screenshot 1" width="180" /> <img src="./Screenshot 1.jpg" alt="Screenshot 2" width="180" /> <img src="./Screenshot 2.jpg" alt="Screenshot 3" width="180" /></p>

---

## 🧰 How to Run the Project
1. Clone the Repository
   git clone https://github.com/Moin333/Vynk.git
   cd note-app-with-realm
2. Open the Project in Android Studio
   Make sure you have the latest version of Android Studio installed.
3. Build and Run the App
   Connect an Android device or emulator and click Run ▶️ in Android Studio.

---

## 🤝 Contributing
Feel free to fork this repository and submit pull requests to improve the project! 🎉

---

## 📄 License
This project is licensed under the MIT License.

---

## 💬 Feedback
If you have any feedback or issues, please open an issue or reach out. 😊
