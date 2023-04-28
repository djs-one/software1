package com.assignment;
/**
 * @author Dan Stadt
 */
public class Outsourced extends com.assignment.Part {
    private String companyName;
    /**
     * Constructor sets all fields
     * @param id the part ID to set
     * @param name the part name to set
     * @param price the part price to set
     * @param stock the part stock to set
     * @param min the part minimum to set
     * @param max the part maximum to set
     * @param companyName the company name to set
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName){
        setId(id);
        setName(name);
        setPrice(price);
        setStock(stock);
        setMin(min);
        setMax(max);
        setCompanyName(companyName);
    }

    /**
     * Default constructor
     */
    public Outsourced() { }

    /**
     * Get company name.
     * @return the company name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Set company name.
     * @param companyName the company name to set
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

}
