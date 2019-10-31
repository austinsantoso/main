package seedu.moneygowhere.ui;

import static java.util.Objects.requireNonNull;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class ResultDisplay extends UiPart<Region> {

    private static final String FXML = "ResultDisplay.fxml";

    @FXML
    private TextArea resultDisplay;

    public ResultDisplay() {
        super(FXML);
        resultDisplay.setText("Welcome to MoneyGoWhere!");
    }

    public void setFeedbackToUser(String feedbackToUser) {
        requireNonNull(feedbackToUser);
        if (feedbackToUser.contains("Status: Deficit")) {
            resultDisplay.setStyle("-fx-text-fill: #ff4253 ;");
        } else {
            resultDisplay.setStyle("");
        }
        resultDisplay.setText(feedbackToUser);
    }

}
