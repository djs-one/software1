package com.assignment;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Dan Stadt
 */
public class ProductController implements Initializable {
    public TextField searchProduct;
    public TextField productId;
    public TextField productName;
    public TextField productInventory;
    public TextField productPrice;
    public TextField productMax;
    public TextField productMin;
    public Button removeButton;
    public Button saveButton;
    public Button cancelButton;
    public Button addPartButton;
    public TableView<Part> productPartTable;
    public TableView<Part> allPartsTable;
    public Text productTitle;
    public TableColumn<Part, Double> productPartPrice;
    public TableColumn<Part, Integer> productPartId;
    public TableColumn<Part, String> productPartName;
    public TableColumn<Part, Integer> productPartInventory;
    public TableColumn<Part, Double> allPartsPrice;
    public TableColumn<Part, String> allPartsName;
    public TableColumn<Part, Integer> allPartsId;
    public TableColumn<Part, Integer> allPartsInventory;
    public Product selectedProduct;
    public int location;
    public boolean addPartCheck;
    public TextField searchBar;

    /**
     * Start the product form screen.
     * @param url the location of the fxml file
     * @param resourceBundle the name of the fxml file
     */
    @Override
    public void initialize (URL url, ResourceBundle resourceBundle){
        cancelButton.setCancelButton(true);
        productPartId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        productPartName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        productPartInventory.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        productPartPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));
        if (selectedProduct != null){productPartTable.setItems(selectedProduct.getAllAssociatedParts());}
        productPartTable.setPlaceholder(new Label("No associated parts found."));
        allPartsTable.setPlaceholder(new Label("No parts found in master list."));
        allPartsId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        allPartsName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        allPartsInventory.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        allPartsPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));
        allPartsTable.setItems(Inventory.getAllParts());
    }

    /**
     * Update product table with latest list of parts associated with the current product.
     */
    public void updateProductPartTable(){
        if(selectedProduct != null){
            ObservableList<Part> list = selectedProduct.getAllAssociatedParts();
            productPartTable.setItems(list);
        }
    }
    /**
     * Update product table with the latest list of all products in inventory.
     */
    public void updateAllPartTable(){
        ObservableList<Part> list = Inventory.getAllParts();
        allPartsTable.setItems(list);
    }

    /**
     * Add a part to the inventory.
     * @param actionEvent triggered when add button is clicked.
     */
    public void addPart(ActionEvent actionEvent) {
        Part selectedPart = allPartsTable.getSelectionModel().getSelectedItem();
        if (!selectedProduct.getAllAssociatedParts().contains(selectedPart)) {
            selectedProduct.addAssociatedPart(selectedPart);
            updateProductPartTable();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Part is already associated with this product.");
            alert.showAndWait();
        }
    }

    /**
     * Remove a part from the table. Generates an error message if part could not be removed.
     * @param actionEvent triggered when remove button is pressed
     */
    public void removePart(ActionEvent actionEvent) {
        Part selectedPart = productPartTable.getSelectionModel().getSelectedItem();
        if (HomeController.isNotNull(selectedPart,"No part selected.")){
            Alert confirmDelete = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to remove this part?");
            if (confirmDelete.showAndWait().isPresent() && confirmDelete.getResult() ==ButtonType.OK) {       //https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/Alert.html
                boolean check = selectedProduct.deleteAssociatedPart(selectedPart);
                if (!check) {    //If check fails
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Part could not be deleted.");
                    alert.showAndWait();
                }
            }
        }
    }
    /**
     * Exit the application.
     * @param actionEvent triggered when cancel button pressed
     */
    public void cancelProduct(ActionEvent actionEvent) {
        exit();
    }
    /**
     * Save the details in the product form. Generates an error if fields empty or improperly formatted.
     * @param actionEvent triggered when save button pressed.
     * @throws IOException thrown when if FXMLoader cannot load fxml file
     */
    public void saveProduct(ActionEvent actionEvent) throws IOException{
        String name = productName.getText();
        String idStr = productId.getText();
        String priceStr = productPrice.getText();
        String stockStr = productInventory.getText();
        String minStr = productMin.getText();
        String maxStr = productMax.getText();
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
            try{
                id = Integer.parseInt(idStr);
            }
            catch (NumberFormatException e) {
                alert.setContentText("ID field not formatted properly.");
                alert.showAndWait();
                return;
            }
            try{
                price = Double.parseDouble(priceStr);
            }
            catch (NumberFormatException e) {
                alert.setContentText("Price field not formatted properly.");
                alert.showAndWait();
                return;
            }
            try{
                stock = Integer.parseInt(stockStr);
            }
            catch (NumberFormatException e) {
                alert.setContentText("Inventory field not formatted properly.");
                alert.showAndWait();
                return;
            }
            try{
                min = Integer.parseInt(minStr);
            }
            catch (NumberFormatException e) {
                alert.setContentText("Minimum field not formatted properly.");
                alert.showAndWait();
                return;
            }
            try{
                max = Integer.parseInt(maxStr);
            }
            catch (NumberFormatException e) {
                alert.setContentText("Maximum field not formatted properly.");
                alert.showAndWait();
                return;
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
                selectedProduct.setName(name);
                selectedProduct.setId(id);
                selectedProduct.setPrice(price);
                selectedProduct.setStock(stock);
                selectedProduct.setMin(min);
                selectedProduct.setMax(max);
                if (addPartCheck) {
                    Inventory.getAllProducts().add(selectedProduct);
                } else {
                    Inventory.updateProduct(location, selectedProduct);
                }
                FXMLLoader fxmlLoader = new FXMLLoader(HomeController.class.getResource("HomeView.fxml"));
                fxmlLoader.load();
                HomeController homeController = fxmlLoader.getController();
                homeController.updateProductTable();
                ((Stage) saveButton.getScene().getWindow()).close();
                exit();
            }
        }
    }

    /**
     * Searches the top part table for any parts with a name containing the search string or an id containing the integer.
     * @param actionEvent triggered when enter is pressed while Search Bar selected
     */
    public void searchPart(ActionEvent actionEvent) {
        ObservableList<Part> foundParts = FXCollections.observableArrayList();
        String partStr = searchBar.getCharacters().toString();
        int partInt;
        if (partStr.isBlank()){
            updateAllPartTable();
        }
        else{
            if (Inventory.lookupPart(partStr) != null) {
                foundParts = Inventory.lookupPart(partStr);
            }
            try {
                partInt = Integer.parseInt(partStr);
                Part foundPart = Inventory.lookupPart(partInt);
                if (foundPart != null && foundParts != null) {
                    foundParts.add(foundPart);
                }
            } catch (NumberFormatException ignored) {
            }
            allPartsTable.setItems(foundParts);
            if (foundParts == null || foundParts.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Part not found.");
                alert.showAndWait();
                searchBar.clear();
                updateAllPartTable();
                updateProductPartTable();
            }
        }
    }
    /**
     * Exits the Product Form and returns to the Home View
     */
    public void exit(){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
