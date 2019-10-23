package seedu.moneygowhere.logic.commands;

import java.util.Map;

import seedu.moneygowhere.logic.commands.exceptions.CommandException;
import seedu.moneygowhere.model.Model;
import seedu.moneygowhere.model.spending.Date;
import seedu.moneygowhere.model.tag.Tag;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public abstract CommandResult execute(Model model) throws CommandException;

    public Map<Date, Double> getGraphData(Model model) {
        return null;
    }

    public Map<Tag, Double> getStatsData(Model model) {
        return null;
    }

    public String getStatsMessage(Model model) {
        return null;
    }
}
