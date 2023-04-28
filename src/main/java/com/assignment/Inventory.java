package com.assignment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 * @author Dan Stadt
 */
public class Inventory {
    /**
     * Default Constructor
     */
    public Inventory() { }
    private final static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private final static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    /**
     * Add a Part to the inventory.
     * @param part the part to be added
     */
    public static void addPart(Part part) {
        allParts.add(part);
    }
    /**
     * Add a Product to the inventory.
     * @param product the product to be added
     */
    public static void addProduct(Product product) {
        allProducts.add(product);
    }
    /**
     * Delete a Part from the inventory.
     * @param selectedPart the part to delete from parts list
     * @return true if part added, else false
     */
    public static boolean deletePart(Part selectedPart) {
        for (int i = 0; i < allParts.size(); i++) {
            if (allParts.get(i) == selectedPart) {
                allParts.remove(i, i + 1);
                return true;
            }
        }
        return false;
    }
    /**
     * Delete a Product from the inventory if and only if there are no parts associated with it.
     * @param selectedProduct product to be deleted from products list
     * @return checks false if product has associated parts or not deleted, true if deleted successfully
     *
     */
    public static boolean deleteProduct(Product selectedProduct) {
        ObservableList<Part> associatedParts = selectedProduct.getAllAssociatedParts();
        if (associatedParts == null){
            return false;
        }
        for (int i = 0; i < allProducts.size(); i++) {
            if (allProducts.get(i) == selectedProduct) {
                allProducts.remove(i, i + 1);
                return true;
            }
        }
        return false;
    }
    /**
     * Get the list of all parts in the inventory.
     * @return an ObservableList of all parts
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * Get a list of all products in the inventory.
     * @return an ObservableList of all products
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
    //endregion
    /**
     * Search for a product in the inventory.
     * FUTURE IMPROVEMENT: Create hashmap for part ids for faster search of a larger inventory.
     * @param partId search part id
     * @return part id if found, else null
     */
    public static Part lookupPart(int partId) {
        for (Part partLookup : allParts) {
            if (partId == partLookup.getId() && partId > 0) {
                return partLookup;
            }
        }
        return null;
    }

    /**
     * Search for a product by its id. Returns the product if for. Returns null if not found.
     * @param productId search for product id
     * @return product if found, else null
     */
    public static Product lookupProduct(int productId) {
        for (Product productLookup : allProducts) {
            if (productId == productLookup.getId() && productId > 0) {
                return productLookup;
            }
        }
        return null;
    }

    /**
     * Search for a part by its name. Returns list of products containing the search string. Returns null if not found.
     * @param partName search for part name
     * @return part list of parts with that name, else null
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> foundParts = FXCollections.observableArrayList();
        for (Part search : allParts) {
            if (search.getName().contains(partName)) {
                foundParts.add(search);
            }
        }
        if (foundParts.isEmpty()) {
            return null;
        }
        else {
            return foundParts;
        }
    }
    /**
     * Search for a product by its name. Returns list of products containing the search string. Returns null if not found.
     * @param productName search for product name
     * @return list of products with name if found, else null
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> foundProducts = FXCollections.observableArrayList();
        for (Product search : allProducts) {
            if (search.getName().contains(productName)) {
                foundProducts.add(search);
            }
        }
        if (foundProducts.isEmpty()) {
            return null;
        } else {
            return foundProducts;
        }
    }
    /**
     * Updates the Part at a designated index in the inventory's part list.
     * @param index the location of part to be updated
     * @param selectedPart the updated Part to replace part at index
     */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    /**
     * Updates the Product at a designated index in the inventory's product list.
     * @param index the location of the product to be updated
     * @param selectedProduct the product to replace the part at index
     */
    public static void updateProduct(int index, Product selectedProduct) {
        allProducts.set(index, selectedProduct);
    }




}