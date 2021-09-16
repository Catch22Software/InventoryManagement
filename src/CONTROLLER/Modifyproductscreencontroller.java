package CONTROLLER;

import INV.Inventory;
import INV.Part;
import INV.Product;
import UTIL.AlertBox;
import UTIL.InventoryHolder;
import UTIL.Utility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/***
 * Controller that loads the Modify Product Screen.
 */
public class Modifyproductscreencontroller implements Initializable {

    private FilteredList<Part> part1 = new FilteredList<>(Inventory.getAllParts());
    private ObservableList<Part> assocparts = FXCollections.observableArrayList();

    @FXML
    private TableView<Part> masterpartsview;

    @FXML
    private TableView<Part> assocpartsview;

    @FXML
    private TextField modprodid;

    @FXML
    private TextField modprodname;

    @FXML
    private TextField modprodinv;

    @FXML
    private TextField modprodprice;

    @FXML
    private TextField modprodmin;

    @FXML
    private TextField modprodmax;

    @FXML
    private TextField searchpartid;

    @FXML
    private TableColumn<Part, ?> masterpartid;

    @FXML
    private TableColumn<Part, ?> masterpartname;

    @FXML
    private TableColumn<Part, ?> masterpartinv;

    @FXML
    private TableColumn<Part, Double> masterpartprice;

    @FXML
    private TableColumn<Part, ?> partid;

    @FXML
    private TableColumn<Part, ?> partname;

    @FXML
    private TableColumn<Part, ?> partinv;

    @FXML
    private TableColumn<Part, Double> partprice;

    /***
     * Gets Textfield info from Modify Product ID field.
     * @param event Event that calls the method.
     * @return Textfield info formatted into an Integer.
     */
    public int modProdIdAction(ActionEvent event) {

        return Integer.parseInt(modprodid.getText());
    }

    /***
     * Gets Textfield info from Modify Product Name field.
     * @param event Event that calls the method.
     * @return Textfield info.
     */
    public String modProdNameAction(ActionEvent event) {

        return modprodname.getText();
    }

    /***
     * Gets Textfield info from Modify Product Inventory field.
     * @param event Event that calls the method.
     * @return Textfield info formatted into an Integer.
     */
    public int modProdInvAction(ActionEvent event) {

        return Integer.parseInt(modprodinv.getText());
    }

    /***
     * Gets Textfield info from Modify Product Price field.
     * @param event Event that calls the method.
     * @return Textfield info formatted into a Double.
     */
    public double modProdPriceAction(ActionEvent event) {

        return Double.parseDouble(modprodprice.getText());
    }

    /***
     * Gets Textfield info from Modify Product Min field.
     * @param event Event that calls the method.
     * @return Textfield info formatted into an Integer.
     */
    public int modProdMinAction(ActionEvent event) {

        return Integer.parseInt(modprodmin.getText());
    }

    /***
     * Gets Textfield info from Modify Product Max field.
     * @param event Event that calls the method.
     * @return Textfield info formatted into an Integer.
     */
    public int modProdMaxAction(ActionEvent event) {

        return Integer.parseInt(modprodmax.getText());
    }

    /***
     * Returns a part list from the master Inventory list based on
     *  part ID matches or partial part name matches. Input Validation performed.
     * @param event Event that calls the method.
     */
    public void searchPartAction(ActionEvent event) {

        //flag set to open error pop up if no search results found.
        Utility.setSearchindexflag(true);
        if(searchpartid.getText() == null)
            part1.getPredicate();
        else {
            part1.setPredicate(Utility.createPartPredicate(searchpartid.getText()));
            if (Utility.isSearchindexflag()) {
                AlertBox.display("Oops", "Please  clear out the " +
                        "search field \n and try your search again.");
            }
        }
    }

    /***
     * Adds selected part from master list to associated parts of Modified product.
     * @param event Event that calls the method.
     */
    public void addAssocPartsAction(ActionEvent event) {

        //Checks if part has already been added as an associated part.
        if(masterpartsview.getSelectionModel().getSelectedItem() != null){
            if(assocparts.contains(masterpartsview.getSelectionModel().getSelectedItem())){
                String title = "You can't do this";
                String message = "You can't associate a part that is already associated";
                AlertBox.display(title,message);
                return;
            }

            assocparts.add(masterpartsview.getSelectionModel().getSelectedItem());
        }
        //Error message if no part is selected when button pressed.
        else{
            String title = "You can't do this";
            String message = "You can't associate a part that isn't selected";
            AlertBox.display(title,message);
        }
    }

    /***
     * Removes selected part from associated part list for modified product.
     * @param event Event that calls the method.
     */
    public void removeAssocPartsAction(ActionEvent event) {

        //Checks if part was selected before clicking button.
        if(assocpartsview.getSelectionModel().getSelectedItem() != null){

            assocparts.remove(assocpartsview.getSelectionModel().getSelectedItem());
        }
        else{
            String title = "You can't do this";
            String message = "You can't un associate a part that isn't selected";
            AlertBox.display(title,message);
        }
    }

