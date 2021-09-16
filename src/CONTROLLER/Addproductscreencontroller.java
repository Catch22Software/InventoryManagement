package CONTROLLER;

import INV.*;
import UTIL.AlertBox;
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
 * Controller class that runs the Add Product Screen.
 */
public class Addproductscreencontroller implements Initializable {

   private FilteredList<Part> part1 = new FilteredList<>(Inventory.getAllParts());
   private ObservableList<Part> assocparts = FXCollections.observableArrayList();

    @FXML
    private TableView<Part> masterpartview;

    @FXML
    private TableView<Part> assocpartview;

    @FXML
    private TextField addprodname;

    @FXML
    private TextField addprodinv;

    @FXML
    private TextField addprodprice;

    @FXML
    private TextField addprodmin;

    @FXML
    private TextField addprodmax;

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
     * Searches the product table for matching product ID and / or partial product name.
     * @param keyEvent Event that calls the method.
     */
    public void searchPartAction(ActionEvent keyEvent) {

        Utility.setSearchindexflag(true); //Search flag displays error message to user if no parts are found.
        if(searchpartid.getText() == null)
            part1.getPredicate();
        else {
            part1.setPredicate(Utility.createPartPredicate(searchpartid.getText()));
            if (Utility.isSearchindexflag()) {
                //error message displayed in pop up window.
                AlertBox.display("Oops", "Please  clear out the " +
                        "search field \n and try your search again.");
            }
        }
    }

    /***
     * Gets the textfield info from the Add Product Name field.
     * @param event Event that calls the method.
     * @return Textfield information.
     */
    public String addProdNameAction(ActionEvent event) {

        return addprodname.getText();
    }

    /***
     * Gets the textfield info from the Add Product Inventory field.
     * @param event Event that calls the method.
     * @return Textfield information formatted as an Integer.
     */
    public int addProdInvAction(ActionEvent event) {

        return Integer.parseInt(addprodinv.getText());
    }

    /***
     * Gets the textfield info from the Add Product Price field.
     * @param event Event that calls the method.
     * @return Textfield information formatted as a Double.
     */
    public double addProdPriceAction(ActionEvent event) {

        return Double.parseDouble(addprodprice.getText());
    }

    /***
     * Gets the textfield info from the Add Product Min field.
     * @param event Event that calls the method.
     * @return Textfield information formatted as an Integer.
     */
    public int addProdMinAction(ActionEvent event) {

        return Integer.parseInt(addprodmin.getText());
    }

    /***
     * Gets the textfield info from the Add Product Max field.
     * @param event Event that calls the method.
     * @return Textfield information formatted as an Integer.
     */
    public int addProdMaxAction(ActionEvent event) {

        return Integer.parseInt(addprodmax.getText());
    }

    /***
     * Adds part from fields to associated parts list for the product to be add. Info is validated before saving.
     * @param event Event that calls the method.
     */
    public void addParttoProdAction(ActionEvent event) {

        //if user clicks that add associated part button on a part that is already added, the error pop up box comes up.
        if(masterpartview.getSelectionModel().getSelectedItem() != null){
            if(assocparts.contains(masterpartview.getSelectionModel().getSelectedItem())){
                String title = "You can't do this";
                String message = "You can't associate a part that is already associated";
                AlertBox.display(title,message);
                return;
            }

            //adds the selected parts to the associated part list for the proposed product.
            assocparts.add(masterpartview.getSelectionModel().getSelectedItem());
        }
        //displays error message pop up if user clicks add associated part button without a part selected.
        else{
            String title = "You can't do this";
            String message = "You can't associate a part that isn't selected";
            AlertBox.display(title,message);
        }
    }

    /***
     * Removes associated part from proposed product.
     * @param event Event that calls the method.
     */
    public void removeAssocPartAction(ActionEvent event) {

        //if the remove button is pressed with no parts selected, the error message pop up is displaed.
        if(assocpartview.getSelectionModel().getSelectedItem() != null){

            assocparts.remove(assocpartview.getSelectionModel().getSelectedItem());
        }
        else{
            String title = "You can't do this";
            String message = "You can't un associate a part that isn't selected";
            AlertBox.display(title,message);
        }
    }

    /***
     * Adds product to Inventory product list with associated parts list if applicable. Input validation is performed
     * first before saving info.
     * @param event Event that calls the method.
     */
    public void saveProductAction(ActionEvent event) {

        int
                stock = 0
                ,min = 0
                ,max = 0;
        double cost = 0.0;
        String name = addProdNameAction(event);
        boolean flag = false;
        {try{
            stock = addProdInvAction(event);
            min = addProdMinAction(event);
            max = addProdMaxAction(event);
            cost = addProdPriceAction(event);
        }
        //checks to see if numerical valued text fields contain spaces or characters.
        catch (NumberFormatException e){
            String title = "You can't do this";
            String message = "You can't use letters, symbols spaces for 'Inv, Min, Max and Cost' . \n You can" +
                    " only use numbers";
            flag = true;
            AlertBox.display(title,message);
        }}
        //checks to see if product name is blank.
        if(name.equals("")){ ;
            String title = "You can't do this";
            String message = "You can't name a product nothing. ";
            flag = true;
            AlertBox.display(title,message);
        }
        //checks if inventory level is between min and max levels.
        if(min > stock || max < stock){
            String title = "You can't do this";
            String message = "The stock level must be between the minimum and maximum values. ";
            AlertBox.display(title,message);
            flag = true;
        }
        //checks to see if negative numbers are used for any numerical values.
        if(min <0 || max < 0|| stock < 0 || cost < 0.00){
            String title = "You can't do this";
            String message = "You can't use negative numbers for any numerical values";
            AlertBox.display(title,message);
            flag = true;
        }
        //stops method execution if any of the above errors are called.
        if(flag)
            return;
        //adds associated parts list to the proposed product if applicable.
        if(!(assocparts.contains(null))){
            Product product = new Product(Utility.addMasterProdID(), name, cost, stock, min, max);
            Inventory.getAllProducts().add(product);
            for (Part assocpart : assocparts) {

                Inventory.getAllProducts().get(Inventory.getAllProducts().indexOf(product)).addAssociatedPart(
                        assocpart
                );

            }
        }
        else{
            Product product = new Product(Utility.addMasterProdID(), name, cost, stock, min, max);
            Inventory.getAllProducts().add(product);
        }
        assocparts.clear();
        //goes back to main screen after saving product.
        Utility.changeScene(1);
    }

    /***
     * Sends the user back to Main Screen.
     * @param event Event that calls the method.
     */
    public void backToMainScreenAction(ActionEvent event) {

        Utility.changeScene(1);
    }

    /***
     * Overloaded initialize method.
     * @param url overloaded parameter
     * @param resourceBundle overloaded parameter.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //populates the tableviews and formats the individual cells.
        masterpartview.setItems(part1);
        assocpartview.setItems(assocparts);
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

    }
}