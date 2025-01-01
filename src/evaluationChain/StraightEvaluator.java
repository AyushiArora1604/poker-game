package evaluationChain;

import model.Card;
import model.Suit;
import model.Rank;
import model.Player;
import model.HandRank;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static util.CardComparisonUtils.getHighestRank;

public class StraightEvaluator implements HandEvaluator{

    public static final StraightEvaluator INSTANCE = new StraightEvaluator();

    @Override
    public HandEvaluator evaluate(List<Card> hand, Map<Rank, Integer> rankCount, Map<Suit, Integer> suitCount, boolean flush, boolean straight) {
        if (straight) {
            return INSTANCE;
        }
        return null;
    }

    @Override
    public String evaluateWinner(List<Player> players) {
        return players.stream()
                .max(Comparator.comparingInt((Player p) -> getHighestRank(p.getHand()))
                        .thenComparingInt(p -> p.getHighestCard().getSuit().ordinal()))
                .map(Player::getName)
                .orElse("No Winner");
    }

    public HandRank getHandRank() {
        return HandRank.STRAIGHT;
    }
}
