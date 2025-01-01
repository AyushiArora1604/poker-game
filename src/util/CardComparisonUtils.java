package util;

import evaluationChain.HandEvaluator;
import model.Card;
import model.Player;

import java.util.List;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Comparator;
import java.util.stream.Collectors;

public class CardComparisonUtils {
    public static int getHighestRank(List<Card> cards) {
        List<Integer> ranks = cards.stream()
                .map(card -> card.getRank().ordinal())
                .sorted()
                .collect(Collectors.toList());

        if (ranks.equals(Arrays.asList(0, 1, 2, 3, 12))) {
            return 4;
        }
        return ranks.get(ranks.size() - 1);
    }

    public static String formatCards(List<Card> cards) {
        return cards.stream()
                .map(card -> card.getRank() + "-" + card.getSuit())
                .collect(Collectors.joining(" "));
    }

    public static String evaluateWinner(List<Player> players) {
        Map<HandEvaluator, List<Player>> groupedPlayers = players.stream()
                .collect(Collectors.groupingBy(
                        Player::evaluateHand,
                        LinkedHashMap::new,
                        Collectors.toList()
                ));

        Map.Entry<HandEvaluator, List<Player>> firstEntry = groupedPlayers.entrySet().stream().
                sorted(Comparator.comparing(entry -> entry.getKey().getHandRank().ordinal(), Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1,e2) -> e1,
                        LinkedHashMap::new
                )).entrySet().iterator().next();

        return firstEntry.getValue().size() == 1 ? firstEntry.getValue().get(0).getName() :
                firstEntry.getKey().evaluateWinner(firstEntry.getValue());
    }
}
