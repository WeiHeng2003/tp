package seedu.cardcollector.command;

public class UndoCommand extends Command {

    @Override
    public CommandResult execute(CommandContext context) {
        var history = context.getCommandHistory();

        if (history.isEmpty()) {
            context.getUi().echo("Nothing to Undo.");
            return new CommandResult(false);
        }

        Command lastCommand = history.pop();
        return lastCommand.undo(context);
    }
}
