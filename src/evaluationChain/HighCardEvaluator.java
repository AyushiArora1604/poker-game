package evaluationChain;

import model.Card;
import model.Rank;
import model.Suit;
import model.Player;
import model.HandRank;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class HighCardEvaluator implements HandEvaluator{

    public static final HighCardEvaluator INSTANCE = new HighCardEvaluator();

    @Override
    public HandEvaluator evaluate(List<Card> hand, Map<Rank, Integer> rankCount, Map<Suit, Integer> suitCount, boolean flush, boolean straight) {
        return INSTANCE;
    }

    @Override
    public String evaluateWinner(List<Player> players) {
        return players.stream()
                .max(Comparator.comparingInt((Player p) -> p.getHighestCard().getRank().ordinal())
                        .thenComparingInt(p -> p.getHighestCard().getSuit().ordinal()))
                .map(Player::getName)
                .orElse("No Winner");
    }

    @Override
    public HandRank getHandRank() {
        return HandRank.HIGH_CARD;
    }
}
