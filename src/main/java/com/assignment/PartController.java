package com.assignment;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Dan Stadt
 */
public class PartController implements Initializable {
    /**
     * Loads the FXML file for the Home Controller
     */
    public FXMLLoader fxmlLoader = new FXMLLoader(HomeController.class.getResource("HomeView.fxml"));
    public RadioButton inHouseButton;
    public RadioButton outsourcedButton;
    public Button saveButton;
    public Button cancelButton;
    public TextField partMinField;
    public TextField partMaxField;// = new TextField(String.valueOf(modifiedPart.getMax()));
    public TextField partPriceField;
    public TextField partInventoryField;
    public TextField partNameField;
    public TextField partIdField;
    public TextField machineIdOrCompanyField;
    public Text machineIdOrCompanyText;
    public Text partTitle;
    public ToggleGroup partButton;
    public Part selectedPart;
    public int location;
    public boolean addPartCheck;

    /**
     * Starts the Part Form.
     * @param url the location of the fxml file to load
     * @param resourceBundle the fxml file to load
     */
    @Override
    public void initialize (URL url, ResourceBundle resourceBundle){
        cancelButton.setCancelButton(true);
        partButton = new ToggleGroup();
        inHouseButton.setToggleGroup(partButton);
        outsourcedButton.setToggleGroup(partButton);
    }

    /**
     * Sets the Part being added or modified to an Outsourced Part. Updates bottom field to Company Name. Discarded if user exits before saving.
     * @param actionEvent triggered when Outsourced button clicked
     */
    public void clickOutsourced(ActionEvent actionEvent) {
        machineIdOrCompanyText.setText("Company ");
        machineIdOrCompanyField.setVisible(true);
        machineIdOrCompanyField.clear();
    }
    /**
     * Sets the Part being added or modified to an InHouse Part. Updates bottom field to Machine ID. Discarded if user exits before saving.
     * @param actionEvent when InHouse button clicked
     *                    bottom field changes to Machine ID
     */
    public void clickInHouse(ActionEvent actionEvent) {
        machineIdOrCompanyText.setText("Machine ID ");
        machineIdOrCompanyField.setVisible(true);
        machineIdOrCompanyField.clear();
    }
    /**
     * Closes the Part Form.
     * @param actionEvent triggered when cancel button clicked
     */

    public void cancelModify(ActionEvent actionEvent) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
    /**
     * Checks entered data for completeness and formatting. If passes all checks, Part is saved and Part Form is closed.
     * @param actionEvent triggered when saved button clicked
     * @throws IOException thrown if error loading fxml file
     */
    public void saveButtonClick(ActionEvent actionEvent) throws IOException {
        InHouse inHousePart;
        Outsourced outsourcedPart;
        String idStr = partIdField.getText();
        String name = partNameField.getText();
        String priceStr = partPriceField.getText();
        String stockStr = partInventoryField.getText();
        String minStr = partMinField.getText();
        String maxStr = partMaxField.getText();
        int id;
        double price;
        int stock;
        int min;
        int max;
        Alert alert = new Alert (Alert.AlertType.ERROR);
        if (name.isBlank()) {
            alert.setContentText("Name field cannot be empty");
            alert.showAndWait();
        }
        else if(priceStr.isBlank()){
            alert.setContentText("Price field cannot be empty");
            alert.showAndWait();
        }
        else if(stockStr.isBlank()) {
            alert.setContentText("Inventory field cannot be empty");
            alert.showAndWait();
        }
        else if(minStr.isBlank()) {
            alert.setContentText("Min field cannot be empty");
            alert.showAndWait();
        }
        else if(maxStr.isBlank()) {
            alert.setContentText("Max field cannot be empty");
            alert.showAndWait();
        }
        else{
            try{id = Integer.parseInt(idStr);}
            catch (NumberFormatException e) {
                alert.setContentText("ID field not formatted properly.");
                alert.showAndWait();
                return;
            }
            try{price = Double.parseDouble(priceStr);}
            catch (NumberFormatException e) {
                alert.setContentText("Price field not formatted properly.");
                alert.showAndWait();
                return;
            }
            try{stock = Integer.parseInt(stockStr);}
            catch (NumberFormatException e) {
                alert.setContentText("Inventory field not formatted properly.");
                alert.showAndWait();
                return;
            }
            try{min = Integer.parseInt(minStr);}
            catch (NumberFormatException e) {
                alert.setContentText("Minimum field not formatted properly.");
                alert.showAndWait();
                return;
            }
            try{max = Integer.parseInt(maxStr);}
            catch (NumberFormatException e) {
                alert.setContentText("Maximum field not formatted properly.");
                alert.showAndWait();
                return;
            }
            if (min > max){
                alert.setContentText("Minimum cannot be greater than maximum.");
                alert.showAndWait();
            }
            if (min > max){
                alert.setContentText("Minimum cannot be greater than maximum.");
                alert.showAndWait();
            }
            else if (stock < min || stock > max){
                alert.setContentText("Inventory cannot be outside of minimum/maximum range.");
                alert.showAndWait();
            }
            else {
                if (inHouseButton.isSelected()) {
                    String machineIdStr = machineIdOrCompanyField.getText();
                    int machineId;
                    if (machineIdStr.isBlank()){
                        alert.setContentText("Machine ID cannot be blank.");
                        alert.showAndWait();
                        return;
                    }
                    else {
                        try {
                            machineId = Integer.parseInt(machineIdStr);
                            inHousePart = new InHouse(id, name, price, stock, min, max, machineId);
                            if (addPartCheck) {
                                Inventory.getAllParts().add(inHousePart);
                            } else {
                                Inventory.updatePart(location, inHousePart);
                            }
                        } catch (NumberFormatException e) {
                            alert.setContentText("Machine ID field not formatted properly.");
                            alert.showAndWait();
                            return;
                        }
                    }
                } else if (outsourcedButton.isSelected()) {
                    String companyName = machineIdOrCompanyField.getText();
                    if (companyName.isBlank()) {
                        alert.setContentText("Company Name cannot be blank.");
                        alert.showAndWait();
                        return;
                    }
                    else{
                        outsourcedPart = new Outsourced(id, name, price, stock, min, max, companyName);
                        if (addPartCheck) {
                            Inventory.getAllParts().add(outsourcedPart);
                        } else {
                            Inventory.updatePart(location, outsourcedPart);
                        }
                    }
                } else {       //Neither button selected
                    alert.setContentText("Check that In-House or Outsourced is selected");
                    alert.showAndWait();
                    return;
                }
                fxmlLoader.load();
                HomeController homeController = fxmlLoader.getController();
                homeController.updatePartTable();
                ((Stage) cancelButton.getScene().getWindow()).close();
            }
        }
    }
}