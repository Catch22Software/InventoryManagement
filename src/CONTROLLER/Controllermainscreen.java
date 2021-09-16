package CONTROLLER;

import INV.*;
import UTIL.AlertBox;
import UTIL.ConfirmBox;
import UTIL.InventoryHolder;
import UTIL.Utility;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import java.lang.reflect.Field;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/***
 * Controller that handles the Main Screen.
 */
public class Controllermainscreen implements Initializable{

    private FilteredList<Part> part1 = new FilteredList<>(Inventory.getAllParts());
    private FilteredList<Product> prod1 = new FilteredList<>(Inventory.getAllProducts());

    @FXML
    private TableView<Product> prodview;

    @FXML
    private TableView<Part> partsview;

    @FXML
    private TextField searchparts;

    @FXML
    private TableColumn<Part, ?> partidcol;

    @FXML
    private TableColumn<Part,?> partnamecol;

    @FXML
    private TableColumn<Part,?> partinvcol;

    @FXML
    private TableColumn<Part,Double> partpricecol;

    @FXML
    private TextField searchproduct;

    @FXML
    private TableColumn<Product,?> prodidcol;

    @FXML
    private TableColumn<Product,?> prodnamecol;

    @FXML
    private TableColumn<Product,?> prodinvcol;

    @FXML
    private TableColumn<Product,Double> prodpricecol;

    /***
     * Searches the part table for part id match or partial part name match.
     * @param actionEvent Event that calls the method.
     */
    public void searchPartAction(ActionEvent actionEvent) {

        //sets flag to check if search returns anything. Error box displayed if no part is found.
        Utility.setSearchindexflag(true);
        if(searchparts.getText() == null)
            part1.getPredicate();
        else {
            part1.setPredicate(Utility.createPartPredicate(searchparts.getText()));
            if (Utility.isSearchindexflag()) {
                AlertBox.display("Oops", "Please  clear out the " +
                        "search field \n and try your search again.");
            }
        }
    }

    /***
     * Loads the Add Part Screen.
     * @param actionEvent Event that calls the method.
     */
    @FXML
    public void addPartAction(ActionEvent actionEvent) {

        Utility.changeScene(2);

    }

    /***
     * Loads the Modify Part Screen with the part selected information loaded. Input Validation included.
     * @param actionEvent Event that calls the method.
     */
    public void modPartAction(ActionEvent actionEvent) {

        //checks to see if part was selected when modify part button is pressed. Displays error if fails.
        if(partsview.getSelectionModel().getSelectedItem() !=null) {
            InventoryHolder inv = InventoryHolder.getInstance();
            //Loads part correctly initially as InHouse or OutSourced part.
            try {
                Class<?> clazz = Class.forName("INV.InHouse");
                Field field = clazz.getDeclaredField("machineId");
                InHouse selectedItem =(InHouse) partsview.getSelectionModel().getSelectedItem();
                if(selectedItem.getMachineId()!=0){
                    if (inv != null) {
                        inv.setInHouse((InHouse) partsview.getSelectionModel().getSelectedItem());
                    }
                }
            } catch (ClassNotFoundException | NoSuchFieldException | ClassCastException e) {
                assert inv != null;
                inv.setOutsourced((Outsourced) partsview.getSelectionModel().getSelectedItem());
            }
            Utility.changeScene(3);
        }
        else{
            String title = "You can't do this";
            String message = "You can't modify a part that isn't selected";
            AlertBox.display(title,message);
        }
    }

    /***
     * Deletes selected part from Master Inventory part list. Input Validation performed.
     * @param actionEvent Event that calls the method.
     */
    public void delPartAction(ActionEvent actionEvent) {

        //displays error if no part is selected, otherwise gives user a confirmation box before deleting part.
        if (partsview.getSelectionModel().getSelectedItem() != null)
            ConfirmBox.displaypart(partsview);
        else{
            String title = "You can't do this";
            String message = "You can't delete a part that isn't selected";
            AlertBox.display(title,message);
        }
    }

