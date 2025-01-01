package evaluationChain;

import model.Card;
import model.Suit;
import model.Rank;
import model.Player;
import model.HandRank;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class FlushEvaluator implements HandEvaluator{

    public static final FlushEvaluator INSTANCE = new FlushEvaluator();

    @Override
    public HandEvaluator evaluate(List<Card> hand, Map<Rank, Integer> rankCount, Map<Suit, Integer> suitCount, boolean flush, boolean straight) {
        if (flush) {
            return INSTANCE;
        }
        return null;
    }

    @Override
    public String evaluateWinner(List<Player> players) {
        return players.stream()
                .max(Comparator.comparingInt((Player p) -> p.getHighestCard().getSuit().ordinal())
                        .thenComparingInt(p -> p.getHighestCard().getRank().ordinal()))
                .map(Player::getName)
                .orElse("No Winner");
    }

    @Override
    public HandRank getHandRank() {
        return HandRank.FLUSH;
    }
}
