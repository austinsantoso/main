package seedu.moneygowhere.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.moneygowhere.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.moneygowhere.logic.parser.CliSyntax.PREFIX_COST;
import static seedu.moneygowhere.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.moneygowhere.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.moneygowhere.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.moneygowhere.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import seedu.moneygowhere.logic.commands.FindCommand;
import seedu.moneygowhere.logic.parser.exceptions.ParseException;
import seedu.moneygowhere.model.spending.Cost;
import seedu.moneygowhere.model.spending.CostInRangePredicate;
import seedu.moneygowhere.model.spending.Date;
import seedu.moneygowhere.model.spending.DateInRangePredicate;
import seedu.moneygowhere.model.spending.Name;
import seedu.moneygowhere.model.spending.NameContainsKeywordsPredicate;
import seedu.moneygowhere.model.spending.Remark;
import seedu.moneygowhere.model.spending.RemarkContainsKeywordsPredicate;
import seedu.moneygowhere.model.spending.Spending;
import seedu.moneygowhere.model.tag.Tag;
import seedu.moneygowhere.model.tag.TagPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {
    public static final String DATE_RANGE_MESSAGE_CONSTRAINTS = "You must enter two Date values. "
            + "Valid values are: today, yesterday, tomorrow or a formal date: DD/MM/YYYY, DD-MM-YYYY or YYYY-MM-DD.";

    public static final String COST_RANGE_MESSAGE_CONSTRAINTS = "You must enter two Cost values and "
            + "the first value cannot exceed the second value."
            + "Cost must be a number with at most 2 decimal places, and it should not be blank.";

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_DATE, PREFIX_COST, PREFIX_REMARK, PREFIX_TAG);

        List<Predicate<Spending>> predicates = new ArrayList<>();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            String name = argMultimap.getValue(PREFIX_NAME).get().trim();

            if (name.isEmpty()) {
                throw new ParseException(Name.MESSAGE_CONSTRAINTS);
            }

            String[] nameKeywords = name.split("\\s+");
            predicates.add(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
        }
        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            List<Date> dates = ParserUtil.parseDates(argMultimap.getAllValues(PREFIX_DATE));

            if (dates.size() < 2) {
                throw new ParseException(DATE_RANGE_MESSAGE_CONSTRAINTS);
            }
            predicates.add(new DateInRangePredicate(dates.get(0), dates.get(1)));
        }
        if (argMultimap.getValue(PREFIX_COST).isPresent()) {
            List<Cost> costs = ParserUtil.parseCosts(argMultimap.getAllValues(PREFIX_COST));

            if (costs.size() != 2) {
                throw new ParseException(COST_RANGE_MESSAGE_CONSTRAINTS);
            }

            double min = Double.parseDouble(costs.get(0).value);
            double max = Double.parseDouble(costs.get(1).value);

            if (min > max) {
                throw new ParseException(COST_RANGE_MESSAGE_CONSTRAINTS);
            }

            predicates.add(new CostInRangePredicate(costs.get(0), costs.get(1)));
        }
        if (argMultimap.getValue(PREFIX_REMARK).isPresent()) {
            String remark = argMultimap.getValue(PREFIX_REMARK).get().trim();

            if (remark.isEmpty()) {
                throw new ParseException(Remark.MESSAGE_CONSTRAINTS);
            }

            String[] remarkKeywords = remark.split("\\s+");
            predicates.add(new RemarkContainsKeywordsPredicate(Arrays.asList(remarkKeywords)));
        }
        if (argMultimap.getValue(PREFIX_TAG).isPresent()) {
            List<String> tags = argMultimap.getAllValues(PREFIX_TAG);
            tags = tags.stream().map(String::trim).collect(Collectors.toList());

            if (tags.isEmpty() || tags.stream().anyMatch(String::isEmpty)) {
                throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
            }

            predicates.add(new TagPredicate(new HashSet<>(tags)));
        }

        if (predicates.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        return new FindCommand(predicates);
    }
}
