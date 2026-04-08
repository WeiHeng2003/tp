package seedu.cardcollector.card;

public enum CardSortCriteria {
    INDEX("index"),
    NAME("name"),
    QUANTITY("quantity"),
    PRICE("price"),
    SET("set"),
    RARITY("rarity"),
    CONDITION("condition"),
    LANGUAGE("language"),
    NUMBER("number"),
    NOTE("note"),
    ADDED("added"),
    MODIFIED("modified"),
    REMOVED("removed");

    private final String keyword;

    CardSortCriteria(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword() {
        return keyword;
    }
}
