package seedu.cardcollector.command;

import seedu.cardcollector.card.Card;

import java.util.ArrayList;

public class FindCommand extends Command {
    private final String name;
    private final Integer quantity;
    private final Float price;
    private final String cardSet;
    private final String rarity;
    private final String condition;
    private final String language;
    private final String cardNumber;
    private final String note;
    private final String tag;

    public FindCommand(String name, Integer quantity, Float price,
                       String cardSet, String rarity, String condition,
                       String language, String cardNumber, String note, String tag) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.cardSet = cardSet;
        this.rarity = rarity;
        this.condition = condition;
        this.language = language;
        this.cardNumber = cardNumber;
        this.note = note;
        this.tag = tag;
    }

    @Override
    public CommandResult execute(CommandContext context) {
        var ui = context.getUi();
        var inventory = context.getTargetList();
        ArrayList<Card> results = inventory.findCards(
                name, quantity, price, cardSet, rarity, condition,
                language, cardNumber, note, tag);
        ui.printFound(results);
        return new CommandResult(false);
    }
}