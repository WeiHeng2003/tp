package seedu.cardcollector.card;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CardSorter {
    /**
     * Returns a comparator for sorting cards according to the specified criteria.
     *
     * @param criteria The sorting criteria to use (e.g. INDEX, NAME, QUANTITY, etc.).
     * @return A comparator that sorts cards by the given criteria.
     */
    public static Comparator<Card> getSortComparator(CardSortCriteria criteria) {
        switch (criteria) {
        case INDEX -> {
            assert false : "index criteria should not use a comparator";
        }
        case NAME -> {
            return Comparator.comparing(Card::getName, String.CASE_INSENSITIVE_ORDER);
        }
        case QUANTITY -> {
            return Comparator.comparingInt(Card::getQuantity);
        }
        case PRICE -> {
            return Comparator.comparingDouble(Card::getPrice);
        }
        case SET -> {
            return Comparator.comparing(Card::getCardSet,
                    Comparator.nullsFirst(String.CASE_INSENSITIVE_ORDER));
        }
        case RARITY -> {
            return Comparator.comparing(Card::getRarity,
                    Comparator.nullsFirst(String.CASE_INSENSITIVE_ORDER));
        }
        case CONDITION -> {
            return Comparator.comparing(Card::getCondition,
                    Comparator.nullsFirst(String.CASE_INSENSITIVE_ORDER));
        }
        case LANGUAGE -> {
            return Comparator.comparing(Card::getLanguage,
                    Comparator.nullsFirst(String.CASE_INSENSITIVE_ORDER));
        }
        case NUMBER -> {
            return Comparator.comparing(Card::getCardNumber,
                    Comparator.nullsFirst(String.CASE_INSENSITIVE_ORDER));
        }
        case NOTE -> {
            return Comparator.comparing(Card::getNote,
                    Comparator.nullsFirst(String.CASE_INSENSITIVE_ORDER));
        }
        case ADDED -> {
            return Comparator.comparing(Card::getLastAdded,
                    Comparator.nullsFirst(Instant::compareTo));
        }
        case MODIFIED -> {
            return Comparator.comparing(Card::getLastModified,
                    Comparator.nullsFirst(Instant::compareTo));
        }
        case REMOVED -> {
            return Comparator.comparing(Card::getLastRemoved,
                    Comparator.nullsFirst(Instant::compareTo));
        }
        default -> {
            assert false : "Unhandled CardSortCriteria";
        }
        }
        return null;
    }

    /**
     * Returns a new sorted ArrayList of cards by the specified criteria,
     * results can be limited to a maximum size and ordered ascending or descending.
     *
     * @param cards The ArrayList of cards.
     * @param criteria The criteria to sort by (e.g. INDEX, NAME, QUANTITY, etc.).
     * @param maxLimit The maximum number of cards to return. Uses defaultMaxLimit if negative.
     * @param defaultMaxLimit The limit applied when maxLimit is negative.
     * @param isDescending True for descending order, false for ascending.
     * @return A new sorted list of cards.
     */
    public static ArrayList<Card> sort(
            ArrayList<Card> cards,
            CardSortCriteria criteria,
            int maxLimit,
            int defaultMaxLimit,
            boolean isDescending) {

        if (cards.isEmpty()) {
            return new ArrayList<>();
        }

        ArrayList<Card> cardsCopy = new ArrayList<>(cards);

        if (criteria == CardSortCriteria.INDEX && isDescending) {
            // Directly apply ascending/descending order,
            // without using comparator
            Collections.reverse(cardsCopy);
        }

        Stream<Card> cardsStream = cardsCopy.stream();
        if (criteria != CardSortCriteria.INDEX) {
            Comparator<Card> comparator = getSortComparator(criteria);

            assert comparator != null : "No available comparator for criteria";

            // Apply ascending/descending order using comparator
            if (isDescending) {
                comparator = comparator.reversed();
            }

            cardsStream = cardsStream.sorted(comparator);
        }

        int recordsLimit = (maxLimit < 0) ? defaultMaxLimit :
                Math.min(cards.size(), maxLimit);

        return cardsStream
                .limit(recordsLimit)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
