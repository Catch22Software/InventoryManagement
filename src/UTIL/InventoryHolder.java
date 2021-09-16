package UTIL;

import INV.*;

/***
 * Class that holds temporary Part or Product in order to be modified.
 */
public class InventoryHolder {

    private InHouse inHouse=null;
    private Outsourced outsourced=null;
    private Product product=null;

    private final static InventoryHolder INSTANCE = new InventoryHolder();

    private InventoryHolder(){}

    /***
     * Gets the single object instance of the class.
     * @return object instance of the class.
     */
    public static InventoryHolder getInstance(){

        return INSTANCE;
    }

    /***
     * Gets product data from the instance.
     * @return product data.
     */
    public Product getProduct() {
        return product;
    }

    /***
     * Sets product data from the instance.
     * @param product product data.
     */
    public void setProduct(Product product) { this.product = product; }

    /***
     * Gets InHouse part data from the instance.
     * @return InHouse part data.
     */
    public InHouse getInHouse() { return inHouse; }

    /***
     * Sets InHouse part data from the instance.
     * @param inHouse Inhouse part data.
     */
    public void setInHouse(InHouse inHouse) { this.inHouse = inHouse; }

    /***
     * Gets OutSourced part data from the instance.
     * @return OutSourced part data.
     */
    public Outsourced getOutsourced() { return outsourced; }

    /***
     * Sets OutSourced part data from the instance.
     * @param outsourced OutSourced part data.
     */
    public void setOutsourced(Outsourced outsourced) { this.outsourced = outsourced; }

    /***
     * Clears out all fields for the instance.
     */
    public void nullifyObjects(){

        inHouse=null;
        outsourced=null;
        product=null;
    }
}