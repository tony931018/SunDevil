package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Navigation {

    public static HBox create(Stage stage) {
        return create(stage, "");
    }

    public static HBox create(Stage stage, String activePage) {
        Label logo = new Label("SUNDEVIL Gear Share");
        logo.setStyle("-fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;");

        Button browse = button("Browse", activePage);
        Button rentals = button("My Rentals", activePage);
        Button listings = button("My Listings", activePage);
        Button add = button("Add Listing", activePage);
        Button profile = button("Profile", activePage);
        Button logout = button("Logout", activePage);

        browse.setOnAction(e -> BrowseView.show(stage));
        rentals.setOnAction(e -> MyRentalsView.show(stage));
        listings.setOnAction(e -> MyListingsView.show(stage));
        add.setOnAction(e -> EquipmentListingView.show(stage));
        profile.setOnAction(e -> ProfileView.show(stage));
        logout.setOnAction(e -> {
            DataStore.clearCurrentUser();
            LoginView.show(stage);
        });

        HBox nav = new HBox(10);
        nav.getChildren().add(logo);

        if (DataStore.currentUserRole.equals("Renter")) {
            nav.getChildren().addAll(browse, rentals, profile, logout);
        } else if (DataStore.currentUserRole.equals("Owner")) {
            nav.getChildren().addAll(browse, rentals, listings, add, profile, logout);
        } else {
            nav.getChildren().addAll(browse, rentals, listings, add, profile, logout);
        }

        nav.setPadding(new Insets(15));
        nav.setAlignment(Pos.CENTER_LEFT);
        nav.setStyle("-fx-background-color: #8C1D40;");

        return nav;
    }

    private static Button button(String text, String activePage) {
        Button button = new Button(text);
        button.setPrefWidth(115);

        if (text.equals(activePage)) {
            button.setStyle("-fx-background-color: #FFC627; -fx-text-fill: black; -fx-font-weight: bold;");
        } else {
            button.setStyle("-fx-background-color: white; -fx-text-fill: #8C1D40; -fx-font-weight: bold;");
        }

        return button;
    }
} 
