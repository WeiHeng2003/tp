package seedu.cardcollector;

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
            String command = parts[0].toLowerCase();

            switch (command) {
            case "add":
                if (parts.length < 2) {
                    System.out.println("Usage: add /n [name] /q [quantity] /p [price]");
                    break;
                }
                handleAdd(parts[1]);
                break;

            case "find":
                if (parts.length < 2) {
                    System.out.println("Usage: find [/n NAME] [/p PRICE] [/q QUANTITY]");
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
                ui.printList(inventory);
                break;

            case "history":
                if (parts.length < 2) {
                    System.out.println("Usage: history [added|modified|removed]");
                    break;
                }
                handleHistory(parts[1]);
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

        Card newCard = new Card(name, quantity, price);
        inventory.addCard(newCard);
        ui.printAdded(inventory);
    }

    private void handleFind(String arguments) {
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
            System.out.println("Invalid number format.");
            return;
        }

        ArrayList<Card> results = inventory.findCards(name, price, quantity);
        ui.printFound(results);
    }

    private void handleRemove(String argument) {
        argument = argument.trim();

        assert inventory != null : "Inventory should be initialized";
        assert argument != null && !argument.isEmpty() : "Argument cannot be empty";

        try {
            int index = Integer.parseInt(argument) - 1;
            int sizeBefore = inventory.getSize();

            if (index < 0 || index >= inventory.getSize()) {
                System.out.println("Invalid card index.");
                assert inventory.getSize() == sizeBefore;
                return;
            }

            inventory.removeCard(index);
            assert inventory.getSize() == sizeBefore - 1;

            ui.printRemoved(inventory, index);

        } catch (NumberFormatException e) {
            int sizeBefore = inventory.getSize();

            boolean removed = inventory.removeCardByName(argument);

            if (removed) {
                assert inventory.getSize() == sizeBefore - 1;
                System.out.println("Card \"" + argument + "\" removed successfully.");
                ui.printList(inventory);
            } else {
                assert inventory.getSize() == sizeBefore;
                System.out.println("Card not found.");
            }
        }
    }

    private void handleHistory(String arguments) {
        assert arguments != null : "History argument cannot be null";

        int sizeBefore = inventory.getSize();
        int removedBefore = inventory.getRemovedSize();

        if ("added".startsWith(arguments)) {
            ui.printAddedHistory(inventory, false);
            assert inventory.getSize() == sizeBefore;

        } else if ("modified".startsWith(arguments)) {
            ui.printModifiedHistory(inventory, false);
            assert inventory.getSize() == sizeBefore;

        } else if ("removed".startsWith(arguments)) {
            ui.printRemovedHistory(inventory, false);
            assert inventory.getRemovedSize() == removedBefore;

        } else {
            System.out.println("Unknown argument!");
        }
    }

    public static void main(String[] args) {
        new CardCollector().run();
    }
}