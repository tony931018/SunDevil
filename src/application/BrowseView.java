package application;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BrowseView {
    public static void show(Stage stage) {
        VBox root = new VBox();
        root.getChildren().add(Navigation.create(stage, "Browse"));

        Label title = new Label("Browse Equipment");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Label message = new Label("");
        message.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");

        TextField search = new TextField();
        search.setPromptText("Search for equipment...");
        search.setMaxWidth(420);

        ComboBox<String> category = new ComboBox<>();
        category.getItems().addAll(
                "All",
                "Electronics Devices",
                "Computer & Laptop",
                "Camera & Photo",
                "Gaming Console"
        );
        category.setValue("All");

        VBox cards = new VBox(10);
        refresh(stage, cards, category.getValue(), search.getText());

        search.setOnKeyReleased(e -> refresh(stage, cards, category.getValue(), search.getText()));
        category.setOnAction(e -> refresh(stage, cards, category.getValue(), search.getText()));

        VBox content = new VBox(15, title, message, search, category, cards);
        content.setPadding(new Insets(25));

        ScrollPane scroll = new ScrollPane(content);
        scroll.setFitToWidth(true);

        root.getChildren().add(scroll);

        stage.setScene(new Scene(root, 1000, 650));
    }

    private static void refresh(Stage stage, VBox cards, String category, String searchText) {
        cards.getChildren().clear();

        String key;

        if (searchText == null) {
            key = "";
        } else {
            key = searchText.toLowerCase();
        }

        boolean found = false;

        for (Equipment item : DataStore.equipmentList) {
            boolean categoryMatch = category.equals("All") || item.getCategory().equals(category);
            boolean searchMatch = item.getName().toLowerCase().contains(key)
                    || item.getCategory().toLowerCase().contains(key)
                    || item.getCondition().toLowerCase().contains(key);

            if (categoryMatch && searchMatch) {
                cards.getChildren().add(card(stage, item));
                found = true;
            }
        }

        if (!found) {
            cards.getChildren().add(new Label("No equipment found."));
        }
    }

    private static VBox card(Stage stage, Equipment item) {
        Label name = new Label(item.getName());
        name.setStyle("-fx-font-size: 17px; -fx-font-weight: bold;");

        Label info = new Label(item.getCategory() + " | " + item.getCondition() + " | " + item.getStatus());

        Label price = new Label(
                "$" + String.format("%.2f", item.getDailyRate())
                        + "/day | Deposit: $"
                        + String.format("%.2f", item.getDeposit())
        );

        Button details = new Button("View Details");
        details.setOnAction(e -> EquipmentDetailsView.show(stage, item));

        VBox box = new VBox(7, name, info, price, details);
        box.setPadding(new Insets(15));
        box.setStyle("-fx-border-color: lightgray; -fx-border-radius: 8;");

        return box;
    }
}