    /***
     * Replaces original product with Modified version and sets the associated parts list correctly.
     * Input Validation performed.
     * @param event Event that calls the method.
     */
    public void saveModProdAction(ActionEvent event) {

        InventoryHolder holder = InventoryHolder.getInstance();

        int
                id = holder.getProduct().getId()
                ,stock = 0
                ,min = 0
                ,max = 0;
        double cost = 0.0;
        String name = modProdNameAction(event);
        boolean flag = false;
        {try{
            stock = modProdInvAction(event);
            min = modProdMinAction(event);
            max = modProdMaxAction(event);
            cost = modProdPriceAction(event);
        }
        //Checks if non numerical characters or spaces are used for numerical fields.
        catch (NumberFormatException e){
            String title = "You can't do this";
            String message = "You can't use letters, symbols spaces for 'Inv, Min, Max and Cost' . \n You can" +
                    " only use numbers";
            flag = true;
            AlertBox.display(title,message);
        }}
        //Checks if product name is blank.
        if(name.equals("")){ ;
            String title = "You can't do this";
            String message = "You can't name a product nothing. ";
            flag = true;
            AlertBox.display(title,message);
        }
        //Checks if stock is between min and max levels.
        if(min > stock || max < stock){
            String title = "You can't do this";
            String message = "The stock level must be between the minimum and maximum values. ";
            AlertBox.display(title,message);
            flag = true;
        }
        //Checks for negative values in Numerical fields.
        if(min <0 || max < 0|| stock < 0 || cost < 0.00){
            String title = "You can't do this";
            String message = "You can't use negative numbers for any numerical values";
            AlertBox.display(title,message);
            flag = true;
        }
        //If any above errors are executed, method stops.
        if(flag)
            return;
        //save modified product to holder object, then replaces Master Inventory product correctly with assoc parts.
        Product modifiedproduct = new Product(id, name, cost, stock, min, max);
        int place = Inventory.getAllProducts().indexOf(holder.getProduct());
        Inventory.updateProduct(place,modifiedproduct);
        if(!(assocparts.contains(null))){
            for (Part assocpart : assocparts) {
                Inventory.getAllProducts().get(Inventory.getAllProducts().indexOf(modifiedproduct)).addAssociatedPart(
                        assocpart);
            }
        }
        assocparts.clear();
        holder.nullifyObjects();
        Utility.changeScene(1);
    }

    /***
     * Goes back to the Main Screen.
     * @param event Event that calls the method.
     */
    public void backToMainAction(ActionEvent event) {

        InventoryHolder holder = InventoryHolder.getInstance();
        holder.nullifyObjects();
        Utility.changeScene(1);
    }

    /***
     * Overloaded initialize method.
     * @param url Overloaded parameter.
     * @param resourceBundle Overloaded parameter.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //populates the associated parts list for the selected product and the master parts list.
        masterpartsview.setItems(part1);
        assocpartsview.setItems(assocparts);
        masterpartid.setCellValueFactory(new PropertyValueFactory<>("id"));
        masterpartname.setCellValueFactory(new PropertyValueFactory<>("name"));
        masterpartinv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        masterpartprice.setCellValueFactory(new PropertyValueFactory<>("price"));
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US);
        masterpartprice.setCellFactory(tc -> new TableCell<Part, Double>() {

            @Override
            protected void updateItem(Double price, boolean empty) {
                super.updateItem(price, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(currencyFormat.format(price));
                }
            }
        });
        partid.setCellValueFactory(new PropertyValueFactory<>("id"));
        partname.setCellValueFactory(new PropertyValueFactory<>("name"));
        partinv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partprice.setCellValueFactory(new PropertyValueFactory<>("price"));
        partprice.setCellFactory(tc -> new TableCell<Part, Double>() {

            @Override
            protected void updateItem(Double price, boolean empty) {
                super.updateItem(price, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(currencyFormat.format(price));
                }
            }
        });
        //sets the previously selected product from the main screen to fields properly for adjustments.
        InventoryHolder inv = InventoryHolder.getInstance();
        Product product = inv.getProduct();
        modprodid.setText(Integer.toString(product.getId()));
        modprodname.setText(product.getName());
        modprodprice.setText(Double.toString(product.getPrice()));
        modprodinv.setText(Integer.toString(product.getStock()));
        modprodmin.setText(Integer.toString(product.getMin()));
        modprodmax.setText(Integer.toString(product.getMax()));
        for(int i = 0; i < inv.getProduct().getAllAssociatedParts().size() ;i++){

            //Checks to see if any of the saved associated parts have been deleted when the product is modified.
            if((Inventory.lookupPart(inv.getProduct().getAllAssociatedParts().get(i).getId()))!=null)
                assocparts.add(inv.getProduct().getAllAssociatedParts().get(i));
        }
    }
}
