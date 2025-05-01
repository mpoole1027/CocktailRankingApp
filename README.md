Cocktail Ranking App
Cocktail Ranking App is an Android application developed in Kotlin that allows users to vote on their favorite cocktails, explore cocktail details, and view real-time rankings based on user-generated ELO scores. This project leverages TheCocktailDB API for fetching cocktail data and provides a smooth, user-friendly experience through intuitive UI components and structured architecture.

Features
Cocktail Voting
Compare two cocktails side-by-side and vote for your favorite. Each vote updates the cocktails' ELO scores using a rating algorithm to reflect user preferences.

Ranking Leaderboard
View a dynamic list of the top 10 cocktails based on current ELO rankings. Rankings are updated in real-time as users vote.

Search Functionality
Search for cocktails by name using TheCocktailDB API. The results include images, instructions, ingredients, and more.

Cocktail Details View
All cocktails—whether voted on, ranked, or searched—can be clicked to open a detailed view showing ingredients, measurements, preparation instructions, and a preview image.

API Integration
This application uses TheCocktailDB as its primary data source. All cocktail metadata—such as names, images, ingredients, and instructions—is fetched using Retrofit from this public API.

Tech Stack
Category	Technology
Language	Kotlin
Architecture	MVVM (Model-View-ViewModel)
Network Layer	Retrofit
Persistence	Room Database
UI Framework	Android Jetpack, RecyclerView
Image Loading	Coil
Build System	Gradle (Kotlin DSL)

Getting Started
Prerequisites
Android Studio (latest stable version)

Android Emulator or physical device running API level 21 or higher

Internet access (for API calls to TheCocktailDB)

Installation
Clone the Repository

bash
Copy
Edit
git clone https://github.com/mpoole1027/CocktailRankingApp.git
cd CocktailRankingApp
Open the Project

Launch Android Studio

Select Open an Existing Project

Choose the CocktailRankingApp directory

Build and Run

Let Gradle finish syncing dependencies

Connect an emulator or physical device

Press Run

Project Structure
bash
Copy
Edit
CocktailRankingApp/
├── data/
│   ├── database/       # Room database models and DAOs
│   └── repository/     # Data source abstraction layer
├── network/
│   └── model/          # Retrofit data classes
├── ui/
│   ├── home/           # Voting screen
│   ├── ranking/        # Leaderboard display
│   ├── search/         # Search UI and logic
│   └── detail/         # Cocktail detail view
├── viewmodel/          # Shared and feature-specific ViewModels
├── utils/              # Helper classes and ELO logic
└── MainActivity.kt     # App entry point
ELO Rating System
The app uses an ELO rating system to dynamically update cocktail rankings based on user votes. Each vote adjusts the ratings of the winning and losing cocktails, ensuring that the leaderboard reflects community preferences.

Contributing
Contributions are welcome! If you find bugs or have suggestions for improvements:

Fork this repository

Create a new branch (git checkout -b feature/YourFeature)

Commit your changes (git commit -am 'Add new feature')

Push to your branch (git push origin feature/YourFeature)

Open a Pull Request

License
This project is open-source and available under the MIT License.

Contact
For questions or feedback, feel free to reach out to the project maintainer: @mpoole1027
