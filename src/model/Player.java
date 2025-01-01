package model;

import evaluationChain.HandEvaluator;
import evaluationChain.HandEvaluatorChain;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Comparator;
import java.util.Set;
import java.util.Collections;
import java.util.Arrays;

public class Player {
    private final String name;
    private final List<Card> hand;

    public Player(String name, List<Card> hand) {
        this.name = name;
        this.hand = hand;
    }

    public String getName() {
        return name;
    }

    public List<Card> getHand() {
        return hand;
    }

    public Card getHighestCard() {
        return hand.stream().max(Comparator.comparing(Card::getRank)).orElse(null);
    }

    public HandEvaluator evaluateHand() {
        Map<Rank, Integer> rankCount = new HashMap<>();
        Map<Suit, Integer> suitCount = new HashMap<>();

        for (Card card : hand) {
            rankCount.put(card.getRank(), rankCount.getOrDefault(card.getRank(), 0) + 1);
            suitCount.put(card.getSuit(), suitCount.getOrDefault(card.getSuit(), 0) + 1);
        }

        boolean flush = suitCount.size() == 1;
        boolean straight = isStraight(rankCount.keySet());

        return HandEvaluatorChain.evaluate(hand, rankCount, suitCount, flush, straight);
    }

    private boolean isStraight(Set<Rank> ranks) {
        List<Integer> ordinals = new ArrayList<>();
        for (Rank rank : ranks) {
            ordinals.add(rank.ordinal());
        }
        Collections.sort(ordinals);

        boolean isRegularStraight = true;
        for (int i = 0; i < ordinals.size() - 1; i++) {
            if (ordinals.get(i) + 1 != ordinals.get(i + 1)) {
                isRegularStraight = false;
                break;
            }
        }

        boolean isAceLowStraight = ordinals.containsAll(Arrays.asList(0, 1, 2, 3, 12));

        return isRegularStraight || isAceLowStraight;
    }

    @Override
    public String toString() {
        return name + "'s Hand: " + hand;
    }
}
