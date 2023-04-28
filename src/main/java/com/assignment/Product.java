package com.assignment;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
/**
 * @author Daniel Stadt
 */
public class Product {
    //Declarations
    private final ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    /**
     * Default constructor
     */
    public Product(){}  //Default constructor
    /**
     * Generates a new Product with all fields populated.
     * @param id the product ID to set
     * @param name the product name to set
     * @param price the product price to set
     * @param stock the product stock to set
     * @param min the product minimum to set
     * @param max the product maximum to set
     */
    public Product(int id, String name, double price, int stock, int min, int max){
        setId(id);
        setName(name);
        setPrice(price);
        setStock(stock);
        setMin(min);
        setMax(max);
    }
    /**
     * Adds a part to the list of parts associated with the Product.
     * @param part the part to be associated with this product
     */
    public void addAssociatedPart(Part part){
        associatedParts.add(part);
    }
    /**
     * Deletes the selected part from the list of parts associated with the Product.
     * @param selectedPart search for selected part to delete from list
     * @return true if part found, false if part not found.
     */
    public boolean deleteAssociatedPart(Part selectedPart){
        for (int i = 0; i < associatedParts.size(); i++) {
            if (associatedParts.get(i) == selectedPart){
                associatedParts.remove(i, i + 1);
                return true;
            }
        }
        return false;
    }
    /**
     * Gets a list of all parts associated with the Product.
     * @return an ObservableList of all associated parts
     */
    public ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;
    }
    /**
     * Get the Product's id integer.
     * @return integer of the product id.
     */
    public int getId(){
        return this.id;
    }
    /**
     * Get the Product's maximum inventory integer.
     * @return integer of the maximum inventory.
     */
    public int getMax() {
        return max;
    }
    /**
     * Get the Product's minimum product inventory amount.
     * @return integer of the minimum inventory.
     */
    public int getMin() {
        return min;
    }
    /**
     * Get the Product's name.
     * @return String of the product name.
     */
    public String getName(){
        return name;
    }
    /**
     * Get the Product's price.
     * @return double of the product price.
     */
    public double getPrice() {
        return price;
    }
    /**
     * Get the Product's stock.
     * @return integer of the product inventory amount.
     */
    public int getStock() {
        return stock;
    }
    /**
     * Set the Product's id.
     * @param id the product id to set
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * Set the Product's maximum inventory.
     * @param max the maximum product stock to set
     */
    public void setMax(int max) {
        this.max = max;
    }
    /**
     * Set the Product's inventory.
     * @param min the minimum product stock to set
     */
    public void setMin(int min){
        this.min = min;
    }
    /**
     * Set the Product's name.
     * @param name the product name to set
     */
    public void setName(String name){
        this.name = name;
    }
    /**
     * Set the Product's price.
     * @param price the product price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }
    /**
     * Set the Product's stock.
     * @param stock the amount of product inventory to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }
}