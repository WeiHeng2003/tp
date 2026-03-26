package seedu.cardcollector.command;

import seedu.cardcollector.CardsList;
import seedu.cardcollector.Ui;

public class CompareCommand extends Command {

    private final int index1;
    private final int index2;

    public CompareCommand(int index1, int index2) {
        this.index1 = index1;
        this.index2 = index2;
    }

    @Override
    public CommandResult execute(Ui ui, CardsList cardsList) {
        if (index1 < 0 || index1 >= cardsList.getSize() ||
                index2 < 0 || index2 >= cardsList.getSize() ||
                index1 == index2) {
            ui.printInvalidIndex();
            return new CommandResult(false);
        }

        ui.printCompared(cardsList, index1, index2);
        return new CommandResult(false);
    }
}
