package seedu.moneygowhere.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.moneygowhere.commons.core.Messages;
import seedu.moneygowhere.logic.commands.exceptions.CommandException;
import seedu.moneygowhere.model.Model;
import seedu.moneygowhere.model.budget.Budget;

/**
 * Sets the budget of the MoneyGoWhere list.
 */
public class BudgetCommand extends Command {
    public static final String COMMAND_WORD = "budget";

    public static final String MESSAGE_SUCCESS = "Budget has been updated!\n"
            + "New budget is ";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sets the monthly budget specified by the amount given by the user input.\n"
            + "Parameters: value (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 10000";

    private Budget budget;

    //Budget must be valid
    public BudgetCommand(Budget budget) {
        this.budget = budget;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (budget.getValue() < 0) {
            throw new CommandException(Messages.MESSAGE_INVALID_BUDGET_AMOUNT);
        }

        model.setBudgetAmount(budget);

        return new CommandResult(MESSAGE_SUCCESS + budget.toString());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof BudgetCommand
            && budget.equals(((BudgetCommand) other).budget));
    }
}
