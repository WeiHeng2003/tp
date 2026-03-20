package seedu.cardcollector;

import seedu.cardcollector.command.Command;
import seedu.cardcollector.command.HistoryCommand;

import java.util.ArrayList;

public class CardCollector {
    private final Ui ui;
    private final CardsList inventory;

    public CardCollector() {
        ui = new Ui();
        inventory = new CardsList();
    }

    public void run() {
        ui.printWelcome();
        boolean isRunning = true;

        while (isRunning) {
            String input = ui.readInput();
            String[] parts = input.split(" ", 2);
            String commandString = parts[0].toLowerCase();

            switch (commandString) {
            case "add":
                if (parts.length < 2) {
                    System.out.println("Usage: add /n [name] /q [quantity] /p [price]");
                    System.out.println("Missing details for add.");
                    break;
                }
                handleAdd(parts[1]);
                break;

            case "find":
                if (parts.length < 2) {
                    System.out.println("Usage: find [/n NAME] [/p PRICE] [/q QUANTITY]");
                    System.out.println("At least one field must be provided.");
                    break;
                }
                handleFind(parts[1]);
                break;

            case "remove":
                if (parts.length < 2) {
                    System.out.println("Missing index for remove.");
                    break;
                }
                handleRemove(parts[1]);
                break;

            case "list":
                assert inventory != null : "Inventory should be initialised before listing";
                int sizeBeforeListing = inventory.getSize();
                ui.printList(inventory);
                assert inventory.getSize() == sizeBeforeListing
                        : "Listing inventory should not modify its size";
                break;

            case "history":
                if (parts.length < 2) {
                    System.out.println("Usage: history [added | modified | removed] [NUMBER | all]");
                    System.out.println("Example: history added");
                    System.out.println("The argument must be provided.");
                    break;
                }
                Command command = handleHistory(parts[1]);
                if (command != null) {
                    command.execute(ui, inventory);
                }
                break;

            case "bye":
                ui.printExit();
                isRunning = false;
                break;

            default:
                System.out.println("Unknown command!");
            }
        }
    }

    private void handleAdd(String arguments) {
        String name = arguments.split("/n")[1].split("/q|/p")[0].trim();
        int quantity = Integer.parseInt(arguments.split("/q")[1].split("/n|/p")[0].trim());
        float price = Float.parseFloat(arguments.split("/p")[1].split("/n|/q")[0].trim());

        assert !name.isEmpty() : "Card name should not be empty";
        assert quantity >= 0 : "Card quantity should not be negative";
        assert price >= 0.0f : "Card price should not be negative";

        Card newCard = new Card.Builder()
                .name(name)
                .price(price)
                .quantity(quantity)
                .build();

        int sizeBefore = inventory.getSize();
        inventory.addCard(newCard);
        assert inventory.getSize() == sizeBefore + 1 : "Inventory size should increase by 1 after adding";

        ui.printAdded(inventory);
    }

    private void handleFind(String arguments) {
        // Precondition: Arguments passed from the main loop should never be null
        assert arguments != null : "Arguments string for find command should not be null";

        String name = null;
        Float price = null;
        Integer quantity = null;

        try {
            if (arguments.contains("/n")) {
                name = arguments.split("/n")[1].split("/q|/p")[0].trim();
            }
            if (arguments.contains("/q")) {
                quantity = Integer.parseInt(arguments.split("/q")[1].split("/n|/p")[0].trim());
            }
            if (arguments.contains("/p")) {
                price = Float.parseFloat(arguments.split("/p")[1].split("/n|/q")[0].trim());
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format for price or quantity.");
            return;
        }

        if (name == null && price == null && quantity == null) {
            System.out.println("At least one search field (/n, /p, /q) must be provided.");
            return;
        }

        ArrayList<Card> results = inventory.findCards(name, price, quantity);

        // Postcondition: Results must be successfully returned (even if empty) before printing
        assert results != null : "Returned search results should not be null";

        ui.printFound(results);
    }

    private void handleRemove(String argument) {
        argument = argument.trim();
        assert inventory.getSize() >= 0 : "Inventory size cannot be negative";

        try {
            int index = Integer.parseInt(argument) - 1;
            int sizeBefore = inventory.getSize();

            if (index < 0 || index >= inventory.getSize()) {
                System.out.println("Invalid card index.");
                assert inventory.getSize() == sizeBefore;
                return;
            }

            inventory.removeCard(index);

            assert inventory.getSize() == sizeBefore - 1
                    : "Inventory size should decrease after removing by index";

            ui.printRemoved(inventory, index);

        } catch (NumberFormatException e) {
            int sizeBefore = inventory.getSize();

            boolean removed = inventory.removeCardByName(argument);

            if (removed) {
                assert inventory.getSize() == sizeBefore - 1
                        : "Inventory size should decrease after removing by name";

                System.out.println("Card \"" + argument + "\" removed successfully.");
                ui.printList(inventory);
            } else {
                assert inventory.getSize() == sizeBefore
                        : "Inventory size should remain unchanged";

                System.out.println("Card with name \"" + argument + "\" not found.");
            }
        }
    }

    /**
     * Handles the "history" command by displaying different types of inventory change history.
     * The format of the argument is [added | modified | removed] [NUMBER | all]
     * Argument matching is intentionally fuzzy and case-insensitive for fast usage
     * For example, input starting with "a" will
     * match "added", "m" will match "modified", and "r" will match "removed".
     *
     * @param arguments The command argument that determines which history type to display.
     */
    private Command handleHistory(String arguments) {
        String lowercaseArguments = arguments.trim().toLowerCase();
        String[] split = lowercaseArguments.split("\\s+", 2);  // Split by one or more spaces

        String historyType = split[0];

        int maxDisplayCount = -1;

        if (split.length > 1) {
            String maxDisplayCountString = split[1];
            try {
                int i = Integer.parseInt(maxDisplayCountString);

                if (i < 1) {
                    System.out.println("History count must be at least 1.");
                    return null;
                }

                maxDisplayCount = i;
            } catch (NumberFormatException e) {
                if ("all".startsWith(maxDisplayCountString)) {
                    maxDisplayCount = Integer.MAX_VALUE;
                }
            }
        }

        if (CardHistoryType.ADDED.getName().startsWith(historyType)) {
            return new HistoryCommand(CardHistoryType.ADDED, maxDisplayCount);
        } else if (CardHistoryType.MODIFIED.getName().startsWith(historyType)) {
            return new HistoryCommand(CardHistoryType.MODIFIED, maxDisplayCount);
        } else if (CardHistoryType.REMOVED.getName().startsWith(historyType)) {
            return new HistoryCommand(CardHistoryType.REMOVED, maxDisplayCount);
        } else {
            System.out.println("Unknown argument!");
            return null;
        }
    }

    public static void main(String[] args) {
        new CardCollector().run();
    }
}
