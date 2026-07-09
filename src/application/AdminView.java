package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AdminView {
    public static void show(Stage stage) {
        VBox root = new VBox();

        HBox adminHeader = createAdminHeader(stage);
        root.getChildren().add(adminHeader);

        Label title = new Label("Admin Dashboard");
        title.setStyle("-fx-font-size: 26px; -fx-font-weight: bold;");

        Label subtitle = new Label("Admin can manage users, listings, rentals, revenue, and reported issues.");
        subtitle.setStyle("-fx-text-fill: gray;");

        HBox stats = new HBox(
                15,
                box("Users: " + DataStore.users.size()),
                box("Active Listings: " + DataStore.equipmentList.size()),
                box("Current Rentals: " + DataStore.rentals.size()),
                box("Revenue: $" + String.format("%.2f", DataStore.totalRevenue()))
        );

        TextArea area = new TextArea("Click a management button to view admin information.");
        area.setEditable(false);
        area.setPrefHeight(300);

        Button users = adminButton("View Users");
        users.setOnAction(e -> {
            String text = "Users:\n\n";

            for (User user : DataStore.users) {
                text += "- " + user + "\n";
            }

            area.setText(text);
        });

        Button listings = adminButton("View Listings");
        listings.setOnAction(e -> {
            String text = "Listings:\n\n";

            for (Equipment item : DataStore.equipmentList) {
                text += "- " + item + "\n";
            }

            area.setText(text);
        });

        Button rentals = adminButton("View Rentals");
        rentals.setOnAction(e -> {
            String text = "Rentals:\n\n";

            if (DataStore.rentals.isEmpty()) {
                text += "No current rentals.";
            }

            for (Rental rental : DataStore.rentals) {
                text += "- " + rental + "\n";
            }

            area.setText(text);
        });

        Button reports = adminButton("View Reports");
        reports.setOnAction(e -> {
            String text = "Reported Issues:\n\n";

            if (DataStore.reports.isEmpty()) {
                text += "No reports.";
            }

            for (String report : DataStore.reports) {
                text += "- " + report + "\n";
            }

            area.setText(text);
        });

        Button system = adminButton("System Summary");
        system.setOnAction(e -> {
            String text = "System Summary:\n\n"
                    + "Logged in as: " + DataStore.currentUser + "\n"
                    + "Role: " + DataStore.currentUserRole + "\n"
                    + "Total users: " + DataStore.users.size() + "\n"
                    + "Total listings: " + DataStore.equipmentList.size() + "\n"
                    + "Total rentals: " + DataStore.rentals.size() + "\n"
                    + "Total reports: " + DataStore.reports.size() + "\n"
                    + "Total revenue: $" + String.format("%.2f", DataStore.totalRevenue());

            area.setText(text);
        });

        HBox buttons = new HBox(15, users, listings, rentals, reports, system);

        VBox content = new VBox(20, title, subtitle, stats, buttons, area);
        content.setPadding(new Insets(25));

        root.getChildren().add(content);

        stage.setScene(new Scene(root, 1000, 650));
    }

    private static HBox createAdminHeader(Stage stage) {
        Label logo = new Label("SUNDEVIL Gear Share | Admin Portal");
        logo.setStyle("-fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;");

        Label adminName = new Label("Logged in as Admin");
        adminName.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");

        Button logout = new Button("Logout");
        logout.setStyle("-fx-background-color: #070707; -fx-font-weight: bold;");
        logout.setOnAction(e -> {
            DataStore.clearCurrentUser();
            LoginView.show(stage);
        });

        HBox header = new HBox(20, logo, adminName, logout);
        header.setPadding(new Insets(15));
        header.setAlignment(Pos.CENTER_LEFT);
        header.setStyle("-fx-background-color: #222222;");

        return header;
    }

    private static Label box(String text) {
        Label label = new Label(text);
        label.setPadding(new Insets(15));
        label.setStyle("-fx-background-color: lightgray; -fx-font-weight: bold;");
        return label;
    }

    private static Button adminButton(String text) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: #8C1D40; -fx-text-fill: white; -fx-font-weight: bold;");
        return button;
    }
}
