package seedu.moneygowhere.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.VALID_DATE_BOB;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.showSpendingAtIndex;
import static seedu.moneygowhere.testutil.TypicalIndexes.INDEX_FIRST_SPENDING;
import static seedu.moneygowhere.testutil.TypicalIndexes.INDEX_SECOND_SPENDING;
import static seedu.moneygowhere.testutil.TypicalSpendings.getTypicalSpendingBook;

import org.junit.jupiter.api.Test;

import seedu.moneygowhere.commons.core.Messages;
import seedu.moneygowhere.commons.core.index.Index;
import seedu.moneygowhere.logic.commands.EditCommand.EditSpendingDescriptor;
import seedu.moneygowhere.model.Model;
import seedu.moneygowhere.model.ModelManager;
import seedu.moneygowhere.model.SpendingBook;
import seedu.moneygowhere.model.UserPrefs;
import seedu.moneygowhere.model.spending.Spending;
import seedu.moneygowhere.testutil.EditSpendingDescriptorBuilder;
import seedu.moneygowhere.testutil.SpendingBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalSpendingBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Spending editedSpending = new SpendingBuilder().build();
        EditSpendingDescriptor descriptor = new EditSpendingDescriptorBuilder(editedSpending).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_SPENDING, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_SPENDING_SUCCESS, editedSpending);

        Model expectedModel = new ModelManager(new SpendingBook(model.getSpendingBook()), new UserPrefs());
        expectedModel.setSpending(model.getFilteredSpendingList().get(0), editedSpending);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastSpending = Index.fromOneBased(model.getFilteredSpendingList().size());
        Spending lastSpending = model.getFilteredSpendingList().get(indexLastSpending.getZeroBased());

        SpendingBuilder spendingInList = new SpendingBuilder(lastSpending);
        Spending editedSpending = spendingInList.withName(VALID_NAME_BOB).withDate(VALID_DATE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        EditSpendingDescriptor descriptor = new EditSpendingDescriptorBuilder().withName(VALID_NAME_BOB)
                .withDate(VALID_DATE_BOB).withTags(VALID_TAG_HUSBAND).build();
        EditCommand editCommand = new EditCommand(indexLastSpending, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_SPENDING_SUCCESS, editedSpending);

        Model expectedModel = new ModelManager(new SpendingBook(model.getSpendingBook()), new UserPrefs());
        expectedModel.setSpending(lastSpending, editedSpending);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_SPENDING, new EditSpendingDescriptor());
        Spending editedSpending = model.getFilteredSpendingList().get(INDEX_FIRST_SPENDING.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_SPENDING_SUCCESS, editedSpending);

        Model expectedModel = new ModelManager(new SpendingBook(model.getSpendingBook()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showSpendingAtIndex(model, INDEX_FIRST_SPENDING);

        Spending spendingInFilteredList = model.getFilteredSpendingList().get(INDEX_FIRST_SPENDING.getZeroBased());
        Spending editedSpending = new SpendingBuilder(spendingInFilteredList).withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_SPENDING,
                new EditSpendingDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_SPENDING_SUCCESS, editedSpending);

        Model expectedModel = new ModelManager(new SpendingBook(model.getSpendingBook()), new UserPrefs());
        expectedModel.setSpending(model.getFilteredSpendingList().get(0), editedSpending);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidSpendingIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredSpendingList().size() + 1);
        EditSpendingDescriptor descriptor = new EditSpendingDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_SPENDING_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of the MoneyGoWhere list
     */
    @Test
    public void execute_invalidSpendingIndexFilteredList_failure() {
        showSpendingAtIndex(model, INDEX_FIRST_SPENDING);
        Index outOfBoundIndex = INDEX_SECOND_SPENDING;
        // ensures that outOfBoundIndex is still in bounds of the MoneyGoWhere list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getSpendingBook().getSpendingList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditSpendingDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_SPENDING_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_SPENDING, DESC_AMY);

        // same values -> returns true
        EditSpendingDescriptor copyDescriptor = new EditSpendingDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_SPENDING, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_SPENDING, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_SPENDING, DESC_BOB)));
    }

}
