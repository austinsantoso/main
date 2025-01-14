package seedu.moneygowhere.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.moneygowhere.commons.core.LogsCenter;
import seedu.moneygowhere.model.ReadOnlySpendingBook;
import seedu.moneygowhere.model.currency.Currency;
import seedu.moneygowhere.model.spending.Spending;

/**
 * Panel containing the list of spending.
 */
public class SpendingListPanel extends UiPart<Region> {
    private static final String FXML = "SpendingListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(SpendingListPanel.class);

    private Currency currency;

    @FXML
    private ListView<Spending> spendingListView;

    public SpendingListPanel(ObservableList<Spending> spendingList, ReadOnlySpendingBook spendingBook) {
        super(FXML);

        this.currency = spendingBook.getCurrencyInUse();
        spendingBook.registerCurrencyChangedListener(((observable, oldValue, newValue) -> {
            this.currency = newValue;

            ObservableList<Spending> spendingObservableList = spendingListView.getItems();

            // Trigger a refresh on all items. Refresh() only refreshes the current visible items.
            spendingListView.setItems(null);
            spendingListView.setItems(spendingObservableList);
        }));

        spendingListView.setItems(spendingList);
        spendingListView.setCellFactory(listView -> new SpendingListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Spending} using a {@code SpendingCard}.
     */
    class SpendingListViewCell extends ListCell<Spending> {
        @Override
        protected void updateItem(Spending spending, boolean empty) {
            super.updateItem(spending, empty);

            if (empty || spending == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new SpendingCard(spending, currency, getIndex() + 1).getRoot());
            }
        }
    }

}
