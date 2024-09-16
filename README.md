🕹️ PokéDex App for Generation 1 Pokémons

Welcome to the Pokémon's 1st Generation Data App! Immerse yourself in the nostalgic world of 1st generation Pokémon with detailed data, interactive features, and engaging sound effects. This app provides comprehensive information on Pokémon, including their weaknesses, evolutions, and powers, along with an immersive experience through voice interactions.


🌟 Features

Pokémon Data: Detailed insights into 1st generation Pokémon, including:

Weaknesses: Explore each Pokémon's vulnerabilities.

Evolutions: Discover next and previous evolutions.

Powers: Learn about unique abilities and powers.

Voice Interaction: Hear the iconic voices of Pokémon by clicking on their images.


✨ Dynamic UI:

CardView: Beautifully designed cards for each Pokémon.

RecyclerView: Efficiently displays Pokémon and other lists.

Efficient Image Handling: Glide is used for smooth image loading and caching.

API Integration: Retrofit manages API requests for seamless data retrieval.


🚀 Installation

Prerequisites

Java 8 or higher

Android Studio for development

Getting Started


Clone the Repository:

bash 
Copy code
git clone https://github.com/Dhawan-Makwana98/PokeDex.git

Navigate to the Project Directory:

bash
Copy code
cd PokeDex

Open with Android Studio: Launch Android Studio and open the project.

Build and Run:

Build the project by selecting Build > Make Project from the menu.

Run the app on an emulator or physical device by selecting Run > Run 'app'.


📖 Usage

Launch the App: Open Android Studio and run the project, or install the APK on your Android device.

Explore Pokémon: Browse through the RecyclerView list to view different 1st generation Pokémon.

View Pokémon Details: Tap on a Pokémon's CardView to access detailed information including weaknesses, evolutions, and powers.

Listen to Voices: Click on a Pokémon’s image to play its unique voice.


🔧 Technologies Used

Retrofit: For making API requests and handling JSON data.

Glide: For efficient image loading and caching.

CardView: To create visually appealing and informative Pokémon cards.

RecyclerView: For managing lists of Pokémon and other data.

Audio Controls: Custom implementation for playing Pokémon voices on image clicks.


🌐 API

The app integrates with the Pokémon data API. For more details, check the repository.


API Endpoint: https://raw.githubusercontent.com/Biuni/PokemonGO-Pokedex/master/pokedex.json

Authentication: No authentication required.

Example Request:

http
Copy code
GET /pokemon-data HTTP/1.1
Host: raw.githubusercontent.com


🤝 Contributing

We welcome contributions to enhance the app!

Fork the Repository on GitHub.

Create a New Branch:
bash
Copy code
git checkout -b feature/your-feature


Make Your Changes: Implement your feature or fix.

Commit Your Changes:
bash
Copy code
git commit -am 'Add new feature'


Push to Your Branch:
bash
Copy code
git push origin feature/your-feature


Create a Pull Request: Submit your changes for review.

🙏 Acknowledgments

Special thanks to the Pokémon community and data providers.

The Pokémon data API and voice files are essential to the app's functionality.

Inspired by the rich Pokémon universe and dedicated to fans worldwide.
