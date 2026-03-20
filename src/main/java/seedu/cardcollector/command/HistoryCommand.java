package seedu.cardcollector.command;

import seedu.cardcollector.CardHistoryType;
import seedu.cardcollector.CardsList;
import seedu.cardcollector.Ui;

public class HistoryCommand extends Command {
    private final CardHistoryType historyType;
    private final int maxDisplayCount;

    public HistoryCommand(CardHistoryType historyType, int maxDisplayCount) {
        this.historyType = historyType;
        this.maxDisplayCount = maxDisplayCount;
    }

    @Override
    public void execute(Ui ui, CardsList inventory) {
        switch (historyType) {
        case ADDED -> ui.printAddedHistory(inventory, maxDisplayCount);
        case MODIFIED -> ui.printModifiedHistory(inventory, maxDisplayCount);
        case REMOVED -> ui.printRemovedHistory(inventory, maxDisplayCount);
        default -> {
            assert false : "Invalid history type";
        }
        }
    }
}
