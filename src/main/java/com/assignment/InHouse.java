package com.assignment;
/**
 * @author Dan Stadt
 */
public class InHouse extends Part {
    private int machineId;

    /**
     * Constructor sets all fields of InHouse part.
     * @param id part id to set
     * @param name part name to set
     * @param price part price to set
     * @param stock part stock to set
     * @param min part minimum to set
     * @param max part maximum to set
     * @param machineId machine ID to set
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId){
        setId(id);
        setName(name);
        setPrice(price);
        setStock(stock);
        setMin(min);
        setMax(max);
        setMachineId(machineId);
    }
    /**
     * Default Constructor.
     */
    public InHouse(){}
    /**
     * Get the machine ID.
     * @return integer of the machine id
     */
    public int getMachineId(){
        return machineId;
    }
    /**
     * Set the Machine ID.
     * @param machineId the machine ID to set
     */
    public void setMachineId(int machineId){
        this.machineId = machineId;
    }

}
