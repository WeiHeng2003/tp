package seedu.cardcollector.command;

public class DuplicatesCommand extends Command {

    @Override
    public CommandResult execute(CommandContext context) {
        var ui = context.getUi();
        var inventory = context.getTargetList();

        ui.printFound(inventory.getDuplicateCards());
        return new CommandResult(false);
    }
}