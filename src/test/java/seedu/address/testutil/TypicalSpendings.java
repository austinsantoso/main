package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_COST_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COST_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.spending.Spending;

/**
 * A utility class containing a list of {@code Spending} objects to be used in tests.
 */
public class TypicalSpendings {

    public static final Spending ALICE = new SpendingBuilder().withName("Alice Pauline")
            .withCost("123").withEmail("alice@example.com")
            .withPhone("94351253")
            .withTags("friends").build();
    public static final Spending BENSON = new SpendingBuilder().withName("Benson Meier")
            .withCost("311")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withTags("owesMoney", "friends").build();
    public static final Spending CARL = new SpendingBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withCost("1.50").build();
    public static final Spending DANIEL = new SpendingBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withCost("10.10").withTags("friends").build();
    public static final Spending ELLE = new SpendingBuilder().withName("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com").withCost("134.70").build();
    public static final Spending FIONA = new SpendingBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withEmail("lydia@example.com").withCost("52").build();
    public static final Spending GEORGE = new SpendingBuilder().withName("George Best").withPhone("9482442")
            .withEmail("anna@example.com").withCost("27.10").build();

    // Manually added
    public static final Spending HOON = new SpendingBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com").withCost("1.70").build();
    public static final Spending IDA = new SpendingBuilder().withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com").withCost("2000").build();

    // Manually added - Spending's details found in {@code CommandTestUtil}
    public static final Spending AMY = new SpendingBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withCost(VALID_COST_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Spending BOB = new SpendingBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withCost(VALID_COST_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalSpendings() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical spendings.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Spending spending : getTypicalSpendings()) {
            ab.addSpending(spending);
        }
        return ab;
    }

    public static List<Spending> getTypicalSpendings() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
