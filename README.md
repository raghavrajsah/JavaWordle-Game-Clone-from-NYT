# JWordle: Java Implementation of the Wordle Game

## Overview
JWordle is a Java-based implementation of the popular word game Wordle. This project involves creating a functional game with a graphical user interface (GUI) that closely mimics the original web-based Wordle game. The implementation focuses on enhancing understanding of Java syntax and reinforcing programmatic design principles.


### How to Play Java Wordle Game

1. **Clone the Repository**

   First, clone the repository to your local machine. Open your terminal and run:

   ```sh
   git clone git@github.com:raghavrajsah/JavaWordle-Game-Clone-from-NYT.git
   ```

2. **Navigate to the Project Directory**

   Change into the project directory:

   ```sh
   cd JavaWordle-Game-Clone-from-NYT
   ```

3. **Compile the Code**

   Compile the Java source files. Ensure you are in the directory containing the `src` folder:

   ```sh
   javac JWordleLauncher.java
   ```

4. **Run the Game**

   Run the game and enjoy:

   ```sh
   java JWordleLauncher
   ```


## Basic Rules
The core gameplay involves the following rules:
- Players have six attempts to guess a randomly chosen five-letter word.
- After each guess, players receive feedback:
  - Green indicates the letter is in the correct position.
  - Yellow indicates the letter is in the word but in the wrong position.
  - Gray indicates the letter is not in the word.
- All guesses and the secret word must be valid English dictionary words. Duplicate letters are allowed.

## GUI Components
The game features a comprehensive GUI, which includes:
- **Game Grid**: Displays the player's guesses and the feedback for each letter.
- **Debug Toggle Notifications**: Shows active debug toggles to assist with development.
- **Graphical Keyboard Interface**: Allows users to input guesses through clickable keys that change color based on feedback.
- **Dialogue Area**: Displays messages and feedback to the user.

## Game Flow
1. **Initialization**: The secret word is randomly chosen from a predefined list.
2. **Guess Input**: Players enter their guesses using either the graphical keyboard or their physical keyboard.
3. **Feedback**: After each guess, the game provides color-coded feedback for each letter.
4. **Completion**: The game ends when the player correctly guesses the word or exhausts all six attempts.

## Word Data Files
The JWordle program reads two types of text files:
- **Secret Words File**: Contains possible secret words.
- **Valid Guesses File**: Contains valid guesses that players can enter.
The format for these files is:
- The first line contains the number of words in the file.
- Each subsequent line contains a single five-letter word.

## Provided Code Structure
### JWordleLauncher.java
This file contains the main method to launch the game. It also includes three debug toggles to facilitate testing and debugging:
- Debug toggles can be enabled/disabled to modify game functionality and provide transparency during development.

### GameLogic.java
This file contains the core game logic, including:
- **initializeGame()**: Initializes the game and selects the secret word.
- **reactToKey(char key)**: Responds to player input, updating the game state accordingly.

### GameGUI.java
This file handles the graphical user interface, including:
- **getSecretWordArr()**: Retrieves the secret word as a character array.
- **setGridChar(int row, int col, char letter)**: Sets the character at a specific position in the grid.
- **getGridChar(int row, int col)**: Retrieves the character at a specific position in the grid.
- **getGridColor(int row, int col)**: Retrieves the color of a specific grid cell.
- **setGridColor(int row, int col, Color newColor)**: Sets the color of a specific grid cell.
- **getKeyColor(char key)**: Retrieves the color of a specific key on the keyboard.
- **setKeyColor(char key, Color newColor)**: Sets the color of a specific key on the keyboard.
- **wiggle(int row)**: Animates a row to indicate an invalid guess.
- **gameOver(boolean didPlayerWin)**: Ends the game, indicating whether the player won or lost.


This project showcases the implementation of a complex word game in Java, focusing on both front-end GUI elements and back-end logic. The structured approach and comprehensive design ensure a functional and enjoyable gaming experience for users.
