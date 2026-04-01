package seedu.cardcollector.card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CardsAnalytics {
    private final int distinctCards;
    private final int totalQuantity;
    private final double totalValue;

    private final ArrayList<CardMetric> mostExpensiveCards;
    private final ArrayList<CardMetric> topCardsByHoldingValue;
    private final ArrayList<CardMetric> cheapestCards;

    private final ArrayList<SetMetric> topSetsByCount;
    private final ArrayList<SetValueMetric> topSetsByValue;

    private final int zeroPriceCards;
    private final int lowPriceCards;
    private final int mediumPriceCards;
    private final int upperMidPriceCards;
    private final int highPriceCards;

    private final int cardsWithNotes;
    private final int cardsWithSetInformation;

    public CardsAnalytics(
            int distinctCards,
            int totalQuantity,
            double totalValue,
            ArrayList<CardMetric> mostExpensiveCards,
            ArrayList<CardMetric> topCardsByHoldingValue,
            ArrayList<CardMetric> cheapestCards,
            ArrayList<SetMetric> topSetsByCount,
            ArrayList<SetValueMetric> topSetsByValue,
            int zeroPriceCards,
            int lowPriceCards,
            int mediumPriceCards,
            int upperMidPriceCards,
            int highPriceCards,
            int cardsWithNotes,
            int cardsWithSetInformation
    ) {
        this.distinctCards = distinctCards;
        this.totalQuantity = totalQuantity;
        this.totalValue = totalValue;
        this.mostExpensiveCards = mostExpensiveCards;
        this.topCardsByHoldingValue = topCardsByHoldingValue;
        this.cheapestCards = cheapestCards;
        this.topSetsByCount = topSetsByCount;
        this.topSetsByValue = topSetsByValue;
        this.zeroPriceCards = zeroPriceCards;
        this.lowPriceCards = lowPriceCards;
        this.mediumPriceCards = mediumPriceCards;
        this.upperMidPriceCards = upperMidPriceCards;
        this.highPriceCards = highPriceCards;
        this.cardsWithNotes = cardsWithNotes;
        this.cardsWithSetInformation = cardsWithSetInformation;
    }

    public int getDistinctCards() {
        return distinctCards;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public double getTotalValue() {
        return totalValue;
    }

    public List<CardMetric> getMostExpensiveCards() {
        return Collections.unmodifiableList(mostExpensiveCards);
    }

    public List<CardMetric> getTopCardsByHoldingValue() {
        return Collections.unmodifiableList(topCardsByHoldingValue);
    }

    public List<CardMetric> getCheapestCards() {
        return Collections.unmodifiableList(cheapestCards);
    }

    public List<SetMetric> getTopSetsByCount() {
        return Collections.unmodifiableList(topSetsByCount);
    }

    public List<SetValueMetric> getTopSetsByValue() {
        return Collections.unmodifiableList(topSetsByValue);
    }

    public int getZeroPriceCards() {
        return zeroPriceCards;
    }

    public int getLowPriceCards() {
        return lowPriceCards;
    }

    public int getMediumPriceCards() {
        return mediumPriceCards;
    }

    public int getUpperMidPriceCards() {
        return upperMidPriceCards;
    }

    public int getHighPriceCards() {
        return highPriceCards;
    }

    public int getCardsWithNotes() {
        return cardsWithNotes;
    }

    public int getCardsWithSetInformation() {
        return cardsWithSetInformation;
    }

    public static class CardMetric {
        private final Card card;
        private final double lineValue;

        public CardMetric(Card card, double lineValue) {
            this.card = card;
            this.lineValue = lineValue;
        }

        public Card getCard() {
            return card;
        }

        public double getLineValue() {
            return lineValue;
        }
    }

    public static class SetMetric {
        private final String setName;
        private final int totalCount;

        public SetMetric(String setName, int totalCount) {
            this.setName = setName;
            this.totalCount = totalCount;
        }

        public String getSetName() {
            return setName;
        }

        public int getTotalCount() {
            return totalCount;
        }
    }

    public static class SetValueMetric {
        private final String setName;
        private final double totalValue;

        public SetValueMetric(String setName, double totalValue) {
            this.setName = setName;
            this.totalValue = totalValue;
        }

        public String getSetName() {
            return setName;
        }

        public double getTotalValue() {
            return totalValue;
        }
    }
}
