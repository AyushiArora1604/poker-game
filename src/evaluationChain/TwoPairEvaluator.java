package evaluationChain;

import model.Card;
import model.Suit;
import model.Player;
import model.Rank;
import model.HandRank;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Comparator;
import java.util.stream.Collectors;

public class TwoPairEvaluator implements HandEvaluator{

    public static final TwoPairEvaluator INSTANCE = new TwoPairEvaluator();

    @Override
    public HandEvaluator evaluate(List<Card> hand, Map<Rank, Integer> rankCount, Map<Suit, Integer> suitCount, boolean flush, boolean straight) {
        if (Collections.frequency(new ArrayList<>(rankCount.values()), 2) == 2) {
            return INSTANCE;
        }
        return null;
    }

    @Override
    public String evaluateWinner(List<Player> players) {
        return players.stream()
                .max(Comparator.comparing((Player p) -> getHighestPairRank(p))
                        .thenComparing(p -> getSecondPairRank(p))
                        .thenComparing(p -> getHighestSuitOfPair(p, getHighestPairRank(p)))
                        .thenComparing(p -> getHighestSuitOfPair(p, getSecondPairRank(p))))
                .orElseThrow(() -> new IllegalArgumentException("Player list cannot be empty"))
                .getName();
    }

    private Rank getHighestPairRank(Player player) {
        return getPairRanks(player).get(0);
    }

    private Rank getSecondPairRank(Player player) {
        return getPairRanks(player).get(1);
    }

    private Suit getHighestSuitOfPair(Player player, Rank rank) {
        return player.getHand().stream()
                .filter(card -> card.getRank().equals(rank))
                .max(Comparator.comparing(card -> card.getSuit().ordinal()))
                .orElseThrow(() -> new IllegalStateException("Suit not found"))
                .getSuit();
    }

    private List<Rank> getPairRanks(Player player) {
        return player.getHand().stream()
                .collect(Collectors.groupingBy(Card::getRank, Collectors.counting()))
                .entrySet().stream()
                .filter(entry -> entry.getValue() == 2)
                .map(Map.Entry::getKey)
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
    }

    @Override
    public HandRank getHandRank() {
        return HandRank.TWO_PAIR;
    }
}
