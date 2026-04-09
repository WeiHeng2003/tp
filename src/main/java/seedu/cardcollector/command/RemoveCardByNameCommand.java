package seedu.cardcollector.command;

import seedu.cardcollector.card.Card;

public class RemoveCardByNameCommand extends Command {
    private final String targetName;
    private Card removedCard;
    private int removedIndex;

    public RemoveCardByNameCommand(String targetName) {
        this.targetName = targetName;
    }

    @Override
    public CommandResult execute(CommandContext context) {
        var ui = context.getUi();
        var inventory = context.getTargetList();

        for (int i = 0; i < inventory.getSize(); i++) {
            if (inventory.getCard(i).getName().equalsIgnoreCase(targetName)) {
                this.removedCard = inventory.getCard(i).copy();
                this.removedIndex = i;
                break;
            }
        }

        boolean removed = inventory.removeCardByName(targetName);

        if (removed) {
            this.isReversible = true;
            ui.printRemoveByNameSuccess(targetName, inventory);
            return new CommandResult(false);
        } else {
            ui.printCardNotFound(targetName);
            return new CommandResult(false,false);
        }
    }

    @Override
    public CommandResult undo(CommandContext context) {
        if (removedCard != null) {
            context.getTargetList().addCardAtIndex(removedIndex, removedCard);
            context.getUi().printUndoSuccess(context.getTargetList());
        }
        return new CommandResult(false);
    }
}
