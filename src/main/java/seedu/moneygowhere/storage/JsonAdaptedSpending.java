package seedu.moneygowhere.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.moneygowhere.commons.exceptions.IllegalValueException;
import seedu.moneygowhere.model.spending.Cost;
import seedu.moneygowhere.model.spending.Date;
import seedu.moneygowhere.model.spending.Name;
import seedu.moneygowhere.model.spending.Remark;
import seedu.moneygowhere.model.spending.Spending;
import seedu.moneygowhere.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Spending}.
 */
class JsonAdaptedSpending {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Spending's %s field is missing!";

    private final String name;
    private final String date;
    private final String remark;
    private final String cost;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedSpending} with the given Spending details.
     */
    @JsonCreator
    public JsonAdaptedSpending(@JsonProperty("name") String name, @JsonProperty("date") String date,
        @JsonProperty("remark") String remark, @JsonProperty("cost") String cost,
            @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        this.name = name;
        this.date = date;
        this.remark = remark;
        this.cost = cost;

        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Spending} into this class for Jackson use.
     */
    public JsonAdaptedSpending(Spending source) {
        name = source.getName().fullName;
        date = source.getDate().value;
        remark = source.getRemark().value;
        cost = source.getCost().value;
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted Spending object into the model's {@code Spending} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted Spending.
     */
    public Spending toModelType() throws IllegalValueException {
        final List<Tag> spendingTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            spendingTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName()));
        }
        if (!Date.isValidDate(date)) {
            throw new IllegalValueException(Date.MESSAGE_CONSTRAINTS);
        }
        final Date modelDate = new Date(date);

        if (remark == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Remark.class.getSimpleName()));
        }

        final Remark modelRemark = new Remark(remark);

        if (cost == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Cost.class.getSimpleName()));
        }
        if (!Cost.isValidCost(cost)) {
            throw new IllegalValueException(Cost.MESSAGE_CONSTRAINTS);
        }
        final Cost modelCost = new Cost(cost);

        final Set<Tag> modelTags = new HashSet<>(spendingTags);

        return new Spending(modelName, modelDate, modelRemark, modelCost, modelTags);
    }
}
