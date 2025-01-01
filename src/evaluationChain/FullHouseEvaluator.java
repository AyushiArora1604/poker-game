package evaluationChain;

import model.Card;
import model.Suit;
import model.Player;
import model.Rank;
import model.HandRank;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FullHouseEvaluator implements HandEvaluator{
    public static final FullHouseEvaluator INSTANCE = new FullHouseEvaluator();

    @Override
    public HandEvaluator evaluate(List<Card> hand, Map<Rank, Integer> rankCount, Map<Suit, Integer> suitCount, boolean flush, boolean straight) {
        if (rankCount.containsValue(3) && rankCount.containsValue(2)) {
            return INSTANCE; // Full House
        }
        return null;
    }

    @Override
    public String evaluateWinner(List<Player> players) {
        Player winner = null;

        for (Player player : players) {
            Map<Rank, Integer> rankCount = getRankCount(player);
            Rank threeOfAKindRank = null;
            Rank pairRank = null;

            for (Map.Entry<Rank, Integer> entry : rankCount.entrySet()) {
                if (entry.getValue() == 3) threeOfAKindRank = entry.getKey();
                if (entry.getValue() == 2) pairRank = entry.getKey();
            }

            if (threeOfAKindRank != null && pairRank != null) {
                if (winner == null) {
                    winner = player;
                } else {
                    int threeComparison = compareRanks(threeOfAKindRank, getRankOfPlayer(winner, 3));
                    if (threeComparison > 0) {
                        winner = player; // Player has a higher Three of a Kind
                    } else if (threeComparison == 0) {
                        int pairComparison = compareRanks(pairRank, getRankOfPlayer(winner, 2));
                        if (pairComparison > 0) {
                            winner = player; // Player has a higher Pair
                        } else if (pairComparison == 0) {
                            winner = compareHighestCardSuit(winner, player, threeOfAKindRank);
                        }
                    }
                }
            }
        }
        return winner != null ? winner.getName() : "No Winner";
    }

    private int compareRanks(Rank rank1, Rank rank2) {
        return Integer.compare(rank1.ordinal(), rank2.ordinal());
    }

    private Rank getRankOfPlayer(Player player, int count) {
        Map<Rank, Integer> rankCount = getRankCount(player);
        return rankCount.entrySet().stream()
                .filter(entry -> entry.getValue() == count)
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);
    }

    private Player compareHighestCardSuit(Player winner, Player challenger, Rank threeOfAKindRank) {
        Card winnerCard = getHighestCardOfRank(winner, threeOfAKindRank);
        Card challengerCard = getHighestCardOfRank(challenger, threeOfAKindRank);

        int suitComparison = Integer.compare(winnerCard.getSuit().ordinal(), challengerCard.getSuit().ordinal());
        if (suitComparison == 0) {
            return winnerCard.getRank().ordinal() > challengerCard.getRank().ordinal() ? winner : challenger;
        }
        return suitComparison > 0 ? winner : challenger;
    }

    private Map<Rank, Integer> getRankCount(Player player) {
        Map<Rank, Integer> rankCount = new HashMap<>();
        for (Card card : player.getHand()) {
            rankCount.put(card.getRank(), rankCount.getOrDefault(card.getRank(), 0) + 1);
        }
        return rankCount;
    }

    private Card getHighestCardOfRank(Player player, Rank rank) {
        return player.getHand().stream()
                .filter(card -> card.getRank() == rank)
                .max(Comparator.comparingInt(card -> card.getSuit().ordinal()))
                .orElse(null);
    }

    @Override
    public HandRank getHandRank() {
        return HandRank.FULL_HOUSE;
    }
}
