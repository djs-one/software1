package com.assignment;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
/**
 * @author Dan Stadt
 */
public class MainApplication extends Application {
    /**
     * Loads the Home View upon program start.
     * @param stage is required to override start properly; will open first stage
     * @throws IOException required for Stage class
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("HomeView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Inventory - Home");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Starts program.
     * FUTURE ENHANCEMENT: Expand Part class and Part form to find all related parts
     * @param args standard input of main class for command line arguments
     */
    public static void main(String[] args){
        Inventory.addPart(new InHouse(1,"In House Test", 10.00, 15, 12, 100, 12));
        Inventory.addPart(new Outsourced(3,"Outsourced Test", 11.00, 11, 10, 110, "Test Company"));
        Inventory.addProduct(new Product(1, "Product Test", 10.00, 14, 14, 14));
        launch();
    }
}
