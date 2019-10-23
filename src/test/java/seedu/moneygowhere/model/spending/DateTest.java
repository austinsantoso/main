package seedu.moneygowhere.model.spending;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.moneygowhere.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class DateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Date(null));
    }

    @Test
    public void constructor_invalidDate_throwsIllegalArgumentException() {
        String invalidDate = " ";
        assertThrows(IllegalArgumentException.class, () -> new Date(invalidDate));
    }

    @Test
    public void isValidDate() {
        // null date number
        assertThrows(NullPointerException.class, () -> Date.isValidDate(null));

        // invalid dates
        assertFalse(Date.isValidDate("")); // empty string
        assertFalse(Date.isValidDate(" ")); // spaces only

        // valid dates
        assertTrue(Date.isValidDate("1/1/2019"));
        assertTrue(Date.isValidDate("25/12/2019"));
        assertTrue(Date.isValidDate("today"));
        assertTrue(Date.isValidDate("yesterday"));
    }

    @Test
    public void compareTo() {
        // Equal
        assertEquals(0, new Date("1/1/2019").compareTo(new Date("1/1/2019")));

        // Less than
        assertEquals(-1, new Date("1/1/2019").compareTo(new Date("2/1/2019")));

        // Greater than
        assertEquals(1, new Date("2/1/2019").compareTo(new Date("1/1/2019")));
        assertEquals(1, new Date("12/1/2019").compareTo(new Date("1/12/2018")));
    }
}
