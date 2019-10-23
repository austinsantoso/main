package seedu.moneygowhere.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.moneygowhere.storage.JsonAdaptedSpending.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.moneygowhere.testutil.Assert.assertThrows;
import static seedu.moneygowhere.testutil.TypicalSpendings.BANANA;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.moneygowhere.commons.exceptions.IllegalValueException;
import seedu.moneygowhere.model.spending.Cost;
import seedu.moneygowhere.model.spending.Date;
import seedu.moneygowhere.model.spending.Name;
import seedu.moneygowhere.model.spending.Remark;

public class JsonAdaptedSpendingTest {

    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_DATE = " ";
    private static final String INVALID_COST = " ";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = BANANA.getName().toString();
    private static final String VALID_DATE = BANANA.getDate().toString();
    private static final String VALID_COST = BANANA.getCost().toString();
    private static final String VALID_REMARK = BANANA.getRemark().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = BANANA.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validSpendingDetails_returnsSpending() throws Exception {
        JsonAdaptedSpending spending = new JsonAdaptedSpending(BANANA);
        assertEquals(BANANA, spending.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedSpending spending =
                new JsonAdaptedSpending(INVALID_NAME, VALID_DATE, VALID_REMARK, VALID_COST, VALID_TAGS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, spending::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedSpending spending =
                new JsonAdaptedSpending(null, VALID_DATE, VALID_REMARK, VALID_COST, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, spending::toModelType);
    }

    @Test
    public void toModelType_invalidDate_throwsIllegalValueException() {
        JsonAdaptedSpending spending =
                new JsonAdaptedSpending(VALID_NAME, INVALID_DATE, VALID_REMARK, VALID_COST, VALID_TAGS);
        String expectedMessage = Date.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, spending::toModelType);
    }

    @Test
    public void toModelType_nullDate_throwsIllegalValueException() {
        JsonAdaptedSpending spending =
                new JsonAdaptedSpending(VALID_NAME, null, VALID_REMARK, VALID_COST, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, spending::toModelType);
    }

    @Test
    public void toModelType_nullRemark_throwsIllegalValueException() {
        JsonAdaptedSpending spending =
                new JsonAdaptedSpending(VALID_NAME, VALID_DATE, null, VALID_COST, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Remark.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, spending::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedSpending spending =
                new JsonAdaptedSpending(VALID_NAME, VALID_DATE, VALID_REMARK, INVALID_COST, VALID_TAGS);
        String expectedMessage = Cost.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, spending::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedSpending spending =
                new JsonAdaptedSpending(VALID_NAME, VALID_DATE, VALID_REMARK, null, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Cost.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, spending::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedSpending spending =
                new JsonAdaptedSpending(VALID_NAME, VALID_DATE, VALID_REMARK, VALID_COST, invalidTags);
        assertThrows(IllegalValueException.class, spending::toModelType);
    }

}
