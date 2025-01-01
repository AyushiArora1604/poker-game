package evaluationChain;

import model.Card;
import model.Suit;
import model.Player;
import model.Rank;
import model.HandRank;

import java.util.List;
import java.util.Map;

public class FourOfAKindEvaluator implements HandEvaluator{

    public static final FourOfAKindEvaluator INSTANCE = new FourOfAKindEvaluator();

    @Override
    public HandEvaluator evaluate(List<Card> hand, Map<Rank, Integer> rankCount, Map<Suit, Integer> suitCount, boolean flush, boolean straight) {
        if (rankCount.containsValue(4)) {
            return INSTANCE;
        }
        return null;
    }

    @Override
    public String evaluateWinner(List<Player> players) {
        Player winner = null;
        Rank highestRank = null;

        for (Player player : players) {
            Rank fourOfAKindRank = null;

            for (Card card : player.getHand()) {
                if (fourOfAKindRank != null) continue;

                int count = (int) player.getHand().stream().filter(c -> c.getRank() == card.getRank()).count();
                if (count == 4) {
                    fourOfAKindRank = card.getRank();
                }
            }

            if (fourOfAKindRank != null) {
                if (highestRank == null || fourOfAKindRank.ordinal() > highestRank.ordinal()) {
                    highestRank = fourOfAKindRank;
                    winner = player;
                }
            }
        }

        return winner != null ? winner.getName() : "No Winner";
    }

    @Override
    public HandRank getHandRank() {
        return HandRank.FOUR_OF_A_KIND;
    }
}