    /***
     * Searches the product table for product id match or partial product name match.
     * @param actionEvent Event that calls the method.
     */
    public void searchProdAction(ActionEvent actionEvent) {

        //Flag set to display error message if product isn't found.
        Utility.setSearchindexflag(true);
        if(searchproduct.getText() == null)
            prod1.getPredicate();
        else {
            prod1.setPredicate(Utility.createProdPredicate(searchproduct.getText()));
            if (Utility.isSearchindexflag()) {
                AlertBox.display("Oops", "Please  clear out the " +
                        "search field \n and try your search again.");
            }
        }

    }

    /***
     * Opens the Add Product Screen
     * @param actionEvent Event that calls the method.
     */
    public void addProdAction(ActionEvent actionEvent) {

        Utility.changeScene(4);
    }

    /***
     * Opens the Modify Product Screen with selected product and load information properly.
     * @param actionEvent Event that calls the method.
     */
    public void modProdAction(ActionEvent actionEvent) {

        //Error message displayed if no product is selected in the TableView.
        if(prodview.getSelectionModel().getSelectedItem() !=null) {
            InventoryHolder inv = InventoryHolder.getInstance();
            Product tempprod = Inventory.lookupProduct(prodview.getSelectionModel().getSelectedItem().getId());
            assert tempprod != null;
            inv.setProduct(tempprod);
            Utility.changeScene(5);
        }
        else{
            String title = "You can't do this";
            String message = "You can't modify a product that isn't selected";
            AlertBox.display(title,message);
        }
    }

    /***
     * Deletes the selected Product from the master Inventory product list.
     * @param actionEvent Event that calls the method.
     */
    public void delProdAction(ActionEvent actionEvent) {

        //error message displayed if no product selected. Otherwise user must confirm to delete product.
        if (prodview.getSelectionModel().getSelectedItem() != null)
            ConfirmBox.displayprod(prodview);
        else{
            String title = "You can't do this";
            String message = "You can't delete a product that isn't selected";
            AlertBox.display(title,message);
        }
    }

    /***
     * Gracefully closes the program.
     * @param actionEvent Event that calls the method.
     *                    --FUTURE ENHANCEMENT-- Edit code in order to save Inventory to database or local file
     *                    based on clients needs.
     */
    public void exitAction(ActionEvent actionEvent) {

       System.exit(0);
    }

    /***
     * Overloaded initialize method.
     * --RUNTIME ERROR-- lines 254 and 255
     * caused the TableView to set the column data twice
     * which threw a runtime
     * error. Set the columns within the FXML file instead.
     * @param url Overloaded parameter.
     * @param resourceBundle Overloaded parameter.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Sets data in the part and product tables with current master Inventory list.
        partsview.setItems(part1);
        prodview.setItems(prod1);
        partidcol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partnamecol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partinvcol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partpricecol.setCellValueFactory(new PropertyValueFactory<>("price"));
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US);
        partpricecol.setCellFactory(tc -> new TableCell<Part, Double>() {

            @Override
            protected void updateItem(Double price, boolean empty) {
                super.updateItem(price, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(currencyFormat.format(price));
                }
            }
        });;
        // partsview.getColumns().addAll(partidcol,partnamecol,partinvcol,partpricecol);
        //prodview.getColumns().addAll(prodidcol,prodnamecol,prodinvcol,prodpricecol);
        // duplicate column name errors when adding the above lines
        prodidcol.setCellValueFactory(new PropertyValueFactory<>("id"));
        prodnamecol.setCellValueFactory(new PropertyValueFactory<>("name"));
        prodinvcol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        prodpricecol.setCellValueFactory(new PropertyValueFactory<>("price"));
        prodpricecol.setCellFactory(tc -> new TableCell<Product, Double>() {

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
    }
}