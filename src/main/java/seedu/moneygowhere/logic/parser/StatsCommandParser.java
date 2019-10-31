package seedu.moneygowhere.logic.parser;

import static seedu.moneygowhere.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.moneygowhere.logic.parser.CliSyntax.PREFIX_DATE;

import java.util.List;
import java.util.stream.Stream;

import seedu.moneygowhere.logic.commands.StatsCommand;
import seedu.moneygowhere.logic.parser.exceptions.ParseException;
import seedu.moneygowhere.model.spending.Date;

/**
 * Parses input arguments and creates a new StatsCommand object
 */
public class StatsCommandParser implements Parser<StatsCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the StatsCommand
     * and returns a StatsCommand object
     * for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public StatsCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_DATE);

        if (arePrefixesPresent(argMultimap, PREFIX_DATE)) {
            List<Date> datesList = ParserUtil.parseDates(argMultimap.getAllValues(PREFIX_DATE));

            //Throw ParseException if number of dates provided is not equal to 2.
            if (datesList.size() != 2) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    StatsCommand.MESSAGE_INVALID_DATERANGE));
            }

            Date startDate = datesList.get(0);
            Date endDate = datesList.get(1);

            //Throw ParseException if startDate is later than endDate.
            if (startDate.compareTo(endDate) > 0) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    StatsCommand.MESSAGE_INVALID_DATERANGE));
            }

            return new StatsCommand(startDate, endDate);
        }

        return new StatsCommand();
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given {@code
     * ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
