package seedu.moneygowhere.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.moneygowhere.commons.core.GuiSettings;
import seedu.moneygowhere.commons.core.LogsCenter;
import seedu.moneygowhere.logic.commands.Command;
import seedu.moneygowhere.logic.commands.CommandResult;
import seedu.moneygowhere.logic.commands.exceptions.CommandException;
import seedu.moneygowhere.logic.parser.SpendingBookParser;
import seedu.moneygowhere.logic.parser.exceptions.ParseException;
import seedu.moneygowhere.model.Model;
import seedu.moneygowhere.model.ReadOnlySpendingBook;
import seedu.moneygowhere.model.spending.Date;
import seedu.moneygowhere.model.spending.Spending;
import seedu.moneygowhere.model.tag.Tag;
import seedu.moneygowhere.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final SpendingBookParser spendingBookParser;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        spendingBookParser = new SpendingBookParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = spendingBookParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.saveSpendingBook(model.getSpendingBook());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public Map<Date, Double> getGraphData(String commandText) throws ParseException {
        Command command = spendingBookParser.parseCommand(commandText);
        return command.getGraphData(model);
    }

    @Override
    public Map<Tag, Double> getStatsData(String commandText) throws ParseException {
        Command command = spendingBookParser.parseCommand(commandText);
        return command.getStatsData(model);
    }

    @Override
    public String getStatsMessage(String commandText) throws ParseException {
        Command command = spendingBookParser.parseCommand(commandText);
        return command.getStatsMessage(model);
    }

    @Override
    public ReadOnlySpendingBook getSpendingBook() {
        return model.getSpendingBook();
    }

    @Override
    public ObservableList<Spending> getFilteredSpendingList() {
        return model.getFilteredSpendingList();
    }

    @Override
    public Path getSpendingBookFilePath() {
        return model.getSpendingBookFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }

}
