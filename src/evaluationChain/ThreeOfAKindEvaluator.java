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

public class ThreeOfAKindEvaluator implements HandEvaluator{

    public static final ThreeOfAKindEvaluator INSTANCE = new ThreeOfAKindEvaluator();

    @Override
    public HandEvaluator evaluate(List<Card> hand, Map<Rank, Integer> rankCount, Map<Suit, Integer> suitCount, boolean flush, boolean straight) {
        if (rankCount.containsValue(3)) {
            return INSTANCE;
        }
        return null;
    }

    @Override
    public String evaluateWinner(List<Player> players) {
        return players.stream()
                .max(Comparator.comparing(this::getThreeOfAKindRank))
                .map(Player::getName)
                .orElse("No Winner");
    }

    private Rank getThreeOfAKindRank(Player player) {
        Map<Rank, Long> rankCount = player.getHand().stream()
                .collect(Collectors.groupingBy(Card::getRank, Collectors.counting()));

        return rankCount.entrySet().stream()
                .filter(entry -> entry.getValue() == 3)
                .map(Map.Entry::getKey)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Player does not have Three of a Kind"));
    }

    @Override
    public HandRank getHandRank() {
        return HandRank.THREE_OF_A_KIND;
    }
}
