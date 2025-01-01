package factory;

import model.Deck;

public class DeckFactory {
    public static Deck createDeck() {
        return new Deck();
    }
}
