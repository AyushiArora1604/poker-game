
# Poker Hand Evaluator

A Java-based application to evaluate and determine the winner among multiple players based on poker hand rankings. This project implements a Poker Hand Evaluator supporting hand types like One Pair, Straight Flush, Royal Flush, and more, using Object-Oriented Design principles and the Chain of Responsibility pattern for extensibility.

## Table of Contents

* Features
* Technologies Used
* Installation
* Usage
* Rules and Validations
* Design Patterns Used
* License

## Features

* Evaluate Poker Hands — Determines the winner based on standard poker hand rankings.
* Validation Checks — Ensures valid inputs for player names, cards, and formats.
* Chain of Responsibility Pattern — Evaluates hand rankings sequentially for extensibility.
* Flexible Input Handling — Accepts user inputs via console with proper error handling.
* Custom Tie-breakers — Resolves ties based on rank and suit comparisons.

## Technologies Used
* Java 11 or later
* Java Streams API — For efficient data processing.
* Collections Framework — For managing card groups and rankings.

## Installation

1. Clone the Repository:

```bash
git clone https://github.com/AyushiArora1604/poker-game.git
cd poker-game
```

2. Compile the Code:

```bash
javac src/**/*.java
```

3. Run the Application:

```bash
java src/Main.java
```

## Usage
1. Enter the number of players (Minimum: 2, Maximum: 10).
2. Input player names and their respective 5-card hands (e.g., ACE-SPADES TEN-HEARTS).
3. The program evaluates the winner based on the highest-ranking hand.

## Rules and Validations
1. Number of Players: Minimum 2 and Maximum 10.
2. Input Format: Each card must be entered in the format RANK-SUIT (e.g., ACE-SPADES).
3. Duplicate Card Detection: Ensures no player or combination of players can have duplicate cards.
4. Valid Ranks and Suits: Supported ranks: 2, 3, 4, ..., 10, JACK, QUEEN, KING, ACE. Supported suits: SPADES, HEARTS, CLUBS, DIAMONDS.

## Design Patterns Used
### Chain of Responsibility Pattern
Why? Allows each evaluator (e.g., Royal Flush, Straight Flush, One Pair) to process a player's hand one by one, enabling extensibility for new rules.
How? Each evaluator checks its condition, and if no match is found, passes the responsibility to the next evaluator in the chain.

### Stream API for Optimization
Simplified pair and rank evaluations using Java Streams API for reduced complexity and better performance.

## Run the Project
1. Make sure Java is installed.
2. Make the script executable:
   ```bash
   chmod +x start.sh
   ```
   
3. Execute the script
    ```bash
    ./start.sh
    ```

## License
This project is licensed under the MIT License. See the LICENSE file for details.