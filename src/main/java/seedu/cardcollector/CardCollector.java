package seedu.cardcollector;

import seedu.cardcollector.command.Command;
import seedu.cardcollector.command.CommandResult;
import seedu.cardcollector.exception.ParseBlankCommandException;
import seedu.cardcollector.exception.ParseInvalidArgumentException;
import seedu.cardcollector.exception.ParseUnknownCommandException;
import seedu.cardcollector.parsing.Parser;

public class CardCollector {
    private final Ui ui;
    private final CardsList inventory;
    private final CardsList wishlist;
    private final Parser parser;

    public CardCollector() {
        ui = new Ui();
        inventory = new CardsList();
        wishlist = new CardsList();
        parser = new Parser();
    }

    public void run() {
        ui.printWelcome();
        boolean isRunning = true;

        while (isRunning) {
            String input = ui.readInput().trim();

            boolean isWishlistCommand = false;
            String parseInput = input;

            if (input.toLowerCase().startsWith("wishlist ")) {
                isWishlistCommand = true;
                parseInput = input.substring(9).trim();
            }

            // Prevent "wishlist" alone from crashing parser
            if (parseInput.isEmpty()) {
                if (isWishlistCommand) {
                    ui.printUnknownCommandWarning("wishlist");
                }
            }

            try {
                Command command = parser.parse(parseInput);
                CardsList targetList = isWishlistCommand ? wishlist : inventory;
                CommandResult result = command.execute(ui, targetList);
                isRunning = !result.getIsExit();
            } catch (ParseBlankCommandException e) {
                ui.printBlankCommandWarning();
            } catch (ParseUnknownCommandException e) {
                ui.printUnknownCommandWarning(e.getMessage());
            } catch (ParseInvalidArgumentException e) {
                ui.printInvalidArgumentWarning(e.getMessage(), e.getUsage());
            }
        }
    }

    public static void main(String[] args) {
        new CardCollector().run();
    }
}
