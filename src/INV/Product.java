package INV;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/***
 * Class that holds Product object for the master inventory list.
 */
public class Product{

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /***
     * Constructor to create a product object.
     * @param id Product ID.
     * @param name Product name.
     * @param price Product price.
     * @param stock Product inventory level.
     * @param min Product min inventory level.
     * @param max Product max inventory level.
     */
    public Product(int id, String name, double price, int stock,
                   int min, int max){
       this.id = id;
       this.name = name;
       this.price = price;
       this.stock = stock;
       this.min = min;
       this.max = max;
    }

    /***
     * Gets product ID.
     * @return product ID.
     */
    public int getId() {
        return id;
    }

    /***
     * Sets product ID.
     * @param id product ID to set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /***
     * Gets product name.
     * @return product name.
     */
    public String getName() {
        return name;
    }

    /***
     * Sets product name.
     * @param name product name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /***
     * Gets product price.
     * @return product price.
     */
    public double getPrice() {
        return price;
    }

    /***
     * Sets product price.
     * @param price product price to set.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /***
     * Gets product inventory level.
     * @return product inventory level.
     */
    public int getStock() {
        return stock;
    }

    /***
     * Sets product inventory level.
     * @param stock product inventory level to set.
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /***
     * Gets product min inventory level.
     * @return product min inventory level.
     */
    public int getMin() {
        return min;
    }

    /***
     * Sets product min inventory level.
     * @param min product min inventory level to set.
     */
    public void setMin(int min) {
        this.min = min;
    }

    /***
     * Gets product max inventory level.
     * @return product max inventory level.
     */
    public int getMax() {
        return max;
    }

    /***
     * Sets product max inventory level.
     * @param max product max inventory level to set.
     */
    public void setMax(int max) {
        this.max = max;
    }

    /***
     * Adds parts to associated parts list.
     * @param part parts to add to list.
     */
    public void addAssociatedPart(Part part){
        associatedParts.add(part);
    }

    /***
     * Deletes selected associated part from product.
     * @param selectedAssociatedPart selected part to delete.
     * @return true if deletion occurs.
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart){

        int index = associatedParts.indexOf(selectedAssociatedPart);
        if(index == -1)
            return false;
        else{
            associatedParts.remove(index);
            return true;
        }
    }

    /***
     * Gets a list of associated parts for a product.
     * @return list of associated parts.
     */
    public ObservableList<Part> getAllAssociatedParts(){

        return associatedParts;
    }
}