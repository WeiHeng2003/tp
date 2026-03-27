package seedu.cardcollector;

import java.io.IOException;

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
    private final Storage storage;

    public CardCollector() {
        ui = new Ui();
        parser = new Parser();
        storage = Storage.createDefault();

        AppState initialState = loadState();
        inventory = initialState.getInventory();
        wishlist = initialState.getWishlist();
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
                saveState();
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

    private AppState loadState() {
        try {
            return storage.load();
        } catch (IOException e) {
            System.err.println("Failed to load saved data: " + e.getMessage());
            return new AppState(new CardsList(), new CardsList());
        }
    }

    private void saveState() {
        try {
            storage.save(new AppState(inventory, wishlist));
        } catch (IOException e) {
            System.err.println("Failed to save data: " + e.getMessage());
        }
    }
}
