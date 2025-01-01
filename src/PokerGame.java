import factory.DeckFactory;
import handler.InputHandler;
import model.Card;
import model.Player;
import model.Deck;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

import static util.CardComparisonUtils.evaluateWinner;
import static util.CardComparisonUtils.formatCards;

public class PokerGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        InputHandler inputHandler = new InputHandler(scanner);

        System.out.print("Do you want to deal cards automatically? (yes/no): ");
        boolean autoDeal = scanner.nextLine().trim().equalsIgnoreCase("yes");

        int numberOfPlayers = inputHandler.getNumberOfPlayers();

        List<Player> players = new ArrayList<>();
        Deck deck = DeckFactory.createDeck();

        for (int i = 1; i <= numberOfPlayers; i++) {
            String name = inputHandler.getPlayerName(i);
            List<Card> cards;

            if (autoDeal) {
                cards = deck.dealHand(5);
                System.out.println(name + "'s hand: " + formatCards(cards));
            } else {
                cards = inputHandler.getPlayerCards();
            }

            players.add(new Player(name, cards));
        }

        String winner = evaluateWinner(players);
        System.out.println("Winner: " + winner);
    }

}