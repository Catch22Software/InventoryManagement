package INV;

/***
 * Inhouse class holds Part objects with Machine ID fields.
 */
public class InHouse extends Part {


    private int machineId;

    /***
     * Constructor to create InHouse object.
     * @param id Part ID field.
     * @param name Part name field.
     * @param price Part price field.
     * @param stock Part Inventory level field.
     * @param min Part Min level field.
     * @param max Part Max level field.
     * @param machineId Part MachineID field.
     */
    public InHouse(int id, String name, double price, int stock,
                   int min, int max, int machineId){
        super(id,name,price,stock,min,max);
        setMachineId(machineId);
    }

    /***
     * Sets MachineID field to value provided.
     * @param machineId Value provided to set field to.
     */
    public void setMachineId(int machineId){
        this.machineId = machineId;
    }

    /***
     * Gets stored MachineID field from object.
     * @return stored MachineID field.
     */
    public int getMachineId(){
        return this.machineId;
    }
}
