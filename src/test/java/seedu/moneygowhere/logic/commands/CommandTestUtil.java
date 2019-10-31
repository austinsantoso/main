package seedu.moneygowhere.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.moneygowhere.logic.parser.CliSyntax.PREFIX_COST;
import static seedu.moneygowhere.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.moneygowhere.logic.parser.CliSyntax.PREFIX_MESSAGE;
import static seedu.moneygowhere.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.moneygowhere.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.moneygowhere.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.moneygowhere.testutil.Assert.assertThrows;
import static seedu.moneygowhere.testutil.TypicalSpendings.getTypicalSpendingBook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.moneygowhere.commons.core.index.Index;
import seedu.moneygowhere.logic.commands.EditCommand.EditSpendingDescriptor;
import seedu.moneygowhere.logic.commands.exceptions.CommandException;
import seedu.moneygowhere.model.Model;
import seedu.moneygowhere.model.ModelManager;
import seedu.moneygowhere.model.SpendingBook;
import seedu.moneygowhere.model.UserPrefs;
import seedu.moneygowhere.model.spending.NameContainsKeywordsPredicate;
import seedu.moneygowhere.model.spending.Spending;
import seedu.moneygowhere.testutil.EditSpendingDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_DATE = "25/12/2019";
    public static final String VALID_DATE_AMY = "01/01/2019";
    public static final String VALID_DATE_BOB = "02/01/2019";
    public static final String VALID_REMARK_AMY = "Likes to watch movies";
    public static final String VALID_REMARK_BOB = "Loves to play soccer";
    public static final String VALID_COST_AMY = "312";
    public static final String VALID_COST_BOB = "123";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";
    public static final String VALID_REMINDER_MESSAGE = "Pay Bill";
    public static final String VALID_REMINDER_COMMAND_WORD_ADD = "add";
    public static final String VALID_REMINDER_COMMAND_WORD_DELETE = "delete";
    public static final String INVALID_REMINDER_COMMAND_WORD = "bla bla";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String DATE_DESC_VALID = " " + PREFIX_DATE + VALID_DATE;
    public static final String DATE_DESC_AMY = " " + PREFIX_DATE + VALID_DATE_AMY;
    public static final String DATE_DESC_BOB = " " + PREFIX_DATE + VALID_DATE_BOB;
    public static final String REMARK_DESC_AMY = " " + PREFIX_REMARK + VALID_REMARK_AMY;
    public static final String REMARK_DESC_BOB = " " + PREFIX_REMARK + VALID_REMARK_BOB;
    public static final String COST_DESC_AMY = " " + PREFIX_COST + VALID_COST_AMY;
    public static final String COST_DESC_BOB = " " + PREFIX_COST + VALID_COST_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;
    public static final String REMINDER_MESSAGE_DESC_VALID = " " + PREFIX_MESSAGE + VALID_REMINDER_MESSAGE;
    public static final String COMMAND_WORD_DESC_ADD = " " + VALID_REMINDER_COMMAND_WORD_ADD;
    public static final String COMMAND_WORD_DESC_DELETE = " " + VALID_REMINDER_COMMAND_WORD_DELETE;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_DATE_DESC = " " + PREFIX_DATE + " "; // blank date
    public static final String INVALID_COST_DESC = " " + PREFIX_COST; // empty string not allowed for cost
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags
    public static final String INVALID_COMMAND_WORD_DESC = " " + INVALID_REMINDER_COMMAND_WORD;

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditSpendingDescriptor DESC_AMY;
    public static final EditSpendingDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditSpendingDescriptorBuilder().withName(VALID_NAME_AMY)
                .withDate(VALID_DATE_AMY).withRemark(VALID_REMARK_AMY).withCost(VALID_COST_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditSpendingDescriptorBuilder().withName(VALID_NAME_BOB)
                .withDate(VALID_DATE_BOB).withRemark(VALID_REMARK_BOB).withCost(VALID_COST_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the MoneyGoWhere list, filtered Spending list and selected Spending in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        SpendingBook expectedAddressBook = new SpendingBook(actualModel.getSpendingBook());
        List<Spending> expectedFilteredList = new ArrayList<>(actualModel.getFilteredSpendingList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getSpendingBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredSpendingList());
    }

    /**
     * Updates {@code model}'s filtered list to show only the Spending at the given {@code targetIndex}
     * in the {@code model}'s MoneyGoWhere list.
     */
    public static void showSpendingAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredSpendingList().size());

        Spending spending = model.getFilteredSpendingList().get(targetIndex.getZeroBased());
        final String[] splitName = spending.getName().fullName.split("\\s+");
        model.updateFilteredSpendingList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredSpendingList().size());
    }

    @Test
    public void getGraphData() {
        Model model = new ModelManager(getTypicalSpendingBook(), new UserPrefs());
        Command command = new ExitCommand();
        assertEquals(command.getGraphData(model), null);
    }

    @Test
    public void getStatsData() {
        Model model = new ModelManager(getTypicalSpendingBook(), new UserPrefs());
        Command command = new ExitCommand();
        assertEquals(command.getStatsData(model), null);
    }

    @Test
    public void getStatsMessage() {
        Model model = new ModelManager(getTypicalSpendingBook(), new UserPrefs());
        Command command = new ExitCommand();
        assertEquals(command.getStatsMessage(model), null);
    }

}
