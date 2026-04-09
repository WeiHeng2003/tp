package seedu.cardcollector.command;

import seedu.cardcollector.AppState;

public class UndoUploadCommand extends Command {
    @Override
    public CommandResult execute(CommandContext context) {
        if (!context.getUploadUndoState().hasBackup()) {
            context.getUi().printNoUploadUndoAvailable();
            return new CommandResult(false, false);
        }

        AppState backupState = context.getUploadUndoState().getBackup();
        context.getInventory().replaceWith(backupState.getInventory());
        context.getWishlist().replaceWith(backupState.getWishlist());
        context.getUploadUndoState().clear();
        context.getCommandHistory().clear();
        context.getUi().printUndoUploadSuccess(context.getStorage().getFilePath());
        return new CommandResult(false);
    }
}
