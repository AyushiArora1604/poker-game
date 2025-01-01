package evaluationChain;

import model.Card;
import model.Suit;
import model.Player;
import model.Rank;
import model.HandRank;

import java.util.List;
import java.util.Map;

public interface HandEvaluator {
    HandEvaluator evaluate(List<Card> hand, Map<Rank, Integer> rankCount, Map<Suit, Integer> suitCount, boolean flush, boolean straight);
    String evaluateWinner(List<Player> players);

    HandRank getHandRank();
}
