package handler;

import model.Card;
import validate.CardValidator;

import java.util.List;
import java.util.Scanner;

public class InputHandler {
    private final Scanner scanner;
    private final CardValidator validator;

    private static final int MIN_PLAYERS = 2;
    private static final int MAX_PLAYERS = 10;

    public InputHandler(Scanner scanner) {
        this.scanner = scanner;
        this.validator = new CardValidator();
    }


    public int getNumberOfPlayers() {
        int numberOfPlayers;
        while (true) {
            try {
                System.out.print("Enter number of players (" + MIN_PLAYERS + " to " + MAX_PLAYERS + "): " );
                numberOfPlayers = Integer.parseInt(scanner.nextLine().trim());
                if (numberOfPlayers < MIN_PLAYERS || numberOfPlayers > MAX_PLAYERS) {
                    System.out.println("Invalid input! Number of players must be between "
                            + MIN_PLAYERS + " and " + MAX_PLAYERS + ".");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
            }
        }

        return numberOfPlayers;
    }


    public String getPlayerName(int playerNumber) {
        while (true) {
            System.out.print("Player " + playerNumber + " Name: ");
            String name = scanner.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println("Player name cannot be empty. Try again.");
            } else {
                return name;
            }
        }
    }


    public List<Card> getPlayerCards() {
        while (true) {
            try {
                System.out.println("Enter 5 cards (format: RANK-SUIT, e.g., ACE-SPADES)");
                System.out.print("Note - Each card must be separated by a single space: ");
                String input = scanner.nextLine().trim();
                String[] cardInputs = input.split(" ");

                return validator.validateCards(cardInputs);
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage() + " Try again.");
            }
        }
    }
}
