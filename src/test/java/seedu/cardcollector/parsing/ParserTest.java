package seedu.cardcollector.parsing;

import org.junit.jupiter.api.Test;
import seedu.cardcollector.command.Command;
import seedu.cardcollector.command.DownloadCommand;
import seedu.cardcollector.command.HistoryCommand;
import seedu.cardcollector.command.UndoUploadCommand;
import seedu.cardcollector.command.UploadCommand;
import seedu.cardcollector.exception.ParseBlankCommandException;
import seedu.cardcollector.exception.ParseInvalidArgumentException;
import seedu.cardcollector.exception.ParseUnknownCommandException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class ParserTest {
    //@@author HX2003
    @Test
    public void parse_unknownCommand_exceptionThrown() {
        Parser parser = new Parser();

        assertThrows(
                ParseBlankCommandException.class,
                () -> parser.parse(" ")
        );

        assertThrows(
                ParseBlankCommandException.class,
                () -> parser.parse("     ")
        );

        assertThrows(
                ParseUnknownCommandException.class,
                () -> parser.parse("powwow")
        );

        assertThrows(
                ParseUnknownCommandException.class,
                () -> parser.parse("delicious flower")
        );
    }

    @Test
    public void parse_invalidArgumentHistory_exceptionThrown() {
        Parser parser = new Parser();

        assertThrows(
                ParseInvalidArgumentException.class,
                () -> parser.parse("history")
        );

        assertThrows(
                ParseInvalidArgumentException.class,
                () -> parser.parse("history delicious")
        );

        assertThrows(
                ParseInvalidArgumentException.class,
                () -> parser.parse("history added -1")
        );

        assertThrows(
                ParseInvalidArgumentException.class,
                () -> parser.parse("history added 3t35t45")
        );
    }


    @Test
    public void parse_historyCommand_success() throws
            ParseBlankCommandException,
            ParseUnknownCommandException,
            ParseInvalidArgumentException {
        Parser parser = new Parser();

        Command command1 = parser.parse("history added");
        assertInstanceOf(HistoryCommand.class, command1);

        Command command2 = parser.parse("history modified");
        assertInstanceOf(HistoryCommand.class, command2);

        Command command3 = parser.parse("history removed");
        assertInstanceOf(HistoryCommand.class, command3);

        Command command4 = parser.parse("history removed all");
        assertInstanceOf(HistoryCommand.class, command4);

        Command command5 = parser.parse("history modified 5");
        assertInstanceOf(HistoryCommand.class, command5);
    }

    @Test
    public void parse_transferCommands_success() throws ParseBlankCommandException,
            ParseUnknownCommandException, ParseInvalidArgumentException {
        Parser parser = new Parser();

        Command downloadCommand = parser.parse("download /f backups/cards.txt");
        assertInstanceOf(DownloadCommand.class, downloadCommand);

        Command uploadCommand = parser.parse("upload /f backups/cards.txt");
        assertInstanceOf(UploadCommand.class, uploadCommand);

        Command undoUploadCommand = parser.parse("undoupload");
        assertInstanceOf(UndoUploadCommand.class, undoUploadCommand);
    }

    @Test
    public void parse_transferInvalidPath() {
        Parser parser = new Parser();

        assertThrows(
                ParseInvalidArgumentException.class,
                () -> parser.parse("download")
        );

        assertThrows(
                ParseInvalidArgumentException.class,
                () -> parser.parse("upload /f")
        );

        assertThrows(
                ParseInvalidArgumentException.class,
                () -> parser.parse("undoupload now")
        );
    }

    //@@author
}
