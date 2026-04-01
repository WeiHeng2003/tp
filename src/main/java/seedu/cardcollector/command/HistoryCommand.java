package seedu.cardcollector.command;

import seedu.cardcollector.card.CardHistoryType;

import java.util.logging.Logger;

public class HistoryCommand extends Command {
    private static final Logger LOGGER = Logger.getLogger(HistoryCommand.class.getName());

    private final CardHistoryType historyType;
    private final int maxDisplayCount;
    private final boolean isDescending;

    public HistoryCommand(CardHistoryType historyType, int maxDisplayCount, boolean isDescending) {
        this.historyType = historyType;
        this.maxDisplayCount = maxDisplayCount;
        this.isDescending = isDescending;
    }

    @Override
    public CommandResult execute(CommandContext context) {
        var ui = context.getUi();
        var inventory = context.getTargetList();
        var history = inventory.getHistory();

        LOGGER.fine("Executing history command");
        ui.printHistory(history, historyType, maxDisplayCount, isDescending);

        return new CommandResult(false);
    }
}
