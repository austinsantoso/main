package seedu.moneygowhere.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javafx.collections.ObservableList;
import seedu.moneygowhere.model.budget.Budget;
import seedu.moneygowhere.model.reminder.Reminder;
import seedu.moneygowhere.model.spending.Spending;
import seedu.moneygowhere.model.spending.SpendingList;

/**
 * Wraps all data at the address-book level
 */
public class SpendingBook implements ReadOnlySpendingBook {

    private final SpendingList spendings;
    private final Budget budget;
    private List<Reminder> reminders;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        spendings = new SpendingList();
        budget = new Budget(0);
        reminders = new ArrayList<>();
    }

    public SpendingBook() {}

    /**
     * Creates a SpendingBook using Spending entries in the {@code toBeCopied}
     */
    public SpendingBook(ReadOnlySpendingBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the Spending list with {@code spendings}.
     * {@code spendings} must not contain duplicate spendings.
     */
    public void setSpendings(List<Spending> spendings) {
        this.spendings.setSpendings(spendings);
    }

    /**
     * Resets the existing data of this {@code SpendingBook} with {@code newData}.
     */
    public void resetData(ReadOnlySpendingBook newData) {
        requireNonNull(newData);

        setSpendings(newData.getSpendingList());
        setBudget(newData.getBudget());
        setReminders(newData.getReminderList());
    }

    //// Spending-level operations

    /**
     * Returns true if a Spending with the same identity as {@code Spending} exists in MoneyGoWhere.
     */
    public boolean hasSpending(Spending spending) {
        requireNonNull(spending);
        return spendings.contains(spending);
    }

    /**
     * Adds a Spending to the MoneyGoWhere list.
     * The Spending must not already exist in the MoneyGoWhere list.
     */
    public void addSpending(Spending p) {
        spendings.add(p);
    }

    /**
     * Replaces the given Spending {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the MoneyGoWhere list.
     * The Spending identity of {@code editedPerson} must not be the same as another existing
     * Spending in the MoneyGoWhere list.
     */
    public void setSpending(Spending target, Spending editedSpending) {
        requireNonNull(editedSpending);

        spendings.setSpending(target, editedSpending);
    }

    /**
     * Removes {@code key} from this {@code SpendingBook}.
     * {@code key} must exist in the MoneyGoWhere list.
     */
    public void removeSpending(Spending key) {
        spendings.remove(key);
    }

    //// Reminder-level operations

    /**
     * Replaces the contents of the Reminder list with {@code reminders}.
     */
    public void setReminders(List<Reminder> reminders) {
        this.reminders = reminders;
    }

    /**
     * Returns true if a Reminder with the same identity as {@code Reminder} exists in MoneyGoWhere.
     */
    public boolean hasReminder(Reminder reminder) {
        requireNonNull(reminder);
        return reminders.contains(reminder);
    }

    /**
     * Adds a Reminder to the reminder list.
     */
    public void addReminder(Reminder r) {
        reminders.add(r);
    }

    /**
     * Removes {@code key} from this {@code  Reminderlist}.
     * {@code key} must exist in the Reminder list.
     */
    public void removeReminder(Reminder key) {
        reminders.remove(key);
    }

    //// Budget related operations

    @Override
    public Budget getBudget() {
        return budget;
    }

    /**
     * Replaces the value of budget in the MoneyGoWhere list with {@code budget}.
     */
    public void setBudget(Budget budget) {
        this.budget.setValue(budget.getValue());
    }

    //// util methods

    @Override
    public String toString() {
        return spendings.asUnmodifiableObservableList().size() + " spendings";
        // TODO: refine later
    }

    @Override
    public ObservableList<Spending> getSpendingList() {
        return spendings.asUnmodifiableObservableList();
    }

    @Override
    public List<Reminder> getReminderList() {
        return reminders;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SpendingBook // instanceof handles nulls
                && spendings.equals(((SpendingBook) other).spendings)
                && reminders.equals(((SpendingBook) other).reminders)
                && budget.equals(((SpendingBook) other).budget));
    }

    @Override
    public int hashCode() {
        return Objects.hash(spendings, reminders, budget);
    }
}
