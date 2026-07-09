package application;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class EquipmentDetailsView {

    public static void show(Stage stage, Equipment item) {
        showDetails(stage, item, true);
    }

    public static void showOwnerDetails(Stage stage, Equipment item) {
        showDetails(stage, item, false);
    }

    private static void showDetails(Stage stage, Equipment item, boolean showRentButton) {
        VBox root = new VBox();
        root.getChildren().add(Navigation.create(stage, "Browse"));

        Label title = new Label(item.getName());
        title.setStyle("-fx-font-size: 25px; -fx-font-weight: bold;");

        Label message = new Label("");
        message.setStyle("-fx-font-weight: bold;");

        VBox content = new VBox();
        content.setSpacing(12);
        content.setPadding(new Insets(30));

        content.getChildren().addAll(
                title,
                message,
                new Label("Owner: " + item.getOwner()),
                new Label("Condition: " + item.getCondition()),
                new Label("Estimated Value: $" + String.format("%.2f", item.getValue())),
                new Label("Daily Rate: $" + String.format("%.2f", item.getDailyRate())),
                new Label("Deposit: $" + String.format("%.2f", item.getDeposit())),
                new Label("Status: " + item.getStatus())
        );

        if (showRentButton) {
            ComboBox<Integer> days = new ComboBox<>();
            days.getItems().addAll(1, 2, 3, 5, 7, 14);
            days.setValue(1);

            Label total = new Label();
            updateTotal(total, item, days.getValue());

            days.setOnAction(e -> updateTotal(total, item, days.getValue()));

            Button rent = new Button("Rent Now");
            rent.setStyle("-fx-background-color: #8C1D40; -fx-text-fill: white; -fx-font-weight: bold;");

            rent.setOnAction(e -> {
                if (!item.isAvailable()) {
                    message.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                    message.setText("This item is already rented.");
                    return;
                }

                item.rent();
                Rental rental = new Rental(item, DataStore.currentUser, days.getValue());
                DataStore.rentals.add(rental);

                message.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
                message.setText("Rental confirmed.");

                MyRentalsView.show(stage);
            });

            content.getChildren().addAll(
                    new Label("Rental Duration:"),
                    days,
                    total,
                    rent
            );
        } else {
            
        }

        Button back = new Button("Back");
        back.setOnAction(e -> {
            if (showRentButton) {
                BrowseView.show(stage);
            } else {
                MyListingsView.show(stage);
            }
        });

        content.getChildren().add(back);

        root.getChildren().add(content);

        stage.setScene(new Scene(root, 1000, 650));
    }

    private static void updateTotal(Label total, Equipment item, int days) {
        double price = item.getDailyRate() * days + item.getDeposit();
        total.setText("Total Cost: $" + String.format("%.2f", price));
        total.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
    }
}
