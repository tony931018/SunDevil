package application;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;


public class EquipmentListingView {
    public static void show(Stage stage) {
        VBox root = new VBox();
        root.getChildren().add(Navigation.create(stage, "Add Listing"));

        Label title = new Label("Add Equipment Listing");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Label message = new Label("");
        message.setStyle("-fx-font-weight: bold;");

        if (!DataStore.currentUserRole.equals("Owner")) {
            message.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
            message.setText("Only owners can add equipment listings.");

            Button back = new Button("Back to Browse");
            back.setOnAction(e -> BrowseView.show(stage));

            VBox content = new VBox(15, title, message, back);
            content.setPadding(new Insets(30));

            root.getChildren().add(content);
            stage.setScene(new Scene(root, 1000, 650));
            return;
        }

        if (DataStore.profileCompleted) {
            message.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
            message.setText("Please complete your profile before adding a listing.");

            Button goProfile = new Button("Go to Profile");
            goProfile.setStyle("-fx-background-color: #FFC627; -fx-font-weight: bold;");

            VBox content = new VBox(15, title, message, goProfile);
            content.setPadding(new Insets(30));

            root.getChildren().add(content);
            stage.setScene(new Scene(root, 1000, 650));
            return;
        }

        TextField name = field("Equipment Name");

        ComboBox<String> category = new ComboBox<>();
        category.getItems().addAll(
                "Electronics Devices",
                "Computer & Laptop",
                "Camera & Photo",
                "Gaming Console"
        );
        category.setPromptText("Category");
        category.setMaxWidth(350);

        ComboBox<String> condition = new ComboBox<>();
        condition.getItems().addAll("Like New", "Good", "Used");
        condition.setPromptText("Condition");
        condition.setMaxWidth(360);

        TextField value = field("Estimated Value");

        Label preview = new Label("Daily rate and deposit will be calculated automatically.");

        Button previewBtn = new Button("Preview Price");
        previewBtn.setOnAction(e -> {
            try {
                double itemValue = Double.parseDouble(value.getText());

                preview.setText(
                        "Daily Rate: $"
                                + String.format("%.2f", itemValue * 0.05)
                                + " | Deposit: $"
                                + String.format("%.2f", itemValue * 0.20)
                );

                message.setText("");

            } 
        });

        Button publish = new Button("Publish Product");
        publish.setStyle("-fx-background-color: #8C1D40; -fx-text-fill: white; -fx-font-weight: bold;");

        publish.setOnAction(e -> {
            if (name.getText().isEmpty()
                    || category.getValue() == null
                    || condition.getValue() == null
                    || value.getText().isEmpty()) {

                message.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                message.setText("Please complete all fields.");
                return;
            }

            try {
                Equipment item = new Equipment(
                        name.getText(),
                        category.getValue(),
                        DataStore.profileName,
                        Double.parseDouble(value.getText())
                );

                DataStore.equipmentList.add(item);
                DataStore.myListings.add(item);

                MyListingsView.show(stage);

            } catch (Exception ex) {
                message.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                message.setText("Estimated value must be a number.");
            }
        });

        VBox content = new VBox(12, title, message, name, category, value, previewBtn, preview, publish);
        content.setPadding(new Insets(30));

        root.getChildren().add(content);

        stage.setScene(new Scene(root, 1000, 650));
    }

    private static TextField field(String prompt) {
        TextField field = new TextField();
        field.setPromptText(prompt);
        field.setMaxWidth(350);
        return field;
    }
}
