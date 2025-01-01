package validate;

import model.Card;
import model.Rank;
import model.Suit;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CardValidator {
    private final Set<Card> usedCards;

    public CardValidator() {
        this.usedCards = new HashSet<>();
    }

    public Card validateCard(String cardInput) {
        String[] parts = cardInput.split("-");

        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid card format: " + cardInput);
        }

        Rank rank = Rank.valueOf(parts[0].toUpperCase());
        Suit suit = Suit.valueOf(parts[1].toUpperCase());

        return new Card(rank, suit);
    }

    public List<Card> validateCards(String[] cardInputs) {
        if (cardInputs.length != 5) {
            throw new IllegalArgumentException("You must enter exactly 5 cards.");
        }

        List<Card> cards = new ArrayList<>();
        Set<Card> tempSet = new HashSet<>();

        for (String input : cardInputs) {
            Card card = validateCard(input);

            if (!tempSet.add(card)) {
                throw new IllegalArgumentException("Duplicate card within player: " + input);
            }

            if (!usedCards.add(card)) {
                throw new IllegalArgumentException("Duplicate card across players: " + input);
            }

            cards.add(card);
        }

        return cards;
    }
}
