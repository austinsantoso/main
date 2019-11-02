package seedu.moneygowhere.logic.parser;

import static seedu.moneygowhere.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.moneygowhere.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.moneygowhere.logic.commands.AddCommand;
import seedu.moneygowhere.logic.commands.BudgetCommand;
import seedu.moneygowhere.logic.commands.ClearCommand;
import seedu.moneygowhere.logic.commands.Command;
import seedu.moneygowhere.logic.commands.CurrencyCommand;
import seedu.moneygowhere.logic.commands.DeleteCommand;
import seedu.moneygowhere.logic.commands.EditCommand;
import seedu.moneygowhere.logic.commands.ExchangeRateCommand;
import seedu.moneygowhere.logic.commands.ExitCommand;
import seedu.moneygowhere.logic.commands.ExportCommand;
import seedu.moneygowhere.logic.commands.FindCommand;
import seedu.moneygowhere.logic.commands.GraphCommand;
import seedu.moneygowhere.logic.commands.HelpCommand;
import seedu.moneygowhere.logic.commands.ImportCommand;
import seedu.moneygowhere.logic.commands.ListCommand;
import seedu.moneygowhere.logic.commands.ReminderCommand;
import seedu.moneygowhere.logic.commands.ShowBudgetCommand;
import seedu.moneygowhere.logic.commands.SortCommand;
import seedu.moneygowhere.logic.commands.StatsCommand;

import seedu.moneygowhere.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class SpendingBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case SortCommand.COMMAND_WORD:
            return new SortCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case ExchangeRateCommand.COMMAND_WORD:
            return new ExchangeRateCommandParser().parse(arguments);

        case CurrencyCommand.COMMAND_WORD:
            return new CurrencyCommandParser().parse(arguments);

        case StatsCommand.COMMAND_WORD:
            return new StatsCommandParser().parse(arguments);

        case GraphCommand.COMMAND_WORD:
            return new GraphCommandParser().parse(arguments);

        case ImportCommand.COMMAND_WORD:
            return new ImportCommandParser().parse(arguments);

        case ExportCommand.COMMAND_WORD:
            return new ExportCommandParser().parse(arguments);

        case BudgetCommand.COMMAND_WORD:
            return new BudgetCommandParser().parse(arguments);

        case ShowBudgetCommand.COMMAND_WORD:
            return new ShowBudgetCommand();

        case ReminderCommand.COMMAND_WORD:
            return new ReminderCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
