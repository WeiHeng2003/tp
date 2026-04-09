package seedu.cardcollector.command;

import java.io.IOException;
import java.nio.file.Path;

import seedu.cardcollector.AppState;
import seedu.cardcollector.Storage;

public class UploadCommand extends Command {
    private final Path sourcePath;

    public UploadCommand(Path sourcePath) {
        this.sourcePath = sourcePath;
    }

    @Override
    public CommandResult execute(CommandContext context) {
        if (!context.getUi().confirmUpload(sourcePath, context.getStorage().getFilePath())) {
            context.getUi().printUploadCancelled();
            return new CommandResult(false, false);
        }

        try {
            Storage importStorage = new Storage(sourcePath);
            AppState importedState = importStorage.load();
            context.getUploadUndoState().saveBackup(new AppState(context.getInventory(), context.getWishlist()));
            context.getInventory().replaceWith(importedState.getInventory());
            context.getWishlist().replaceWith(importedState.getWishlist());
            context.getCommandHistory().clear();
            context.getUi().printUploadSuccess(sourcePath, context.getStorage().getFilePath());
        } catch (IOException e) {
            context.getUi().printStorageTransferError("upload", sourcePath, e.getMessage());
        }
        return new CommandResult(false);
    }
}
