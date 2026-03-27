package seedu.cardcollector.command;

public class EditCommand extends Command {

    private final int targetIndex;
    private final String newName;
    private final Integer newQuantity;
    private final Float newPrice;

    public EditCommand(int targetIndex, String newName, Integer newQuantity, Float newPrice) {
        this.targetIndex = targetIndex;
        this.newName = newName;
        this.newQuantity = newQuantity;
        this.newPrice = newPrice;
    }

    @Override
    public CommandResult execute(CommandContext context) {
        var ui = context.getUi();
        var inventory = context.getTargetList();
        if (targetIndex < 0 || targetIndex >= inventory.getSize()) {
            ui.printInvalidIndex();
            return new CommandResult(false, false);
        }

        inventory.editCard(targetIndex, newName, newQuantity, newPrice);
        ui.printEdited(inventory, targetIndex);
        return new CommandResult(false, true);
    }
}
