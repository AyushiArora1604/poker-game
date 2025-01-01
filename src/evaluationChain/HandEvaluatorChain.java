package evaluationChain;

import model.Card;
import model.Rank;
import model.Suit;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class HandEvaluatorChain {
    private static final List<HandEvaluator> evaluators = Arrays.asList(
            new RoyalFlushEvaluator(),
            new StraightFlushEvaluator(),
            new FourOfAKindEvaluator(),
            new FullHouseEvaluator(),
            new FlushEvaluator(),
            new StraightEvaluator(),
            new ThreeOfAKindEvaluator(),
            new TwoPairEvaluator(),
            new OnePairEvaluator(),
            new HighCardEvaluator()
    );

    public static HandEvaluator evaluate(List<Card> hand, Map<Rank, Integer> rankCount, Map<Suit, Integer> suitCount, boolean flush, boolean straight) {
        for (HandEvaluator evaluator : evaluators) {
            HandEvaluator result = evaluator.evaluate(hand, rankCount, suitCount, flush, straight);
            if (result != null) {
                return result;
            }
        }
        return null;
    }
}
