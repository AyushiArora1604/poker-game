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

public class RoyalFlushEvaluator implements HandEvaluator{

    public static final RoyalFlushEvaluator INSTANCE = new RoyalFlushEvaluator();

    @Override
    public HandEvaluator evaluate(List<Card> hand, Map<Rank, Integer> rankCount, Map<Suit, Integer> suitCount, boolean flush, boolean straight) {
        if (flush && straight && isRoyalFlush(hand)) {
            return INSTANCE; // Royal Flush
        }
        return null;
    }

    private boolean isRoyalFlush(List<Card> hand) {

        boolean isRoyalFlush;
        List<Rank> sortedRanks = hand.stream()
                .map(Card::getRank)
                .sorted()
                .collect(Collectors.toList());

        isRoyalFlush = sortedRanks.get(sortedRanks.size()-1) == Rank.ACE && sortedRanks.get(0) == Rank.TEN;
        return isRoyalFlush;
    }


    @Override
    public String evaluateWinner(List<Player> players) {
        return players.stream()
                .max(Comparator.comparing(player -> player.getHighestCard().getSuit().ordinal()))
                .map(Player::getName)
                .orElse("No Winner");
    }

    @Override
    public HandRank getHandRank() {
        return HandRank.ROYAL_FLUSH;
    }
}
