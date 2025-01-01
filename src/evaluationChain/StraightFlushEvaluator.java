package evaluationChain;

import model.Card;
import model.Suit;
import model.Player;
import model.Rank;
import model.HandRank;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static util.CardComparisonUtils.getHighestRank;

public class StraightFlushEvaluator implements HandEvaluator{
    public static final StraightFlushEvaluator INSTANCE = new StraightFlushEvaluator();

    @Override
    public HandEvaluator evaluate(List<Card> hand, Map<Rank, Integer> rankCount, Map<Suit, Integer> suitCount, boolean flush, boolean straight) {
        if (flush && straight) {
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

    @Override
    public HandRank getHandRank() {
        return HandRank.STRAIGHT_FLUSH;
    }


}
