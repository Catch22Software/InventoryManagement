package INV;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/***
 * Inventory class that holds Master Inventory list of parts and products.
 */
public class Inventory {

    private static ObservableList<Part> allParts =
            FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts =
            FXCollections.observableArrayList();

    /***
     * Adds part to Master List.
     * @param newPart Part to add to Master List.
     */
    public static void addPart(Part newPart){

        allParts.add(newPart);
    }

    /***
     * Adds product to Master List.
     * @param newProduct Product to add to Master LIst.
     */
    public static void addProduct(Product newProduct){

        allProducts.add(newProduct);
    }

    /***
     * Finds selected part within Master List.
     * @param partId Search criteria.
     * @return Part that matches search criteria.
     */
    public static Part lookupPart(int partId){

        for(int i = 0;i < getAllParts().size();i++){
            if(getAllParts().get(i).getId()==partId)
                return getAllParts().get(i);
        }
        return null;
    }

    /***
     * Finds selected product within Master List.
     * @param productId Search criteria.
     * @return Product that matches search criteria.
     */
    public static Product lookupProduct(int productId) {

        for(int i = 0;i < getAllProducts().size();i++){
            if(getAllProducts().get(i).getId()==productId)
                return getAllProducts().get(i);
        }
        return null;
    }

    /***
     * Finds parts that match search criteria from Master List.
     * @param partName Search criteria.
     * @return Parts list that matches search criteria.
     */
    public static ObservableList<Part> lookupPart(String partName){

       ObservableList<Part> filterlist = FXCollections.observableArrayList();
       for(int i =0;i<getAllParts().size();i++){
           if(getAllParts().get(i).getName().toLowerCase().contains(partName))
               filterlist.add(getAllParts().get(i));
       }
       return filterlist;
    }

    /***
     * Finds products that match search criteria from Master List.
     * @param productName Search criteria.
     * @return Product list that matches search criteria.
     */
    public static ObservableList<Product> lookupProduct(String productName){

        ObservableList<Product> filterlist = FXCollections.observableArrayList();
        for(int i =0;i<getAllProducts().size();i++){
            if(getAllProducts().get(i).getName().toLowerCase().contains(productName))
                filterlist.add(getAllProducts().get(i));
        }
        return filterlist;
    }

    /***
     * Updates master list with selected part.
     * @param index Index in Master list to update.
     * @param selectedPart Replacement part.
     */
    public static void updatePart(int index, Part selectedPart){

        getAllParts().set(index,selectedPart);
    }

    /***
     * Updates master list with selected product.
     * @param index Index in Master list to update.
     * @param newProduct Replacement product.
     */
    public static void updateProduct(int index, Product newProduct){

        getAllProducts().set(index,newProduct);
    }

    /***
     * Deletes part from master list.
     * @param selectedPart part to delete from list.
     * @return true if deletion occurs.
     */
    public static boolean deletePart(Part selectedPart){

        int index = allParts.indexOf(selectedPart);
        if(index == -1)
            return false;
        else{
            allParts.remove(index);
            return true;
        }
    }

    /***
     * Deletes product from master list.
     * @param selectedProduct Product to delete from list.
     * @return true if deletion occurs.
     */
    public static boolean deleteProduct(Product selectedProduct){

        int index = allProducts.indexOf(selectedProduct);
        if(index == -1)
            return false;
        else{
            //Deletes associated parts to product if applicable.
            ObservableList<Part> parts =
            allProducts.get(index).getAllAssociatedParts();
                for(int i=0; i< parts.size(); i++){
                    if(allProducts.get(index).deleteAssociatedPart(parts.get(i)));
                }
            }
            allProducts.remove(index);
            return true;
    }

    /***
     * Returns the master list of parts.
     * @return Master list of parts.
     */
    public static ObservableList<Part> getAllParts(){

        return allParts;
    }

    /***
     * Returns the master list of products.
     * @return Master list of products.
     */
    public static ObservableList<Product> getAllProducts(){

        return allProducts;
    }
}