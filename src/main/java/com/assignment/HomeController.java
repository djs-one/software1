package com.assignment;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
/**
 * @author Dan Stadt
 */
public class HomeController implements Initializable {
    @FXML
    public Button deletePartButton;
    @FXML
    public Button modifyPartButton;
    @FXML
    public Button addPartButton;
    //#region Part Parameters
    @FXML
    public TextField searchPartField;
    @FXML
    public TableView<Part> partTable;
    //https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/TableView.html
    @FXML
    public TableColumn<Part, Integer> partId;
    @FXML
    public TableColumn<Part, String> partName;
    @FXML
    public TableColumn<Part, Integer> partInventory;
    @FXML
    public TableColumn <Part,Double> partCost;
    @FXML
    public TextField searchProductField;
    @FXML
    public TableView<Product> productTable;
    @FXML
    public TableColumn<Product, Integer> productId;
    @FXML
    public TableColumn<Product, String> productName;
    @FXML
    public TableColumn<Product, Double> productCost;
    @FXML
    public TableColumn<Product, Integer> productInventory;
    //#endregion
    @FXML
    public Button exitMain = new Button();
    /**
     * Start the Home View.
     * @param url enter location of fxml file
     * @param resourceBundle enter name of fxml file
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        partInventory.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        partCost.setCellValueFactory(new PropertyValueFactory<>("Price"));
        partTable.setPlaceholder(new Label("No associated parts found."));
        productTable.setPlaceholder(new Label("No associated parts found."));
        productId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        productName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        productInventory.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        productCost.setCellValueFactory(new PropertyValueFactory<>("Price"));
        updatePartTable();
        updateProductTable();
        exitMain.setCancelButton(true);
    }

    /**
     * Add a Part to the Inventory.
     * @param actionEvent  triggered when add part button clicked
     * @throws IOException thrown by FXMLoader if fxml file could not load
     */
    public void addPart(ActionEvent actionEvent) throws IOException{
        FXMLLoader partLoader = new FXMLLoader(MainApplication.class.getResource("PartView.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(partLoader.load());
        stage.setScene(scene);
        int newId;
        if (Inventory.getAllParts().isEmpty()){newId  = 1;}
        else{
            newId = Inventory.getAllParts().get(Inventory.getAllParts().size() - 1).getId() + 1;
        }
        PartController partController = partLoader.getController();
        partController.partIdField.setText(String.valueOf(newId));
        partController.location = Inventory.getAllParts().size() + 1; //Used to add new part instead of replacing part
        partController.addPartCheck = true;
        partController.partTitle.setText("Add Part");
        stage.showAndWait();
    }
    /**
     * Opens Product Form with an auto-generated ID and blank fields. Product is not added until saved in Product View.
     * RUNTIME ERROR could not add Part if Product table was empty. Solved by assigning ID if product table empty
     * @param actionEvent triggered when add product button clicked
     * @throws IOException thrown by FXMLoader if fxml file could not load
     */
    public void addProduct(ActionEvent actionEvent) throws IOException{
        FXMLLoader productLoader = new FXMLLoader(MainApplication.class.getResource("ProductView.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(productLoader.load());
        stage.setScene(scene);
        int newId;
        if (Inventory.getAllProducts().isEmpty()){newId = 1;}
        else {
            newId = Inventory.getAllProducts().get(Inventory.getAllProducts().size() - 1).getId() + 1;
        }
        ProductController productController = productLoader.getController();
        productController.selectedProduct = new Product();
        productController.productId.setText(String.valueOf(newId));
        productController.location = Inventory.getAllParts().size() + 1;    //Used to create new Product
        productController.addPartCheck = true;
        productController.productTitle.setText("Add Product");
        stage.showAndWait();
    }
    /**
     * Checks if input is not null. Returns true if not null. If null, generates an error message with input String.
     * @param object any input object to check if null
     * @param alertMessage message to display if object is null
     * @return true if not null, otherwise false
     */
    static boolean isNotNull(Object object, String alertMessage){
        if (object == null){
            Alert alert = new Alert(Alert.AlertType.ERROR, alertMessage);
            alert.showAndWait();
            return false;
        }
        else{return true;}
    }

    /**
     * Deletes a Part from the inventory. Generates an error message if part not deleted.
     * @param actionEvent triggered when delete part button clicked
     */
    public void deletePart(ActionEvent actionEvent) {
        if(isNotNull(partTable.getSelectionModel().getSelectedItem(), "Part not selected.")) {
            Alert confirmDelete = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this part?");
            if (confirmDelete.showAndWait().isPresent() && confirmDelete.getResult() == ButtonType.OK) {          //https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/Alert.html
                Part deletedPart = partTable.getSelectionModel().getSelectedItem();
                boolean deleteCheck = Inventory.deletePart(deletedPart);
                if (!deleteCheck) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Part could not be deleted.");
                    alert.showAndWait();
                }
            }
        }
    }

    /**
     * Delete a Product from the Inventory if and only if no Parts are associated with it. Generates error message if Product not deleted.
     * @param actionEvent   triggered when delete product button clicked
     **/
    public void deleteProduct(ActionEvent actionEvent) {
        if(isNotNull(productTable.getSelectionModel().getSelectedItem(), "No product selected.")) {
            Alert confirmDelete = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this part?");
            confirmDelete.showAndWait();
            if (confirmDelete.getResult() == ButtonType.OK) {          //https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/Alert.html
                Product deletedProduct = productTable.getSelectionModel().getSelectedItem();
                boolean deleteCheck = Inventory.deleteProduct(deletedProduct);
                if (!deleteCheck) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Product could not be deleted because a part is associated with it.");
                    alert.showAndWait();
                }
            }
        }
    }
    /**
     * Exits program
     */
    @FXML
    protected void exitMain(){
        Stage stage = (Stage) exitMain.getScene().getWindow();
        stage.close();
    }

    /** Opens the Part Form and fills fields with information from selected Part.
     * @param actionEvent triggered when modify part button clicked*
     * @throws IOException thrown by FXMLoader if fxml file could not load
     */
    public void modifyPart(ActionEvent actionEvent) throws IOException {
        if(isNotNull(partTable.getSelectionModel().getSelectedItem(), "No part selected.")) {
            FXMLLoader partLoader = new FXMLLoader(MainApplication.class.getResource("PartView.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(partLoader.load());
            PartController partController = partLoader.getController();
            Part selectedPart = partTable.getSelectionModel().getSelectedItem();
            partController.partIdField.setText(String.valueOf(selectedPart.getId()));
            partController.partPriceField.setText(String.format("%,.2f", selectedPart.getPrice()));
            partController.partMinField.setText(String.valueOf(selectedPart.getMin()));
            partController.partMaxField.setText(String.valueOf(selectedPart.getMax()));
            partController.partInventoryField.setText(String.valueOf(selectedPart.getStock()));
            partController.partNameField.setText(selectedPart.getName());
            if (selectedPart instanceof Outsourced) {
                partController.outsourcedButton.fire();
                partController.machineIdOrCompanyField.setText(((Outsourced) selectedPart).getCompanyName());
            } else{
                partController.inHouseButton.fire();
                partController.machineIdOrCompanyField.setText(String.valueOf(((InHouse) selectedPart).getMachineId()));
            }
            partController.selectedPart = selectedPart;
            partController.location = Inventory.getAllParts().indexOf(selectedPart);
            partController.partTitle.setText("Modify Part");
            stage.setScene(scene);
            stage.showAndWait();
        }
    }

    /** Opens the Product Form and fills fields and table with information from selected Product.
     * @param actionEvent when Modify Product button clicked
     * @throws IOException thrown by FXMLoader if fxml file could not load
     */
    public void modifyProduct(ActionEvent actionEvent) throws IOException{
        if(isNotNull(productTable.getSelectionModel().getSelectedItem(), "No product selected.")) {
            FXMLLoader productLoader = new FXMLLoader(ProductController.class.getResource("ProductView.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(productLoader.load());
            ProductController productController = productLoader.getController();
            Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
            productController.productId.setText(String.valueOf(selectedProduct.getId()));
            productController.productPrice.setText(String.format("%,.2f", selectedProduct.getPrice()));
            productController.productMin.setText(String.valueOf(selectedProduct.getMin()));
            productController.productMax.setText(String.valueOf(selectedProduct.getMax()));
            productController.productInventory.setText(String.valueOf(selectedProduct.getStock()));
            productController.productName.setText(selectedProduct.getName());
            productController.selectedProduct = selectedProduct;
            productController.location = Inventory.getAllProducts().indexOf(selectedProduct);
            productController.productTitle.setText("Modify Product");
            productController.allPartsTable.setItems(Inventory.getAllParts()); ;
            productController.productPartTable.setItems(selectedProduct.getAllAssociatedParts());
            stage.setScene(scene);
            stage.showAndWait();
        }
    }
    /**
     * Search for a part by ID and Name. Returns a list of Parts with the name containing search string and/or ID value. If blank, resets to all Products.
     * RUNTIME ERROR getCharacters() returns a CharSequence, requires toString() to cast instead of (String)
     * @param actionEvent triggered when enter pressed while search bar selected
     */
    public void searchPart(ActionEvent actionEvent) {
        ObservableList<Part> foundParts = FXCollections.observableArrayList();
        String partStr = searchPartField.getCharacters().toString();
        int partInt;
        if (!partStr.isBlank()) {
            if (Inventory.lookupPart(partStr) != null) {
                foundParts = Inventory.lookupPart(partStr);
            }
            try {
                partInt = Integer.parseInt(partStr);
                Part foundPart = Inventory.lookupPart(partInt);
                if (foundPart != null){foundParts.add(foundPart);}
            }
            catch (NumberFormatException ignored){}
            partTable.setItems(foundParts);
        }
        else{updatePartTable();}
        if (foundParts == null || foundParts.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Part not found.");
            alert.showAndWait();
            searchPartField.clear();
            updatePartTable();
        }
    }

    /**
     * Search for a part by ID and Name. Returns a list of Parts with the name containing search string and/or ID value. If blank, resets to all Products.
     * @param actionEvent triggered when enter pressed while search bar selected
     */
    public void searchProduct(ActionEvent actionEvent) {
        ObservableList<Product> foundProducts = FXCollections.observableArrayList();
        String productStr = searchProductField.getCharacters().toString();
        int productInt;
        if (!productStr.isBlank()) {
            if (Inventory.lookupProduct(productStr) != null){
                foundProducts = Inventory.lookupProduct(productStr);
            }
            try {
                productInt = Integer.parseInt(productStr);
                Product foundProduct = Inventory.lookupProduct(productInt);
                if (foundProduct != null){foundProducts.add(foundProduct);}
            }
            catch (NumberFormatException ignored){}
            productTable.setItems(foundProducts);
            if (foundProducts == null || foundProducts.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Product not found.");
                alert.showAndWait();
                searchProductField.clear();
                updateProductTable();
            }
        }
        else{updateProductTable();}

    }
    /**
     * Updates part table with latest parts
     */
    public void updatePartTable (){
        ObservableList<Part> partList = Inventory.getAllParts();
        partTable.setItems(partList);
    }

    /**
     * Updates product table with latest parts
     */
    public void updateProductTable (){
        ObservableList<Product> productList = Inventory.getAllProducts();
        productTable.setItems(productList);
    }
}