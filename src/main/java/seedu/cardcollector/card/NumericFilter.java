package seedu.cardcollector.card;

public class NumericFilter {
    public enum Operator {
        EXACT, GT, GTE, LT, LTE
    }

    private final Operator operator;
    private final double value;

    public NumericFilter(Operator operator, double value) {
        this.operator = operator;
        this.value = value;
    }

    /**
     * Parses e.g. "30", ">30", ">=30", "<3.0", "<=3.5".
     * Exact match if no operator.
     * Throws NumberFormatException on bad input (caught by Parser).
     */
    public static NumericFilter parse(String conditionStr) {
        if (conditionStr == null || conditionStr.trim().isEmpty()) {
            return null;
        }
        String s = conditionStr.trim();

        Operator op = Operator.EXACT;
        String numStr = s;

        if (s.startsWith(">=")) {
            op = Operator.GTE;
            numStr = s.substring(2).trim();
        } else if (s.startsWith("<=")) {
            op = Operator.LTE;
            numStr = s.substring(2).trim();
        } else if (s.startsWith(">")) {
            op = Operator.GT;
            numStr = s.substring(1).trim();
        } else if (s.startsWith("<")) {
            op = Operator.LT;
            numStr = s.substring(1).trim();
        }

        double val = Double.parseDouble(numStr);
        return new NumericFilter(op, val);
    }

    public boolean matches(int cardValue) {
        return matches((double) cardValue);
    }

    public boolean matches(double cardValue) {
        return switch (operator) {
        case EXACT -> cardValue == value;
        case GT -> cardValue > value;
        case GTE -> cardValue >= value;
        case LT -> cardValue < value;
        case LTE -> cardValue <= value;
        };
    }
}
