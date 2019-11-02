package seedu.moneygowhere.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.moneygowhere.logic.parser.CliSyntax.PREFIX_PATH;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import javafx.util.Pair;
import seedu.moneygowhere.logic.commands.exceptions.CommandException;
import seedu.moneygowhere.logic.parser.ParserUtil;
import seedu.moneygowhere.logic.parser.exceptions.ParseException;
import seedu.moneygowhere.model.Model;
import seedu.moneygowhere.model.path.FilePath;
import seedu.moneygowhere.model.spending.Cost;
import seedu.moneygowhere.model.spending.Date;
import seedu.moneygowhere.model.spending.Name;
import seedu.moneygowhere.model.spending.Remark;
import seedu.moneygowhere.model.spending.Spending;
import seedu.moneygowhere.model.tag.Tag;

/**
 * Imports all spending from a new filepath.
 */
public class ImportCommand extends Command {

    public static final String COMMAND_WORD = "import";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Imports all spending from the file to the spending list. "
            + "Parameters: "
            + PREFIX_PATH + "FILEPATH\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PATH + "C:\\Users\\User\\Documents\\importfile.csv";

    public static final String MESSAGE_SUCCESS = "Imported all spending. \nAdded %s spending.";
    public static final String MESSAGE_SUCCESS_WITH_ERRORS = "Imported %s spending. "
            + "\nThe following line has errors and were not imported:\n%s";

    private final FilePath fullFilePath;

    public ImportCommand(FilePath filePath) {
        requireNonNull(filePath);
        fullFilePath = filePath;
    }

    /**
     * Converts a map of objects into a Spending object.
     * @param map A map of objects
     * @return Spending A Spending object
     * @throws ParseException If the input cannot be parsed
     */
    private static Spending createSpending(Map<String, String> map) throws ParseException {
        String metaName = map.get("name"); // Name
        String metaCost = map.get("cost"); // Cost
        String metaDate = map.get("date"); // Date
        String metaRemarks = map.get("remark"); // Remarks
        String metaTags = map.get("tagged"); // Tags

        String[] tags = metaTags.split(";|,");

        Name name = ParserUtil.parseName(metaName);
        Date date = ParserUtil.parseDate(metaDate);
        Remark remark = ParserUtil.parseRemark(metaRemarks);
        Cost cost = ParserUtil.parseCost(metaCost);
        Set<Tag> tagList = ParserUtil.parseTags(Arrays.asList(tags));

        return new Spending(name, date, remark, cost, tagList);

    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<String> errors;
        int count = 0;
        try {
            Pair<List<Spending>, List<String>> csvData = readSpendingFromCsv(fullFilePath.fullPath);
            List<Spending> spendings = csvData.getKey();
            errors = csvData.getValue();
            for (int i = 0; i < spendings.size(); i++) {
                Spending spending = spendings.get(i);
                model.addSpending(spending);
            }
            count = spendings.size();
        } catch (IOException ex) {
            throw new CommandException(ex.getMessage());
        }
        if (errors.size() != 0) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < errors.size(); i++) {
                sb.append(errors.get(i));
            }
            String result = String.format(MESSAGE_SUCCESS_WITH_ERRORS, count, sb.toString());
            return new CommandResult(result);
        } else {
            return new CommandResult(String.format(MESSAGE_SUCCESS, count));
        }

    }

    /**
     * Reads a csv file from the specified file path.
     * @param path A string representing a file path
     * @return {@code Pair} a pair of lists containing valid spending and errors, respectively.
     * @throws ParseException If the input cannot be parsed
     */
    private Pair<List<Spending>, List<String>> readSpendingFromCsv(String path) throws IOException {
        List<Spending> spendings = new ArrayList<>();
        List<String> errors = new ArrayList<>();
        int count = 0;
        File csvFile = new File(path);
        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = CsvSchema.emptySchema()
                .withHeader(); // use first row as header; otherwise defaults are fine
        MappingIterator<Map<String, String>> it = mapper.readerFor(Map.class)
                .with(schema)
                .readValues(csvFile);
        while (it.hasNext()) {
            count++;
            Map<String, String> rowAsMap = it.next();
            if (rowAsMap.size() == 5) {
                try {
                    Spending spending = createSpending(rowAsMap);
                    spendings.add(spending);
                } catch (ParseException p) {
                    errors.add("Row " + count + ": " + p.getMessage() + "\n");
                }
            }
        }

        return new Pair<>(spendings, errors);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ImportCommand // instanceof handles nulls
                && fullFilePath.equals(((ImportCommand) other).fullFilePath));
    }
}
