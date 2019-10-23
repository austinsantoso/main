package seedu.moneygowhere.logic.parser;

import static seedu.moneygowhere.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.moneygowhere.logic.parser.CliSyntax.PREFIX_DATE;

import java.util.List;
import java.util.stream.Stream;

import seedu.moneygowhere.logic.commands.GraphCommand;
import seedu.moneygowhere.logic.parser.exceptions.ParseException;
import seedu.moneygowhere.model.spending.Date;

/**
 * Parses input arguments and creates a new GraphCommand object
 */
public class GraphCommandParser implements Parser<GraphCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the GraphCommand
     * and returns a GraphCommand object
     * for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public GraphCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_DATE);

        if (arePrefixesPresent(argMultimap, PREFIX_DATE)) {
            List<Date> datesList = ParserUtil.parseDates(argMultimap.getAllValues(PREFIX_DATE));

            if (datesList.size() != 2) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, GraphCommand.MESSAGE_USAGE));
            }

            Date startDate = datesList.get(0);
            Date endDate = datesList.get(1);

            if (startDate.value.compareTo(endDate.value) > 0) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, GraphCommand.MESSAGE_USAGE));
            }

            return new GraphCommand(startDate, endDate);
        }

        return new GraphCommand();
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given {@code
     * ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
