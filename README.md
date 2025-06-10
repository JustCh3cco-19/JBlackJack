# JBlackJack

JBlackJack is a Blackjack card game built in Java. This project was developed for the Software Engineering Methods exam at the University of Rome "La Sapienza". [cite_start]It features a graphical user interface built with **Java Swing**  [cite_start]and implements various design patterns for a modular and extensible structure.

---

## 📋 Features

* **User Profiles**: Players can create a profile by choosing a nickname and an avatar. [cite_start]Player statistics, such as games played, wins, losses, level, and experience, are tracked and can be viewed.
* **Game Logic**: The game follows standard Blackjack rules, where the player competes against the house. [cite_start]It includes one human player, two bot players, and the dealer.
* **Progression System**: Players start at level zero and earn 100 experience points for each win. [cite_start]To advance to the next level, a player needs to accumulate points equal to their current level multiplied by 1000.
* **Audio Feedback**: The game includes background music and sound effects for actions like drawing a card or standing.

---

## 🛠️ Getting Started

Follow these instructions to compile and run the project.

### Prerequisites

* Java Development Kit (JDK)

### Compilation and Execution

1.  **Navigate to the source directory**:
    Open your terminal and change the directory to where your `.java` files are located. Assuming your package structure is `main/blackjack`, you would navigate to the directory containing the `main` folder.

2.  **Compile the project**:
    Run the following command to compile the Java source files.

    ```bash
    javac -d . main/blackjack/JBlackJack.java
    ```

3.  **Run the application**:
    After successful compilation, run the main class with this command.

    ```bash
    java main.blackjack.JBlackJack
    ```

---

## 💡 Project Snippet

JBlackJack is a simple Blackjack game developed in Java Swing. [cite_start]It allows users to create a profile, play against AI opponents, and track their statistics. [cite_start]The project showcases the use of several design patterns, including MVC, Singleton, and Factory Method, to create a well-structured and maintainable application.
