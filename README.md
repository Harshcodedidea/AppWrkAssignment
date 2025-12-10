# AppWrk Assignment

**Short Description:**  
This Android app demonstrates a simple yet practical use of Kotlin, MVVM architecture, RecyclerView, and Material Components. Users can view a list of items, filter them by status, and interact with full-screen dialogs for detailed information.

---

## Git Repository

https://github.com/Harshcodedidea/AppWrkAssignment.git

---

## How to Run the Project

1. Clone the repository:  
   ```bash
   git clone https://github.com/Harshcodedidea/AppWrkAssignment.git
2. Open the project in **Android Studio** (Arctic Fox or newer recommended).
3. Let Android Studio **sync the Gradle project**.
4. Connect an **Android device** or start an **emulator**.
5. Click **Run** (green play button) to install and launch the app.


## Architecture Overview

The app follows the **MVVM (Model-View-ViewModel)** architecture:
+ **Model:** Handles data, such as ```bashDataItemModel```bash and ```bashUserProfilePrefs```bash.
+ **ViewModel:** Manages the data logic and exposes filtered lists to the UI.
+ **View (Activity/Fragment):** Displays data using RecyclerView and listens to user actions.

## Key Components Used:

+ **RecyclerView + Adapter:** Shows a dynamic list of items.
+ **MaterialButtonToggleGroup:** Allows filtering items by status (Pending/Completed).
+ **Full-Screen Custom Dialogs:** Shows item details interactively.
+ **Kotlin Coroutines:** Handles background tasks like filtering without freezing the UI.
+ **Hilt (Dependency Injection):** Makes shared data like ```bashUserProfilePrefs```bash easy to manage.

## Why This Approach Was Chosen

+ **MVVM** keeps the code clean, modular, and easy to maintain.
+ **Material Components** provide a modern and interactive UI.
+ **RecyclerView + Adapter** efficiently handles dynamic lists.
+ **Full-screen** dialogs improve user experience by showing details without leaving the screen.
+ **Hilt + Coroutines** ensure proper dependency management and smooth performance.

## What I Would Improve with More Time

+ Implement **DiffUtil** for RecyclerView to animate changes efficiently.
+ Add **persistent storage (Room database)** to save filtered lists and statuses across app restarts.
+ Add **search functionality** to quickly find items.
+ Improve UI/UX with **animations and transitions** for dialogs and filters.
+ Add **unit and UI tests** for better reliability and maintainability.
