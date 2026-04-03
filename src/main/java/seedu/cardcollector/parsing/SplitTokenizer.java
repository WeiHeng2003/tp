package seedu.cardcollector.parsing;

import java.util.Arrays;
import java.util.ArrayList;

public class SplitTokenizer {
    private final String regex;
    private ArrayList<String> tokens;

    public SplitTokenizer(String regex) {
        this.regex = regex;
        this.tokens = new ArrayList<>();
    }

    public void tokenize(String input) {
        tokens = new ArrayList<>(Arrays.asList(input.split(regex)));
    }

    public String getString(int index) {
        if (index >= 0 && index < tokens.size()) {
            return tokens.get(index);
        }

        return null;
    }
}
