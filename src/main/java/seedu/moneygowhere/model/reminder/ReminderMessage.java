package seedu.moneygowhere.model.reminder;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Reminder's message in the Reminder list. Guarantees: immutable; is always valid
 */
public class ReminderMessage implements Comparable<ReminderMessage> {

    public final String value;

    public ReminderMessage(String message) {
        requireNonNull(message);
        value = message;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReminderMessage // instanceof handles nulls
                && value.equals(((ReminderMessage) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public int compareTo(ReminderMessage o) {
        return value.compareTo(o.value);
    }
}
