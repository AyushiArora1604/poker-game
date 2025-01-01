package evaluationChain;

import model.Card;
import model.Suit;
import model.Player;
import model.Rank;
import model.HandRank;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OnePairEvaluator implements HandEvaluator{

    public static final OnePairEvaluator INSTANCE = new OnePairEvaluator();

    @Override
    public HandEvaluator evaluate(List<Card> hand, Map<Rank, Integer> rankCount, Map<Suit, Integer> suitCount, boolean flush, boolean straight) {
        if (rankCount.containsValue(2)) {
            return INSTANCE;
        }
        return null;
    }

    @Override
    public String evaluateWinner(List<Player> players) {
        Player winner = null;
        Rank bestRank = null;
        Suit bestSuit = null;

        for (Player player : players) {
            Card bestPairCard = player.getHand().stream()
                    .collect(Collectors.groupingBy(Card::getRank)) // Group by rank
                    .values().stream()
                    .filter(group -> group.size() == 2) // Only pairs
                    .flatMap(List::stream) // Flatten pairs
                    .max(Comparator.comparing(Card::getRank) // Compare by rank first
                            .thenComparing(Card::getSuit)) // Compare by suit if ranks are equal
                    .orElse(null);

            if (bestPairCard != null) {
                if (winner == null ||
                        bestPairCard.getRank().compareTo(bestRank) > 0 || // Higher rank
                        (bestPairCard.getRank().equals(bestRank) && bestPairCard.getSuit().compareTo(bestSuit) > 0)) { // Tie-breaker by suit
                    winner = player;
                    bestRank = bestPairCard.getRank();
                    bestSuit = bestPairCard.getSuit();
                }
            }
        }

        return (winner == null) ? "no winner" : winner.getName();
    }

    @Override
    public HandRank getHandRank() {
        return HandRank.ONE_PAIR;
    }
}